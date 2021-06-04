import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.lang.IllegalStateException
import kotlin.jvm.Throws

object TimeFormatterUtils {

    @Throws(UnsupportedOperationException::class, IllegalArgumentException::class, IllegalStateException::class)
    fun getTimeWithHoursAndMinutes(timeInput: String): DateTime? {
        val dtf: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
        val simulatedHour = dtf.parseDateTime(timeInput).hourOfDay
        val simulatedMinute = dtf.parseDateTime(timeInput).minuteOfHour
        return DateTime.now()
            .withHourOfDay(simulatedHour)
            .withMinuteOfHour(simulatedMinute)
            .withSecondOfMinute(0)
            .withMillisOfSecond(0)
    }
}