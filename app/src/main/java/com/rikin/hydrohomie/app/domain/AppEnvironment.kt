package com.rikin.hydrohomie.app.domain

import com.google.firebase.firestore.FirebaseFirestore
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.drinkrepo.DrinkRepository

data class AppEnvironment(
  val store: DrinkRepository,
  val dates: Dates,
)
