package koma.internal

actual annotation class KomaNonFrozen()

actual typealias JvmName = kotlin.jvm.JvmName
actual typealias JvmMultifileClass = kotlin.jvm.JvmMultifileClass
actual typealias KomaJvmName = kotlin.jvm.JvmName
actual typealias KomaJvmMultifileClass = kotlin.jvm.JvmMultifileClass

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
actual annotation class KomaJsName(actual val name: String)


