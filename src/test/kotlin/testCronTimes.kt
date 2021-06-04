import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File

@RunWith(JUnit4::class)
class testCronTimes {

    lateinit var lines: List<String>

    @Before
    fun setup() {
        lines = File("input.txt").bufferedReader().readLines()
    }

    @Test
    fun testTodayTomorrow() {
    //TODO test the today tomorrow logic
    }

    @Test
    fun testStar() {
        //TODO test the star for hour and for minute logic
    }
}