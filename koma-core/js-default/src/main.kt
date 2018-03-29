import koma.internal.default.generated.matrix.DefaultDoubleMatrixFactory
import koma.internal.default.generated.matrix.DefaultFloatMatrixFactory
import koma.internal.default.generated.matrix.DefaultIntMatrixFactory

fun main(args: Array<String>) {
    /**
     * Register the matrices this backend contains with koma
     */
    koma.internal.doubleFac = DefaultDoubleMatrixFactory()
    koma.internal.intFac = DefaultIntMatrixFactory()
    koma.internal.floatFac = DefaultFloatMatrixFactory()
}
