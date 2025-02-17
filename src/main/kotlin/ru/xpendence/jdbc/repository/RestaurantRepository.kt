package ru.xpendence.jdbc.repository

import ru.xpendence.jdbc.domain.Restaurant
import java.util.UUID

interface RestaurantRepository {

    fun getAllByUserOrdered(userId: UUID): List<Restaurant>
}