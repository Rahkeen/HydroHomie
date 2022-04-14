package com.rikin.hydrohomie.drinks

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface DrinkRepository {
  suspend fun getDrink(day: String): DrinkModel
  suspend fun updateDrink(day: String, drink: DrinkModel)
  suspend fun updateCount(day: String, count: Double)
  suspend fun updateGoal(day: String, goal: Double)
}

data class DrinkModel(
  val count: Double,
  val goal: Double
)

class RealDrinkRepository(private val store: FirebaseFirestore) : DrinkRepository {
  override suspend fun getDrink(day: String): DrinkModel {
    return withContext(Dispatchers.IO) {
      val document = store
        .collection("drinks")
        .document(day)
        .get()
        .await()

      val count = (document.data?.get("count") ?: 0.0) as Double
      val goal = (document.data?.get("goal") ?: 64.0) as Double
      DrinkModel(count, goal)
    }
  }

  override suspend fun updateDrink(day: String, drink: DrinkModel) {
    withContext(Dispatchers.IO) {
      store
        .collection("drinks")
        .document(day)
        .set(
          hashMapOf<String, Any>(
            "count" to drink.count,
            "goal" to drink.goal,
            "date" to day
          )
        )
        .await()
    }
  }

  override suspend fun updateCount(day: String, count: Double) {
    withContext(Dispatchers.IO) {
      store
        .collection("drinks")
        .document(day)
        .update("count", count)
        .await()
    }
  }

  override suspend fun updateGoal(day: String, goal: Double) {
    withContext(Dispatchers.IO) {
      store
        .collection("drinks")
        .document(day)
        .update("goal", goal)
        .await()
    }
  }
}

class FakeDrinkRepository: DrinkRepository {
  override suspend fun getDrink(day: String): DrinkModel {
    return DrinkModel(0.0, 64.0)
  }

  override suspend fun updateDrink(day: String, drink: DrinkModel) {
  }

  override suspend fun updateCount(day: String, count: Double) {
  }

  override suspend fun updateGoal(day: String, goal: Double) {
  }
}