package ru.xpendence.jdbc.repository

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import ru.xpendence.jdbc.JdbcApplicationTests
import java.util.UUID
import kotlin.test.assertTrue

class RestaurantRepositoryTest : JdbcApplicationTests() {

    @Autowired
    private lateinit var restaurantRepository: RestaurantRepository

    @Test
    @DisplayName("getRestaurants(): проблема N + 1. Проверяем, был ли пользователь в ресторане")
    @Transactional
    fun getRestaurants() {
        val userId = UUID.fromString("1031f963-957c-425f-962e-767080a699cb")
        val restaurants = restaurantRepository.getAllByUserOrdered(userId)

        assertTrue { restaurants.map { it.name }.contains("Три поросёнка") }
    }
}