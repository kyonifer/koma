# Module koma
# Package koma
A set of toplevel functions which provide a scientific environment similar to NumPy or MATLAB.
# Package koma.containers
A set of useful containers.
# Package koma.matrix
Interfaces for the abstract Matrix and MatrixFactory types which are implemented by various computation backends.
# Package koma.matrix.common
Generic algorithms which apply to any implementation of a Matrix.
# Package koma.matrix.ejml
An implementation of Matrix using the Efficient Java Matrix Library.
# Package koma.matrix.ejml.backend
A set of convenient wrappers for using EJML standalone if ever needed.
# Package koma.matrix.jblas
An implementation of Matrix using the Java BLAS package.
# Package koma.matrix.jblas.backend
A set of convenient wrappers for using jBLAS standalone if ever needed.
# Package koma.matrix.mtj
An implementation of Matrix using the Matrix Toolkit for Java.
# Package koma.matrix.mtj.backend
A set of convenient wrappers for using MTJ standalone if ever needed.
# Package koma.util
Utility functions.
# Package koma.util.test
Utility functions for writing tests with matrices.
# Package koma.util.validation
A set of tools for automatically validating matrix dimensions, bounds, and other properties.
# Package koma.platformsupport
Code which needs to change when switching between jvm, js, and native backends
# Package koma.polyfill
Implementations of parts of the stdlib which don't exist on all platforms