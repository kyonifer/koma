package koma.platformsupport

import java.util.*

var seed: Long? = null
    get() = field
    set(value) {
        field = value
        value?.let {
            rng.setSeed(it)
        }
    }
val rng = Random()