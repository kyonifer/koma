/**
 * This file defines the default backend. Change the property below to use another
 * linear algebra library.
 */

package golem

// Default factory to build matrices from
val factory = golem.matrix.ejml.MatFactory()

