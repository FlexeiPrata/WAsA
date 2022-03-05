package com.flexship.wordsassociations.common

interface State
interface Intent
interface Item {
    fun id(): Any
    fun getUIContent(): MutableList<Any>
}
