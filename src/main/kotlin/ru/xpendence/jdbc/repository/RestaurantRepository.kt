package ru.xpendence.jdbc.repository

import ru.xpendence.jdbc.domain.Restaurant
import java.util.UUID

/**
 * Репозиторий для работы с [Restaurant]
 */
interface RestaurantRepository {

    /**
     * Добавление новой записи о ресторане.
     *
     * @param restaurant - добавляемый ресторан
     * @return идентификатор добавленной записи
     * @see Restaurant
     */
    fun insert(restaurant: Restaurant): UUID

    /**
     * Обновление существующей записи о ресторане.
     *
     * @param restaurant - добавляемый ресторан
     * @see Restaurant
     */
    fun update(restaurant: Restaurant)

    /**
     * Поиск ресторана по идентификатору.
     *
     * @param id - идентификатор ресторана
     * @return найденный ресторан
     * @see Restaurant
     */
    fun findById(id: UUID): Restaurant?

    /**
     * Удаление ресторана по идентификатору.
     *
     * @param id - идентификатор ресторана
     */
    fun deleteById(id: UUID)

    /**
     * Получение всех ресторанов, в которых данный пользователь делал заказ.
     *
     * @param userId - идентификатор пользователя
     * @return список найденных ресторанов
     */
    fun getAllByUserOrdered(userId: UUID): List<Restaurant>

    /**
     * Получение имени ресторана по идентификатору.
     *
     * @param id - идентификатор ресторана
     * @return имя ресторана
     */
    fun getName(id: UUID): String
}