g = require('golem-backend-purekt')

fac = new g.golem.matrix.purekt.DoublePureKtMatrixFactory
m=fac.randn(3,3)

console.log(m)
console.log(m.plus(m.timesScalar(5)))
 
