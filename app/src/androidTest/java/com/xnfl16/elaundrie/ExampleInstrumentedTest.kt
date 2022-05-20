package com.xnfl16.elaundrie

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.xnfl16.elaundrie", appContext.packageName)
        val currentDate: ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC)
        val currentTime = Date()

        val sdf = SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z")

// Give it to me in GMT time.

// Give it to me in GMT time.
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        println("GMT time: " + sdf.format(currentTime))
    }

}