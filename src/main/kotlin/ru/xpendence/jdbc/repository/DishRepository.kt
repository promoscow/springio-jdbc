package ru.xpendence.jdbc.repository

interface DishRepository {

    fun countOrderedByName(name: String): Long
}