package com.rikin.hydrohomie.drinks

import com.google.firebase.firestore.FirebaseFirestore
import com.rikin.hydrohomie.settings.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface DrinkRepository {
  suspend fun getDrink(day: String): DrinkModel
  suspend fun getDrinksForRange(startDate: String, endDate: String): Map<String, DrinkModel>
  suspend fun updateDrink(day: String, drink: DrinkModel)
  suspend fun updateCount(day: String, count: Double)
  suspend fun updateGoal(day: String, goal: Double)
}

data class DrinkModel(
  val count: Double,
  val goal: Double,
  val date: String,
  val userId: String
)

class RealDrinkRepository(private val store: FirebaseFirestore) : DrinkRepository {
  override suspend fun getDrink(day: String): DrinkModel {
    return withContext(Dispatchers.IO) {
      val document = store
        .collection("drinks")
        .document("$USER_ID-$day")
        .get()
        .await()

      val count = (document.data?.get("count") ?: 0.0) as Double
      val goal = (document.data?.get("goal") ?: 64.0) as Double
      val date = (document.data?.get("date") ?: "") as String
      val userId = (document.data?.get("user_id") ?: "") as String
      DrinkModel(count, goal, date, userId)
    }
  }

  override suspend fun getDrinksForRange(
    startDate: String,
    endDate: String
  ): Map<String, DrinkModel> {
    return withContext(Dispatchers.IO) {
      val query = store
        .collection("drinks")
        .whereEqualTo("user_id", USER_ID)
        .whereGreaterThanOrEqualTo("date", startDate)
        .whereLessThanOrEqualTo("date", endDate)
        .get()
        .await()

      query
        .documents
        .map { document ->
          val count = (document.data?.get("count") ?: 0.0) as Double
          val goal = (document.data?.get("goal") ?: 64.0) as Double
          val date = (document.data?.get("date") ?: "") as String
          val userId = (document.data?.get("user_id") ?: "") as String
          DrinkModel(count, goal, date, userId)
        }
        .associateBy { it.date }
    }
  }

  override suspend fun updateDrink(day: String, drink: DrinkModel) {
    withContext(Dispatchers.IO) {
      store
        .collection("drinks")
        .document("$USER_ID-$day")
        .set(
          hashMapOf<String, Any>(
            "count" to drink.count,
            "goal" to drink.goal,
            "date" to day,
            "user_id" to drink.userId
          )
        )
        .await()
    }
  }

  override suspend fun updateCount(day: String, count: Double) {
    withContext(Dispatchers.IO) {
      store
        .collection("drinks")
        .document("$USER_ID-$day")
        .update("count", count)
        .await()
    }
  }

  override suspend fun updateGoal(day: String, goal: Double) {
    withContext(Dispatchers.IO) {
      store
        .collection("drinks")
        .document("$USER_ID-$day")
        .update("goal", goal)
        .await()
    }
  }
}

class FakeDrinkRepository : DrinkRepository {
  override suspend fun getDrink(day: String): DrinkModel {
    return DrinkModel(0.0, 64.0, "2023-01-08", "rikin")
  }

  override suspend fun getDrinksForRange(
    startDate: String,
    endDate: String
  ): Map<String, DrinkModel> {
    return mapOf("2023-01-08" to DrinkModel(0.0, 64.0, "2023-01-08", "rikin"))
  }

  override suspend fun updateDrink(day: String, drink: DrinkModel) {
  }

  override suspend fun updateCount(day: String, count: Double) {
  }

  override suspend fun updateGoal(day: String, goal: Double) {
  }
}