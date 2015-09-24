/**
 * This file defines the default backend. Change the property below to use another
 * linear algebra library.
 */

package golem

val factory = org.ujmp.jblas.JBlasDenseDoubleMatrix2DFactory()
