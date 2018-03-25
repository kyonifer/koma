
kotlin = require('kotlin')
koma = require('koma_core_api').koma
// Load the default implementation
require('koma_core_implementation').koma

m = koma.randn(3,3)

console.log(m)
console.log(m.plus(m.timesScalar(5)))

m2 = koma.ones(3,4)

console.log(m2)
console.log(m2.plus(m2).minusScalar(3))
