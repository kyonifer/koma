koma = require('koma-core').koma

m = koma.randn(3,3)

console.log(m)
console.log(m.plus(m.timesScalar(5)))

// Force a particular backend

Fac = require('koma-backend-purekt').koma.matrix.purekt.DoublePureKtMatrixFactory
f = new Fac()

m2 = f.ones(3,4)

console.log(m2)
console.log(m2.plus(m2).minusScalar(3))
