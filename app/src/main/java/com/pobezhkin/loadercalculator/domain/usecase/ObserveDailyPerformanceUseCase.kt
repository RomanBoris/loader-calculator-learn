package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.percent.Rates
import com.pobezhkin.loadercalculator.domain.percent.calcPerformancePercent
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveDailyPerformanceUseCase @Inject constructor(
    private val repo: LoaderRepository
) {
    operator fun invoke(
        hoursWorked: Double = 11.0,
        rates: Rates = Rates()
    ): Flow<Double> =
        combine(
            repo.getAllTrucks(),
            repo.getAllMiniTrucks(),
            repo.getAllUploads()
        ) { trucks, minis, uploads ->
            val l20 = trucks.sumOf { it.h_unit }                 // включает мороз
            val l20Freeze = trucks.sumOf { it.fz_h_unit }

            val lSmall = minis.sumOf { it.mini_eo }              // включает мороз
            val lSmallFreeze = minis.sumOf { it.mini_fz_eo }

            val receive = uploads.sumOf { it.upload }            // приемка (развозка подразумевается)
            val freezeFromLoad = l20Freeze + lSmallFreeze        // мороз только из загрузок

            calcPerformancePercent(
                hoursWorked = hoursWorked,
                palletsLoad20 = l20,
                palletsLoadSmall = lSmall,
                palletsReceive = receive,
                palletsFreezeFromLoad = freezeFromLoad,
                rates = rates
            )
        }
}