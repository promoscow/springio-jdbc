package ru.xpendence.jdbc.repository.mapper

import ru.xpendence.jdbc.domain.Restaurant
import java.sql.ResultSet
import java.util.UUID

fun ResultSet.toRestaurant(): Restaurant = Restaurant(
    id = this.getString("id").let { UUID.fromString(it) },
    name = this.getString("name")
)