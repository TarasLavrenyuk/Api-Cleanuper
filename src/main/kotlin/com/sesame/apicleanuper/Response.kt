package com.sesame.apicleanuper

class Response(
    private val map: MutableMap<String, Any> = mutableMapOf()
) {
    constructor(vararg pairs: Pair<String, Any>): this() {
        map.putAll(pairs)
    }

    fun addData(vararg data: Pair<String, Any>) {
        map.putAll(data)
    }

    /**
     * @return copy of the existing data as a separate object
     */
    fun getData() = HashMap(map)

    override fun toString(): String {
        return map.toString()
    }
}