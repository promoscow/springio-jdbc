package ru.xpendence.jdbc.repository

import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import ru.xpendence.jdbc.JdbcApplicationTests
import ru.xpendence.jdbc.domain.Restaurant
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RestaurantRepositoryTest : JdbcApplicationTests() {

    @Autowired
    private lateinit var repository: RestaurantRepository

    @Test
    @DisplayName("getRestaurants(): проблема N + 1. Проверяем, был ли пользователь в ресторане")
    @Transactional
    fun getRestaurants() {
        val userId = UUID.fromString("1031f963-957c-425f-962e-767080a699cb")
        val restaurants = repository.getAllByUserOrdered(userId)

        assertTrue { restaurants.map { it.name }.contains("Три поросёнка") }
    }

    @Test
    fun insert() {
        //given
        val restaurant = Restaurant(
            name = RandomStringUtils.secure().nextAlphanumeric(32)
        )
        //when
        val savedId = repository.insert(restaurant)
        //then
        repository.findById(savedId)
            .also { found ->
                assertNotNull(found)
                assertEquals(savedId, found.id)
                assertEquals(restaurant.name, found.name)
            }
        //after
        repository.deleteById(savedId)
    }

    @Test
    fun update() {
        //given
        val restaurant = Restaurant(
            name = RandomStringUtils.secure().nextAlphanumeric(32)
        )
        val savedId = repository.insert(restaurant)
        //when
        val saved = repository.findById(savedId)!!
        val newName = RandomStringUtils.secure().nextAlphanumeric(32)
        val toUpdate = saved.copy(name = newName)
        repository.update(toUpdate)
        //then
        repository.findById(savedId)
            .also { found ->
                assertEquals(newName, found!!.name)
            }
        //after
        repository.deleteById(savedId)
    }

    @Test
    fun findById() {
        //given
        val restaurant = Restaurant(
            name = RandomStringUtils.secure().nextAlphanumeric(32)
        )
        val savedId = repository.insert(restaurant)
        //when
        repository.findById(savedId)
            .also { found ->
                //then
                assertNotNull(found)
                assertEquals(savedId, found.id)
            }
        //after
        repository.deleteById(savedId)
    }

    @Test
    fun deleteById() {
        //given
        val restaurant = Restaurant(
            name = RandomStringUtils.secure().nextAlphanumeric(32)
        )
        val savedId = repository.insert(restaurant)
        //when
        repository.deleteById(savedId)
        //then
        repository.findById(savedId).also { assertNull(it) }
    }

    @Test
    fun getName() {
        //given
        val restaurant = Restaurant(
            name = RandomStringUtils.secure().nextAlphanumeric(32)
        )
        val savedId = repository.insert(restaurant)
        //when
        repository.getName(savedId)
            .also { found ->
                //then
                assertEquals(restaurant.name, found)
            }
        //after
        repository.deleteById(savedId)
    }
}