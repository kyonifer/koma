import koma.matrix.default.DefaultDoubleMatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory
import koma.matrix.default.DefaultLongMatrixFactory

fun main(args: Array<String>) {
    /**
     * Register the matrices this backend contains with koma
     */
    koma.internal.doubleFac = DefaultDoubleMatrixFactory()
    koma.internal.intFac = DefaultIntMatrixFactory()
    koma.internal.floatFac = DefaultFloatMatrixFactory()
}
