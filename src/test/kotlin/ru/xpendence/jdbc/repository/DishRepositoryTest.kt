package ru.xpendence.jdbc.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.xpendence.jdbc.JdbcApplicationTests
import kotlin.test.assertEquals

class DishRepositoryTest : JdbcApplicationTests() {

    @Autowired
    private lateinit var repository: DishRepository

    @Test
    fun countOrderedByName() {
        repository.countOrderedByName("Харчо с изюминкой").also { assertEquals(2, it) }
    }
}