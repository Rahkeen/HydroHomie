package com.rikin.hydrohomie.app.domain

import com.google.firebase.firestore.FirebaseFirestore
import com.rikin.hydrohomie.dates.Dates

data class AppEnvironment(
  val firestore: FirebaseFirestore,
  val dates: Dates,
)
