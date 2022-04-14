package com.rikin.hydrohomie.dates

import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface Dates {
  val today: String
  val dayOfWeek: Int
}

class RealDates(formatter: DateTimeFormatter): Dates {
  override val today: String = LocalDate.now().format(formatter)
  override val dayOfWeek: Int = LocalDate.now().dayOfWeek.value-1
}

class FakeDates(): Dates {
  override val today: String = "01-08-2023"
  override val dayOfWeek: Int = 6
}

