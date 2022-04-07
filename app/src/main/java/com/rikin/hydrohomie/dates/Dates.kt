package com.rikin.hydrohomie.dates

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Dates(formatter: DateTimeFormatter) {
  val today: String = LocalDate.now().format(formatter)
  val dayOfWeek: Int = LocalDate.now().dayOfWeek.value-1
}

