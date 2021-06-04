import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File

//TODO add test to show we handle errors properly
@RunWith(JUnit4::class)
class testParseTest {
    lateinit var lines: List<String>

    @Before
    fun setup() {
        lines = File("input.txt").bufferedReader().readLines()
    }

    @Test
    fun testGetWellFormattedValues() {
        assertEquals(lines[0].getWellFormattedValues().toString(), listOf("30", "1", "/bin/run_me_daily").toString())
        assertEquals(lines[1].getWellFormattedValues().toString(), listOf("03", "10", "/bin/run_me_daily").toString())
        assertEquals(lines[2].getWellFormattedValues().toString(), listOf("03", "*", "/bin/run_me_hourly").toString())
        assertEquals(lines[5].getWellFormattedValues(), null)
        assertEquals(lines[6].getWellFormattedValues(), listOf("*","*", "/bin/run_me_every_minute"))
        assertEquals(lines[7].getWellFormattedValues(), listOf("*","19", "/bin/run_me_sixty_times"))
    }
}