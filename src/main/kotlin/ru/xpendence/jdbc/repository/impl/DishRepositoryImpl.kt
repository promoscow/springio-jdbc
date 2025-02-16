package ru.xpendence.jdbc.repository.impl

import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.xpendence.jdbc.repository.DishRepository

@Repository
class DishRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : DishRepository {

    override fun countOrderedByName(name: String): Long =
        jdbcTemplate.query(
            """
                SELECT COUNT(*)
                FROM dishes
                         INNER JOIN restaurants ON restaurants.id = dishes.restaurant_id
                         INNER JOIN orders ON restaurants.id = orders.restaurant_id
                         INNER JOIN users ON users.id = orders.user_id
                WHERE dishes."name" = :name
            """.trimIndent(),
            mapOf("name" to name),
            ResultSetExtractor { rs -> if (rs.next()) { rs.getInt("count") } else null }
        )?.toLong() ?: 0
}