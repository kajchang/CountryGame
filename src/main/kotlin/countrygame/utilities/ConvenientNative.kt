package countrygame.utilities

fun nativeArray(I: Iterable<Any>): dynamic {
    val result = js("[]")

    for (i in I) {
        result.push(i)
    }

    return result
}

fun nativeObject(M: Map<dynamic, dynamic>): dynamic {
    val result = js("{}")

    for (k in M.keys) {
        result[k] = M[k]
    }

    return result
}
