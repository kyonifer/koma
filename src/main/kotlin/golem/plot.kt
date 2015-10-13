package golem

import golem.matrix.Matrix
import org.knowm.xchart.*
import javax.swing.JFrame
import golem.util.*;

private var plotCount = 1

fun plot(x: Matrix<Double>, y: Matrix<Double>) = plot(x.getDoubleData(), y.getDoubleData())
fun plot(x: DoubleArray, y: DoubleArray): Pair<Chart?, JFrame>  {

    // Workaround for Kotlin REPL starting in headless mode
    System.setProperty("java.awt.headless", "false")
    val plot = QuickChart.getChart("Plot #${plotCount.toString()}", "X", "Y", "Data", x, y)
    var frame: JFrame
    frame = displayChart(plot)
    frame.defaultCloseOperation = 2//JFrame.DISPOSE_ON_CLOSE;
    plotCount += 1

    return Pair(plot, frame)
}
fun plot(x: Any?, y: Any): Pair<Chart?, JFrame>   {
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
    return plot(xdata, ydata)
}
fun plot(y: Any) = plot(null, y)


fun displayChart(c: Chart): JFrame {

    // Create and set up the window.
    val frame = JFrame("Foo")

    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(object : Runnable {

        override fun run() {

            val chartPanel = XChartPanel(c)
            frame.add(chartPanel)

            // Display the window.
            frame.pack()
            frame.setVisible(true)
        }
    })

    return frame
}