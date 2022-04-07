package com.rikin.hydrohomie.drinkrepo

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface DrinkRepository {
  suspend fun getDrink(day: String): DrinkModel
  suspend fun updateDrink(day: String, drink: DrinkModel)
  suspend fun updateCount(day: String, count: Double)
}

data class DrinkModel(
  val count: Double
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
      DrinkModel(count)
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
}