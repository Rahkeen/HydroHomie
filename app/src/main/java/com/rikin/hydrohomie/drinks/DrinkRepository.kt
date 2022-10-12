package com.rikin.hydrohomie.drinks

interface DrinkRepository {
  suspend fun getDrink(day: String): DrinkModel
  suspend fun getDrinksForRange(startDate: String, endDate: String): Map<String, DrinkModel>
  suspend fun updateDrink(day: String, drink: DrinkModel)
}

data class DrinkModel(
  val date: String,
  val count: Int,
  val goal: Int
)

class FakeDrinkRepository : DrinkRepository {
  override suspend fun getDrink(day: String): DrinkModel {
    return DrinkModel("2023-01-08", 0, 64)
  }

  override suspend fun getDrinksForRange(
    startDate: String,
    endDate: String
  ): Map<String, DrinkModel> {
    return mapOf("2023-01-08" to DrinkModel("2023-01-08", 0, 64))
  }

  override suspend fun updateDrink(day: String, drink: DrinkModel) {
  }
}