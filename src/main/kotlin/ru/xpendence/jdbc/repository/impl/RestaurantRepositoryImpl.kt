package ru.xpendence.jdbc.repository.impl

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.xpendence.jdbc.domain.Restaurant
import ru.xpendence.jdbc.repository.RestaurantRepository
import ru.xpendence.jdbc.repository.mapper.toRestaurant
import java.util.UUID

@Repository
class RestaurantRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : RestaurantRepository {

    override fun getAllByUserOrdered(userId: UUID): List<Restaurant> =
        jdbcTemplate.query(
            """
                SELECT restaurants.id, restaurants."name"
                FROM restaurants
                    RIGHT JOIN dishes ON restaurants.id = dishes.restaurant_id
                    RIGHT JOIN orders ON dishes.id = orders.dish_id
                WHERE orders.user_id = :userId;
            """.trimIndent(),
            mapOf("userId" to userId)
        ) { rs, _ -> rs.toRestaurant() }
}