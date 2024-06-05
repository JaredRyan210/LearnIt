package com.hfad.finalproject_team_temp

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AlarmTest {

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testOnReceive() {
        val alarm = alarm()
        val intent = Intent()
        alarm.onReceive(mockContext, intent)

        // Verify that createNotificationChannel and showNotification were called
        Mockito.verify(alarm).createNotificationChannel(mockContext)
        Mockito.verify(alarm).showNotification(mockContext)
    }



}
