package com.rikin.hydrohomie.settings

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

class LocalSettingsRepository(private val settingsDao: LocalSettingsDao): SettingsRepository {
  override suspend fun getSettings(): LocalSettings =
    settingsDao.getSettings()

  override suspend fun insertSettings(localSettings: LocalSettings) =
    settingsDao.insertSettings(localSettings)

  override suspend fun updateSettings(localSettings: LocalSettings) =
    settingsDao.updateSettings(localSettings)
}

@Entity
data class LocalSettings(
  @PrimaryKey @ColumnInfo(name = "user_id")
  val userId: Int = 0,
  @ColumnInfo(name = "drink_size")
  val drinkSize: Int = 8,
  val goal: Int = 64,
  @ColumnInfo(name = "user_image_url")
  val userImageUrl: String = ""
)


@Dao
interface LocalSettingsDao {
  @Query("SELECT * FROM localSettings LIMIT 1")
  suspend fun getSettings(): LocalSettings

  @Insert(onConflict = OnConflictStrategy.ABORT)
  suspend fun insertSettings(settings: LocalSettings)

  @Update
  suspend fun updateSettings(settings: LocalSettings)
}