package golem.matrix.ejml

import golem.matrix.Matrix
import golem.matrix.ejml.backend.*
import org.ejml.simple.SimpleMatrix
import org.ejml.ops.*

class Mat(var storage: SimpleMatrix) : Matrix<Mat, Double>
{

    override fun diag() = Mat(storage.extractDiag())
    override fun cumsum() = storage.elementSum()
    override fun max() = CommonOps.elementMax(this.storage.matrix)
    override fun mean() = throw UnsupportedOperationException()
    override fun min() = CommonOps.elementMin(this.storage.matrix)
    override fun argMax() = throw UnsupportedOperationException()
    override fun argMean() = throw UnsupportedOperationException()
    override fun argMin() = throw UnsupportedOperationException()
    override fun norm() = this.storage.normF()
    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage.get(i)
    override fun setDouble(i: Int, v: Double) = this.storage.set(i, v)
    override fun setDouble(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)

    override fun numRows() = this.storage.numRows()
    override fun numCols() = this.storage.numCols()
    override fun times(other: Mat) = Mat(this.storage.times(other.storage))
    override fun times(other: Double) = Mat(this.storage.times(other))
    override fun elementTimes(other: Mat) = Mat(this.storage.elementMult(other.storage))
    override fun mod(other: Mat) = Mat(this.storage.mod(other.storage))
    override fun minus() = Mat(this.storage.minus())
    override fun minus(other: Double) = Mat(this.storage.minus(other))
    override fun minus(other: Mat) = Mat(this.storage.minus(other.storage))
    override fun div(other: Int) = Mat(this.storage.div(other))
    override fun div(other: Double) = Mat(this.storage.div(other))
    override fun transpose() = Mat(this.storage.transpose())
    override fun set(i: Int, v: Double): Unit = if(this.storage.numRows()==1) this.storage.set(1,i,v) else this.storage.set(i,1,v)
    override fun set(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)
    override fun get(i: Int, j: Int) = this.storage.get(i,j)
    override fun get(i: Int) = this.storage.get(i)
    override fun getRow(row: Int) = Mat(SimpleMatrix(CommonOps.extractRow(this.storage.matrix, row, null)))
    override fun getCol(col: Int) = Mat(SimpleMatrix(CommonOps.extractColumn(this.storage.matrix, col, null)))
    override fun plus(other: Mat) = Mat(this.storage.plus(other.storage))
    override fun plus(other: Double) = Mat(this.storage.plus(other))
    override fun chol() = Mat(SimpleMatrix(this.storage.chol().getT(null)))
    override fun inv() = Mat(this.storage.inv())
    override fun det() = this.storage.determinant()
    override fun pinv()= Mat(this.storage.pseudoInverse())
    override fun normf() = this.storage.normF()
    override fun elementSum() = this.storage.elementSum()
    override fun trace() = this.storage.trace()

    override val T: Mat
        get() = this.transpose()


    override fun LU(): Pair<Mat, Mat> {
        val decomp = this.storage.LU()
        return Pair(Mat(SimpleMatrix(decomp.getLower(null))),
                    Mat(SimpleMatrix(decomp.getUpper(null))))
    }

    override fun QR(): Pair<Mat, Mat> {
        val decomp = this.storage.QR()
        return Pair(Mat(SimpleMatrix(decomp.getQ(null, false))),
                    Mat(SimpleMatrix(decomp.getR(null, false))))
    }

    override fun repr() = this.storage.toString()

    /* These methods are defined in order to support fast non-generic calls. However,
       since our type is Double we'll disable them here in case someone accidentally
       uses them.
     */
    override fun getInt(i: Int, j: Int): Int {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }
    override fun getFloat(i: Int, j: Int): Float {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }
    override fun getInt(i: Int): Int {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }

    override fun getFloat(i: Int): Float {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setInt(i: Int, v: Int) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setFloat(i: Int, v: Float) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setInt(i: Int, j: Int, v: Int) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }
    override fun setFloat(i: Int, j: Int, v: Float) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                "Please call getDouble and cast manually if this is intentional.")
    }


}
