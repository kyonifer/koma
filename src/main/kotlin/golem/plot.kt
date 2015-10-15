package golem

import golem.matrix.Matrix
import org.knowm.xchart.*
import javax.swing.JFrame
import golem.util.*;
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.WindowConstants

// While a state-machine isnt very OO, its necessary to replicate convenient MATLAB style plotting.
// This tracks the current figure we're plotting our lines to when someone calls plot(). Its updated
// by figure(numberHere)
private var currentFigure = 1
private val MAX_FIGURES = 100
public var figures= arrayOfNulls<Triple<Chart, JFrame, Int>>(MAX_FIGURES)

fun figure(num: Int) {
    currentFigure = num
}

fun plot(y: Any) = plot(null, y)
fun plot(x: Matrix<Double>, y: Matrix<Double>) = plot(x.getDoubleData(), y.getDoubleData())
fun plot(x: Any?, y: Any) {
    var xdata: DoubleArray
    var ydata: DoubleArray

    when (y)
    {
        is IntArray -> ydata =  fromCollection(y.map { it.toDouble() })
        is IntRange -> ydata =  fromCollection(y.toList() map { it.toDouble() })
        is DoubleArray -> ydata = fromCollection(y.toList())
        is DoubleRange -> ydata = fromCollection(y.toList())
        is Matrix<*> -> ydata = y.getDoubleData()
        else -> throw IllegalArgumentException("Can only plot double arrays, matrices, or ranges (y was ${y.javaClass}")
    }
    when (x) {
        is IntArray -> xdata =  fromCollection(x.map { it.toDouble() })
        is IntRange -> xdata =  fromCollection(x.toList() map { it.toDouble() })
        is DoubleArray -> xdata = fromCollection(x.toList())
        is DoubleRange -> xdata = fromCollection(x.toList())
        is Matrix<*> -> xdata = x.getDoubleData()
        null -> xdata =  fromCollection((0.0..(ydata.size().toDouble()-1)).toList())
        else -> throw IllegalArgumentException("Can only plot double arrays, matrices, or ranges (x was ${x.javaClass}")
    }
    plot(xdata, ydata)
}
fun plot(x: DoubleArray, y: DoubleArray) {
    // Workaround for Kotlin REPL starting in headless mode
    System.setProperty("java.awt.headless", "false")

    // If we've never shown this plot before OR someone closed the window, make a new frame and chart
    if (figures[currentFigure] == null) {
        val chart = QuickChart.getChart("Plot #${currentFigure.toString()}", "X", "Y", "Line #1", x, y)
        val frame = displayChart(chart)
        figures[currentFigure] = Triple(chart, frame, 1)
    }
    // Adding a line to an existing chart
    else {
        var (chart, frame, numLines) = figures[currentFigure]!!
        figures[currentFigure] = Triple(chart, frame, numLines+1)
        val series = chart.addSeries("Line #${(numLines+1).toString()}", x, y)
        series.setMarker(SeriesMarker.NONE)

        frame.repaint()
}
}

private fun displayChart(c: Chart): JFrame {

    // Create and set up the window.
    val frame = JFrame("Plot #${currentFigure}")

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