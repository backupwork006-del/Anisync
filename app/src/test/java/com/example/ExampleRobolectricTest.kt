package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.WatchlistEntity
import com.example.data.model.WatchStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("AnimeStream", appName)
    }

    @Test
    fun `watchlist database insert and retrieve`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val entity = WatchlistEntity(
            animeId = "test-solo",
            title = "Solo Leveling",
            posterUrl = "https://example.com/poster.jpg",
            totalEpisodes = 12,
            episodesWatched = 4,
            status = WatchStatus.WATCHING,
            notifyNewEpisodes = true
        )
        db.watchlistDao().insertOrUpdate(entity)
        val retrieved = db.watchlistDao().getWatchlistEntryDirect("test-solo")
        assertNotNull(retrieved)
        assertEquals("Solo Leveling", retrieved?.title)
        assertEquals(4, retrieved?.episodesWatched)
        assertEquals(WatchStatus.WATCHING, retrieved?.status)
    }
}
