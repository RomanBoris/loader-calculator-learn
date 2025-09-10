package com.pobezhkin.loadercalculator.domain.percent

data class Rates(
    val load20Rate: Double = 25.0,
    val loadSmallRate: Double = 20.0,
    val uploadCombinedPerPallet: Double = (1.0 / 25.0 + 1.0 / 50.0), // 0.06
    val freezeBonusPerPalletHours: Double = 0.02
)

fun calcPerformancePercent(
    hoursWorked: Double,
    palletsLoad20: Int,
    palletsLoadSmall: Int,
    palletsReceive: Int,
    palletsFreezeFromLoad: Int, // мороз только из L20+LSmall
    rates: Rates = Rates()
): Double {
    require(hoursWorked > 0) { "hoursWorked must be > 0" }

    val totalLoaded = (palletsLoad20 + palletsLoadSmall).coerceAtLeast(0)
    val freezeClamped = palletsFreezeFromLoad.coerceIn(0, totalLoaded)

    val normHours =
        (palletsLoad20 / rates.load20Rate) +
                (palletsLoadSmall / rates.loadSmallRate) +
                (palletsReceive * rates.uploadCombinedPerPallet) +
                (freezeClamped * rates.freezeBonusPerPalletHours)

    return (normHours / hoursWorked) * 100.0
}