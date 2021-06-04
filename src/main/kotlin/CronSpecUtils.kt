import org.joda.time.DateTime
import java.text.DecimalFormat

fun tryToShowNextRunTime(time: DateTime, line: String) {
    runCatching {
        line.printCronTimeSpecification(time)
    }.onFailure {
        when (it) {
            //TODO better error handling,e.g., in case the second parameter is actually the third
            //TODO handle the case with spaces in the third param, e.g., " / bin /toto /tata"
            //TODO handle the case with wrong formatting such as 2 configs on the same line
            is ArrayIndexOutOfBoundsException -> println("It seems you forgot to specify one of the arguments")
            else -> println("oops we couldn't parse the next running time, ${it.message}")
        }
    }
}

fun String.getWellFormattedValues(): List<String>? {
    var values = split(" ").onEach { trim() }
    return values.filterNot { it.isNullOrBlank() }.takeIf { it.isNotEmpty() }
}

private fun String.printCronTimeSpecification(simulatedDateTime: DateTime) {
    getWellFormattedValues()?.let {
        val df = DecimalFormat("00")
        val dateTime = getDateTimeValue(it[0], it[1], simulatedDateTime)
        println("${dateTime.hourOfDay}:${df.format(dateTime.minuteOfHour)} ${if (dateTime.dayOfYear > simulatedDateTime.dayOfYear) "tomorrow" else "today"} - ${it[2]}")
    }
}

private fun getDateTimeValue(minute: String, hour: String, simulatedDateTime: DateTime): DateTime {
    var cronDateTime = DateTime()
    val minuteIfCorrect = getIfCorrect(minute)
    val hourIfCorrect = getIfCorrect(hour)
    if (minute != "*" && hour != "*") {
        cronDateTime = cronDateTime.withHourOfDay(hourIfCorrect!!).withMinuteOfHour(minuteIfCorrect!!).withMillisOfSecond(0)
    }
    return when {
        //TODO find a better way to handle * values instead of going through each case
        minute == "*" && hour == "*" -> simulatedDateTime

        minute == "*" && hourIfCorrect == simulatedDateTime.hourOfDay -> simulatedDateTime
        minute == "*" && hourIfCorrect!! < simulatedDateTime.hourOfDay -> simulatedDateTime.plusDays(1)
            .withHourOfDay(hourIfCorrect).withMinuteOfHour(0)
        minute == "*" && hourIfCorrect!! > simulatedDateTime.hourOfDay -> simulatedDateTime.withHourOfDay(hourIfCorrect)
            .withMinuteOfHour(0)

        minuteIfCorrect == simulatedDateTime.minuteOfHour && hour == "*" -> simulatedDateTime
        minuteIfCorrect!! < simulatedDateTime.minuteOfHour && hour == "*" -> simulatedDateTime.withMinuteOfHour(
            minuteIfCorrect
        ).plusHours(1)
        minuteIfCorrect > simulatedDateTime.minuteOfHour && hour == "*" -> simulatedDateTime.withMinuteOfHour(
            minuteIfCorrect
        )

        cronDateTime.isEqual(simulatedDateTime) -> simulatedDateTime
        cronDateTime.isBefore(simulatedDateTime) -> cronDateTime.plusDays(1)
        cronDateTime.isAfter(simulatedDateTime) -> cronDateTime
        else -> error(" got a wrong date value")
    }
}

private fun getIfCorrect(value: String): Int? {
    return if (value.toIntOrNull() != null) value.toInt() else null
}
