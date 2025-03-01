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

    override fun insert(restaurant: Restaurant): UUID =
        UUID.randomUUID()
            .also { id ->
                jdbcTemplate.update(
                    """
                        INSERT INTO restaurants (id, name)
                        VALUES(:id, :name)
                    """.trimIndent(),
                    mapOf(
                        "id" to id,
                        "name" to restaurant.name
                    )
                )
            }

    override fun update(restaurant: Restaurant) {
        jdbcTemplate.update(
            """
                UPDATE restaurants
                SET name = :name
                WHERE id = :id
            """.trimIndent(),
            mapOf(
                "id" to restaurant.id!!,
                "name" to restaurant.name
            )
        )
    }

    override fun findById(id: UUID): Restaurant? =
        jdbcTemplate.query(
            """
                SELECT restaurants.id, restaurants.name
                FROM restaurants
                WHERE id = :id
            """.trimIndent(),
            mapOf("id" to id)
        ) { rs, _ -> rs.toRestaurant() }
            .singleOrNull()

    override fun deleteById(id: UUID) {
        jdbcTemplate.update(
            """
                DELETE FROM restaurants
                WHERE id = :id
            """.trimIndent(),
            mapOf("id" to id)
        )
    }

    override fun getAllByUserOrdered(userId: UUID): List<Restaurant> =
        jdbcTemplate.query(
            """
                SELECT restaurants.id, restaurants.name
                FROM restaurants
                    RIGHT JOIN dishes ON restaurants.id = dishes.restaurant_id
                    RIGHT JOIN orders ON dishes.id = orders.dish_id
                WHERE orders.user_id = :userId
            """.trimIndent(),
            mapOf("userId" to userId)
        ) { rs, _ -> rs.toRestaurant() }

    override fun getName(id: UUID): String =
        jdbcTemplate.query(
            """
                SELECT r.name
                FROM restaurants r
                WHERE r.id = :id
            """.trimIndent(),
            mapOf("id" to id)
        ) { rs, _ -> rs.getString("name") }
            .single()
}