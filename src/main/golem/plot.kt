@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.Matrix
import org.knowm.xchart.*
import javax.swing.JFrame
import golem.util.*;
import java.awt.Color
import java.awt.Graphics
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.image.BufferedImage
import javax.swing.JLabel
import javax.swing.WindowConstants


// While a state-machine isnt very OO, its necessary to replicate convenient MATLAB style plotting.
// This tracks the current figure we're plotting our lines to when someone calls plot(). Its updated
// by figure(numberHere)
private var currentFigure = 1
private val MAX_FIGURES = 100
/**
 * The set of raw objects for all figures currently created. This should not ordinarily be used by the end-user,
 * but may be helpful for advanced plotting. Be careful with threading issues if you modify these.
 */
public var figures= arrayOfNulls<Triple<Chart, JFrame, Int>>(MAX_FIGURES)

/**
 * Sets the current figure to plot to. For example, the following plots 2 lines to the first window
 * and 1 line to the second window:
 *
 * figure(1)
 * randn(3)
 * randn(3)
 * figure(2)
 * randn(3)
 *
 * @param The window to plot any new lines to
 *
 */
fun figure(num: Int) {
    currentFigure = num
}

/**
 * Plots y as a line series. y must be a matrix, numerical array, or range.
 */
fun plot(y: Any) = plot(null, y)

/**
 * Plots x vs y, where x is the horizontal axis and y is the vertical. x and y must be
 * matrices, numerical arrays, or ranges.
 *
 * @param x X-axis locations
 * @param y Corresponding Y-axis locations
 *
 */
@JvmOverloads
fun plot(x: Any?, y: Any, color: String="k") {

    // Workaround for Kotlin REPL starting in headless mode
    System.setProperty("java.awt.headless", "false")

    // Someone might have tried to called us as plot(data,"color")
    if ((y is String || y is Char) && x != null)
        return plot(null, x, y.toString())


    var xdata: DoubleArray
    var ydata: DoubleArray

    ydata = when (y)
    {
        is IntArray -> fromCollection(y.map { it.toDouble() })
        is IntRange -> fromCollection(y.toList().map { it.toDouble() })
        is DoubleArray -> fromCollection(y.toList())
        is Matrix<*> -> y.getDoubleData()
        else -> throw IllegalArgumentException("Can only plot double arrays, matrices, or ranges (y was ${y.javaClass}")
    }
    xdata = when (x) {
        is IntArray -> fromCollection(x.map { it.toDouble() })
        is IntRange -> fromCollection(x.toList().map { it.toDouble() })
        is DoubleArray -> fromCollection(x.toList())
        is Matrix<*> -> x.getDoubleData()
        null -> fromCollection((0..(ydata.size.toInt()-1)).toList().map{it.toDouble()})
        else -> throw IllegalArgumentException("Can only plot double arrays, matrices, or ranges (x was ${x.javaClass}")
    }
    plotArrays(xdata, ydata, color)
}
/**
 * Plots x vs y, where x is the horizontal axis and y is the vertical.
 *
 * @param x X-axis locations
 * @param y Corresponding Y-axis locations
 *
 */
@JvmOverloads
fun plotArrays(x: DoubleArray, y: DoubleArray, color: String = "k") {

    // If we've never shown this plot before OR someone closed the window, make a new frame and chart
    if (figures[currentFigure] == null) {
        val chart = QuickChart.getChart("Plot #${currentFigure.toString()}", "X", "Y", "Line #1", x, y)
        val frame = displayChart(chart)
        figures[currentFigure] = Triple(chart, frame, 1)
        var series = chart.seriesMap.values.first()
        series.setLineColor(plotColors[color])

    }
    // Adding a line to an existing chart
    else {
        var (chart, frame, numLines) = figures[currentFigure]!!
        figures[currentFigure] = Triple(chart, frame, numLines+1)
        val series = chart.addSeries("Line #${(numLines+1).toString()}", x, y)
        series.setLineColor(plotColors[color])
        series.setMarker(SeriesMarker.NONE)
        java.awt.Color.BLACK

        frame.repaint()
    }
}

private fun displayChart(c: Chart): JFrame {

    // Create and set up the window.
    val frame = JFrame("Plot #$currentFigure")

    // Clear the plot # if we close its window (mimic matplotlib behavior)
    frame.addWindowListener(object : WindowListener {
        override fun windowDeiconified(e: WindowEvent?) {}
        override fun windowActivated(e: WindowEvent?) {}
        override fun windowDeactivated(e: WindowEvent?) {}
        override fun windowIconified(e: WindowEvent?) {}
        override fun windowClosing(e: WindowEvent?) {}
        override fun windowOpened(e: WindowEvent?) {}
        override fun windowClosed(e: WindowEvent?) {
            figures.forEachIndexed { i, triple ->
                if (triple != null && triple.second == frame)
                    figures[i] = null
            }
        }
    })

    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(object : Runnable {

        override fun run() {

            val chartPanel = XChartPanel(c)
            frame.add(chartPanel)

            // Causes program to exit if all windows are closed and other threads have exited.
            frame.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

            // Display the window.
            frame.pack()
            frame.isVisible = true
        }
    })

    return frame
}

/**
 * Plots a matrix consisting of 2D data as an image.
 *
 * @param mat the matrix to display as an image
 * @param representation an integer representing a color space from [BufferedImage]
 */
fun imshow(mat: Matrix<Double>, representation: Int = BufferedImage.TYPE_BYTE_GRAY)
{
    // Workaround for Kotlin REPL starting in headless mode
    System.setProperty("java.awt.headless", "false")

    var image = BufferedImage(mat.numCols(),
                              mat.numRows(),
                              representation)

    for (r in 0..mat.numRows()-1)
        for (c in 0..mat.numCols()-1)
            image.setRGB(c,r,(mat[r,c]).toInt())

    var frame = JFrame()
    frame.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    var panel = object:JLabel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.drawImage(image, 0, 0, null)
        }
    }
    frame.add(panel)
    frame.setSize(mat.numCols(),mat.numRows())
    frame.isVisible = true

}

fun main(args: Array<String>) {
    plot(randn(50), "p")
    plot(randn(50), 'o')

}

val plotColors = mapOf(Pair("k", Color.BLACK),
                       Pair("b", Color.BLUE),
                       Pair("g", Color.GREEN),
                       Pair("gr", Color.GRAY),
                       Pair("lgr", Color.LIGHT_GRAY),
                       Pair("y", Color.YELLOW),
                       Pair("o", Color.ORANGE),
                       Pair("c", Color.ORANGE),
                       Pair("m", Color.MAGENTA),
                       Pair("r", Color.RED),
                       Pair("dg", Color.DARK_GRAY),
                       Pair("p", Color.PINK))
