package com.rikin.hydrohomie.app.workflow.workers

import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.common.domain.HYDRATION_LIMIT
import com.rikin.hydrohomie.app.common.domain.toWeekday
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.drinks.DrinkRepository
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.squareup.workflow1.Worker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InitialLoadWorker(
  private val dates: Dates,
  private val drinkRepository: DrinkRepository
) : Worker<AppState> {
  override fun run(): Flow<AppState> = flow {
    val drink = drinkRepository.getDrink(day = dates.today)
    val appState = AppState(
      weekday = dates.dayOfWeek.toWeekday(),
      hydrations = buildList {
        repeat(HYDRATION_LIMIT) { index ->
          if (index == dates.dayOfWeek) {
            add(HydrationState(drank = drink.count, goal = drink.goal))
          } else if (index < dates.dayOfWeek) {
            add(HydrationState(drank = 64.0))
          } else {
            add(HydrationState())
          }
        }
      }
    )
    emit(appState)
  }
}