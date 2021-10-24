package com.flexship.wordsassociations.common

import java.util.*

object IntentDataHolder {

    private val values: MutableMap<UUID, Any?> by lazy {
        mutableMapOf()
    }

    fun addToStore(target: Any?): UUID {
        val key = UUID.randomUUID()
        values[key] = target
        return key
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getFromStore(key: UUID): T? {
        return try {
            values.remove(key) as T
        } catch (ex: Exception) {
            null
        }
    }
}