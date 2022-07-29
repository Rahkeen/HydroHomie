package com.rikin.hydrohomie.settings

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface SettingsRepository {
  suspend fun getSettings(): SettingsModel
  suspend fun updateDrinkSize(drinkSize: Double)
  suspend fun updateGoal(goal: Double)
}

data class SettingsModel(
  val drinkSize: Double,
  val goal: Double,
  val userImageUrl: String,
)

class FakeSettingsRepository : SettingsRepository {
  override suspend fun getSettings(): SettingsModel {
    return SettingsModel(drinkSize = 8.0, goal = 64.0, userImageUrl = "")
  }

  override suspend fun updateDrinkSize(drinkSize: Double) {
  }

  override suspend fun updateGoal(goal: Double) {
  }

}

class RealSettingsRepository(private val store: FirebaseFirestore) : SettingsRepository {
  override suspend fun getSettings(): SettingsModel {
    return withContext(Dispatchers.IO) {
      val document = store
        .collection("settings")
        .document("rikin")
        .get()
        .await()

      val drinkSize = (document.data?.get("drink_size") ?: 0.0) as Double
      val goal = (document.data?.get("goal") ?: 0.0) as Double
      val imageUrl = (document.data?.get("user_image_url") ?: 0.0) as String
      SettingsModel(drinkSize, goal, imageUrl)
    }
  }

  override suspend fun updateDrinkSize(drinkSize: Double) {
    withContext(Dispatchers.IO) {
      store
        .collection("settings")
        .document("rikin")
        .update("drink_size", drinkSize)
        .await()
    }
  }

  override suspend fun updateGoal(goal: Double) {
    withContext(Dispatchers.IO) {
      store
        .collection("settings")
        .document("rikin")
        .update("goal", goal)
        .await()
    }
  }
}
