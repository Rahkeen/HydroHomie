package com.rikin.hydrohomie.drinks

interface DrinkRepository {
  suspend fun getDrink(day: String): LocalDrink
  suspend fun getDrinksForRange(startDate: String, endDate: String): Map<String, LocalDrink>
  suspend fun updateDrink(day: String, drink: LocalDrink)
}


class FakeDrinkRepository : DrinkRepository {
  override suspend fun getDrink(day: String): LocalDrink {
    return LocalDrink("2023-01-08", 0, 64)
  }

  override suspend fun getDrinksForRange(
    startDate: String,
    endDate: String
  ): Map<String, LocalDrink> {
    return mapOf("2023-01-08" to LocalDrink("2023-01-08", 0, 64))
  }

  override suspend fun updateDrink(day: String, drink: LocalDrink) {}
}