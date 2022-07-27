package com.rikin.hydrohomie.dates

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

interface Dates {
  val today: String
  val dayOfWeek: Int

  fun currentWeek(): List<String>
}


class RealDates(private val formatter: DateTimeFormatter) : Dates {
  override val today: String = LocalDate.now().format(formatter)
  override val dayOfWeek: Int = LocalDate.now().dayOfWeek.value - 1

  override fun currentWeek(): List<String> {
    val week = mutableListOf<String>()
    val monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    for (i in 0..6) {
      val date = monday.plusDays(i.toLong()).format(formatter)
      week.add(date)
    }
    return week
  }
}

class FakeDates : Dates {
  override val today: String = "2023-01-08"
  override val dayOfWeek: Int = 6

  override fun currentWeek(): List<String> {
    return listOf(
      "2023-01-08",
      "2023-01-07",
      "2023-01-06",
      "2023-01-05",
      "2023-01-04",
      "2023-01-03",
      "2023-01-02",
    )
  }
}

