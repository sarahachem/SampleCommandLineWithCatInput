import org.joda.time.DateTime
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main(args: Array<String>) {
    var time: DateTime? = null
    //TODO: add regex to remove empty lines here
    val lines = BufferedReader(InputStreamReader(System.`in`)).readLines().filterNot { it.isNullOrBlank() }
    runCatching {
        val simulatedTime = args[0]
        time = TimeFormatterUtils.getTimeWithHoursAndMinutes(simulatedTime)
    }.onFailure {
        when (it) {
            is IllegalStateException, is IllegalArgumentException -> println("You seem to have provided the wrong date format")
            is ArrayIndexOutOfBoundsException -> println("you didn't provide a date")
            is UnsupportedOperationException -> println(" I can't do what you're asking for because you asked for something I don't support")
            else -> println("Oops Something went wrong ${it.message}")
        }
    }.onSuccess {
        time?.let { time -> lines.forEach { tryToShowNextRunTime(time, it) } }
    }
}