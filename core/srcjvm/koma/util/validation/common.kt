@file:JvmName("Validation")

package koma.util.validation

import koma.*
import koma.matrix.*
import koma.polyfill.annotations.*

private val DEFAULT_NAME = "matrix"

/**
 * Callback used by ValidationContext after evaluating all rules. Subclass this if your validation plugin needs
 * to hold all validation until the end.
 */
interface Validator {
    /**
     * Validate all the matrices in the context according to your collected rules.
     */
    fun performValidation(context: ValidationContext)
}


/**
 * A lambda receiver with state and convenience methods for validating a group of matrices.
 *
 * In general there is no reason to instantiate this directly, instead @see validate(fn) for example usage.
 */
class ValidationContext {
    val matrices = mutableListOf<Matrix<Double>>()
    val matrixNames = mutableListOf<String>()
    val metadataStorage = mutableMapOf<String, Any>()
    private val validators = mutableSetOf<Validator>()

    val currentMatrix: Matrix<Double> get() = matrices[matrices.size - 1]
    val currentMatrixName: String get() = matrixNames[matrixNames.size - 1]

    /**
     * Execute the given fn (which should contain validation rules) against the given matrix.
     *
     * In general it is preferable to use the other verison of this function that lets you specify a name.
     * All matrices that are validated with this method will appear in the error message with the name
     * "matrix"
     *
     */
    operator fun Matrix<Double>.invoke(fn: ValidationContext.() -> Unit): ValidationContext = this(DEFAULT_NAME, fn)

    /**
     * Execute the given fn (which should contain validation rules) against the given matrix with the given
     * name.
     * @param name Name of the matrix to report in validation error messages.
     * @returns this
     */
    operator fun Matrix<Double>.invoke(name: String, fn: ValidationContext.() -> Unit): ValidationContext {
        matrixNames.add(name)
        matrices.add(this)
        if (validateMatrices)
            fn(this@ValidationContext)
        return this@ValidationContext
    }

    // Convenience methods for dimension checking
    //@formatter: off
    /** @see dim( Int,  Int) */ infix fun  Int.x(cols: Int)  { dim(this, cols) }
    /** @see dim( Int, Char) */ infix fun  Int.x(cols: Char) { dim(this, cols) }
    /** @see dim(Char,  Int) */ infix fun Char.x(cols: Int)  { dim(this, cols) }
    /** @see dim(Char, Char) */ infix fun Char.x(cols: Char) { dim(this, cols) }
    //@formatter: on

    /**
     * Add a helper that will perform validation at the end of the block.
     */
    fun addValidator(validator: Validator) {
        validators.add(validator)
    }

    /**
     * Check declared matrices against any rules that have been added with addValidator.
     */
    fun validate() {
        if (!validateMatrices) return
        for (validator in validators)
            validator.performValidation(this)
    }

    /**
     * Check declared matrices against any rules that have been added with addValidator, after
     * running the given callback.
     *
     * @param fn A function to execute before validating.
     */
    fun validate(fn: ValidationContext.() -> Unit) {
        fn()
        validate()
    }

    inline fun <reified T> metadata(key: String, factory: () -> T): T {
        return metadataStorage.getOrPut(key, { factory() as Any }) as T
    }

    // Method syntax workaround for people who use testMatrix instead of validate.
    operator fun invoke() : ValidationContext = this
}


/**
 * Return a validation context that can be used to validate the given matrix with the default name of "matrix".
 *
 * In general it is preferable to use the other verison of this function that lets you specify a name. All
 * matrices that are validated with this method will appear in the error message with the name "matrix"
 *
 * @param matrix A matrix to validate.
 * @returns A validation context that can be used to validate the given matrix.
 */
fun testMatrix(matrix: Matrix<Double>) : ValidationContext = testMatrix(matrix, DEFAULT_NAME)

/**
 * Return a validation context that can be used to validate the given matrix with the given name.
 *
 * <pre>
 * {@code
 * testMatrix(myMatrix, "myMatrix").dim(1, 4).validate() // validates myMatrix is 1 by 4.
 * }
 * </pre>
 *
 * @param matrix A matrix to validate.
 * @param name The name of the matrix (used in displayed errors)
 * @returns A validation context that can be used to validate the given matrix.
 */
fun testMatrix(matrix: Matrix<Double>, name: String) : ValidationContext  {
    val ctx = ValidationContext()
    ctx.matrixNames.add(name)
    ctx.matrices.add(matrix)
    return ctx
}

/**
 * Execute the given rules within a ValidationContext, letting you validate multiple matrices at once with
 * interrelated dimensions, and return a list of matrices that match your validation rules.
 *
 * <pre>
 * {@code
 * val (myFoo, myBar) = validate {
 *     foo("foo") { 1 x 'N' }
 *     bar("bar") { 'N' x 1; max = 0.5 }
 * }
 * }
 * </pre>
 */
fun validate(fn: ValidationContext.() -> Unit) {
    val ctx = ValidationContext()
    ctx.validate(fn)
}

/**
 * Use the given fn to validate a matrix. Return either the matrix itself or a copy that has been transformed
 * to match the validation rules.
 *
 * In general it is preferable to use the other verison of this function that lets you specify a name. All
 * matrices that are validated with this method will appear in the error message with the name "matrix"
 *
 * @see Matrix<Double>.validate(name, fn)
 * @param fn Validation rules for the matrix.
 * @returns Either a reference to the input matrix itself, or a transformed version. The return value is
 * guaranteed to pass the validation rules.
 */
fun Matrix<Double>.validate(fn: ValidationContext.() -> Unit) = validate(DEFAULT_NAME, fn)


/**
 * Use the given fn to validate a matrix with the given name. Return either the matrix itself or a copy that
 * has been transformed to match the validation rules.
 *
 * <pre>
 * {@code
 * myMatrix.validate("myMatrix") { 1 x 4 } // validates myMatrix is 1 by 4.
 * }
 * </pre>
 *
 * @param name The name of the matrix (used in displayed errors)
 * @param fn Validation rules for the matrix.
 */
fun Matrix<Double>.validate(name: String, fn: ValidationContext.() -> Unit) {
    val matrix = this
    koma.util.validation.validate { matrix(name, fn) }
}
