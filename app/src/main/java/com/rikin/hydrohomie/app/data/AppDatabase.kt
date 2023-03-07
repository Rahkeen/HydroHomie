package com.rikin.hydrohomie.app.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rikin.hydrohomie.drinks.LocalDrink
import com.rikin.hydrohomie.drinks.LocalDrinkDao
import com.rikin.hydrohomie.settings.LocalSettings
import com.rikin.hydrohomie.settings.LocalSettingsDao

@Database(
  version = 2,
  entities = [LocalDrink::class, LocalSettings::class],
  autoMigrations = [
    AutoMigration(from = 1, to = 2)
  ]
)
abstract class AppDatabase: RoomDatabase() {
  abstract fun localDrinkDao(): LocalDrinkDao
  abstract fun localSettingsDao(): LocalSettingsDao
}

const val DATABASE_NAME = "hydro-homie"