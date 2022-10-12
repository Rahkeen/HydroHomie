package com.rikin.hydrohomie.drinks

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

class LocalDrinkRepository(private val localDrinkDao: LocalDrinkDao): DrinkRepository {
  override suspend fun getDrink(day: String): LocalDrink {
    return localDrinkDao.getDrink(day)
  }

  override suspend fun getDrinksForRange(
    startDate: String,
    endDate: String
  ): Map<String, LocalDrink> {
    return localDrinkDao
      .getDrinksForRange(startDate, endDate)
      .associateBy { it.date }
  }

  override suspend fun updateDrink(day: String, drink: LocalDrink) {
    return localDrinkDao.insertDrink(drink)
  }
}

@Entity
data class LocalDrink(
  @PrimaryKey @ColumnInfo(name = "date")
  val date: String,
  @ColumnInfo(name = "count")
  val count: Int,
  @ColumnInfo(name = "goal")
  val goal: Int
)

@Dao
interface LocalDrinkDao {
  @Query("SELECT * FROM localDrink WHERE date IS :date")
  suspend fun getDrink(date: String): LocalDrink

  @Query("SELECT * FROM localDrink WHERE date BETWEEN :startDate AND :endDate")
  suspend fun getDrinksForRange(startDate: String, endDate: String): List<LocalDrink>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDrink(drink: LocalDrink)
}
