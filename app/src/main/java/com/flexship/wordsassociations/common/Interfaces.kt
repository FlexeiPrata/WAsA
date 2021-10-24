package com.flexship.wordsassociations.common

interface State
interface Action
interface Item {
    fun id(): Any
    fun getUIContent(): MutableList<Any>
}
