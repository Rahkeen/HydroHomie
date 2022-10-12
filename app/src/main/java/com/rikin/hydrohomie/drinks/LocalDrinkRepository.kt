package com.rikin.hydrohomie.drinks

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

class LocalDrinkRepository(private val localDrinkDao: LocalDrinkDao): DrinkRepository {
  override suspend fun getDrink(day: String): DrinkModel {
    return localDrinkDao.getDrink(day).toDrinkModel()
  }

  override suspend fun getDrinksForRange(
    startDate: String,
    endDate: String
  ): Map<String, DrinkModel> {
    return localDrinkDao.getDrinksForRange(startDate, endDate)
      .map { it.toDrinkModel() }
      .associateBy { it.date }
  }

  override suspend fun updateDrink(day: String, drink: DrinkModel) {
    return localDrinkDao.insertDrink(drink.toDrink())
  }
}

@Entity
data class LocalDrink(
  @PrimaryKey @ColumnInfo(name = "date") val date: String,
  @ColumnInfo(name = "count") val count: Int,
  @ColumnInfo(name = "goal") val goal: Int
)

fun LocalDrink.toDrinkModel(): DrinkModel {
  return DrinkModel(
    date = this.date,
    count = this.count,
    goal = this.goal
  )
}

fun DrinkModel.toDrink(): LocalDrink {
  return LocalDrink(
    date = this.date,
    count = this.count,
    goal = this.goal,
  )
}

@Dao
interface LocalDrinkDao {
  @Query("SELECT * FROM localDrink WHERE date IS :date")
  suspend fun getDrink(date: String): LocalDrink

  @Query("SELECT * FROM localDrink WHERE date BETWEEN :startDate AND :endDate")
  suspend fun getDrinksForRange(startDate: String, endDate: String): List<LocalDrink>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDrink(drink: LocalDrink)
}
