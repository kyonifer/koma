find ../golem-core -type f | xargs sed -i '/^@file/d'
find ../golem-core -type f | xargs sed -i '/^@JvmName/d'
find ../golem-core -type f | xargs sed -i 's/@JvmOverloads//g'
find ../golem-core -type f | xargs sed -i 's/@JvmField//g'

rm golem-core/src/golem/plot.kt