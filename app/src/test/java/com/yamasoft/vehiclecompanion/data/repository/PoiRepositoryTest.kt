package com.yamasoft.vehiclecompanion.data.repository

import com.yamasoft.vehiclecompanion.data.local.dao.PoiDao
import com.yamasoft.vehiclecompanion.data.remote.api.RoadtrippersApiService
import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDiscoverResponse
import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDto
import com.yamasoft.vehiclecompanion.domain.model.Poi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Response

class PoiRepositoryTest {

    private lateinit var mockApiService: RoadtrippersApiService
    private lateinit var mockPoiDao: PoiDao
    private lateinit var poiRepository: PoiRepositoryImpl

    @Before
    fun setup() {
        mockApiService = mockk()
        mockPoiDao = mockk()
        poiRepository = PoiRepositoryImpl(mockApiService, mockPoiDao)
    }

    @Test
    fun `discoverPois should return success with pois when api call is successful`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val mockPoiDto = PoiDto(
            id = 1,
            name = "Test Place",
            url = "https://example.com",
            primaryCategoryDisplayName = "Restaurant",
            rating = 4.5,
            imageUrl = "https://example.com/image.jpg",
            location = listOf(40.7128, -74.0060)
        )
        val mockResponse = PoiDiscoverResponse(pois = listOf(mockPoiDto))
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse
        coEvery { mockPoiDao.isPoiFavorite(1) } returns false

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val pois = result.getOrNull()
        assertNotNull(pois)
        assertEquals(1, pois?.size)
        assertEquals("Test Place", pois?.first()?.name)
        assertEquals("Restaurant", pois?.first()?.category)
        assertFalse(pois?.first()?.isFavorite ?: true)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
        coVerify { mockPoiDao.isPoiFavorite(1) }
    }

    @Test
    fun `discoverPois should return success with empty list when api returns empty response`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val emptyResponse = PoiDiscoverResponse(pois = emptyList())
        val successfulResponse = Response.success(emptyResponse)

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val pois = result.getOrNull()
        assertNotNull(pois)
        assertTrue(pois?.isEmpty() ?: false)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `discoverPois should return success with empty list when api returns null body`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val successfulResponseWithNullBody = Response.success<PoiDiscoverResponse>(null)

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponseWithNullBody

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val pois = result.getOrNull()
        assertNotNull(pois)
        assertTrue(pois?.isEmpty() ?: false)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `discoverPois should return failure when api call returns error response`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val errorResponse = Response.error<PoiDiscoverResponse>(
            404,
            okhttp3.ResponseBody.create(null, "Not Found")
        )

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } returns errorResponse

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception?.message?.contains("404") ?: false)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `discoverPois should return failure when api call throws exception`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50
        val networkException = Exception("Network error")

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } throws networkException

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertEquals(networkException, exception)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `discoverPois should update favorite status from local database`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val mockPoiDto1 = PoiDto(
            id = 1,
            name = "Favorite Place",
            url = "https://example.com",
            primaryCategoryDisplayName = "Restaurant",
            rating = 4.5,
            imageUrl = "https://example.com/image1.jpg",
            location = listOf(40.7128, -74.0060)
        )
        val mockPoiDto2 = PoiDto(
            id = 2,
            name = "Regular Place",
            url = "https://example.com",
            primaryCategoryDisplayName = "Shop",
            rating = 3.8,
            imageUrl = "https://example.com/image2.jpg",
            location = listOf(40.7200, -74.0100)
        )
        val mockResponse = PoiDiscoverResponse(pois = listOf(mockPoiDto1, mockPoiDto2))
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse
        coEvery { mockPoiDao.isPoiFavorite(1) } returns true  // First POI is favorite
        coEvery { mockPoiDao.isPoiFavorite(2) } returns false // Second POI is not favorite

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val pois = result.getOrNull()
        assertNotNull(pois)
        assertEquals(2, pois?.size)

        // Check favorite status is correctly set
        val favoritePoi = pois?.find { it.id == 1 }
        val regularPoi = pois?.find { it.id == 2 }

        assertTrue(favoritePoi?.isFavorite ?: false)
        assertFalse(regularPoi?.isFavorite ?: true)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
        coVerify { mockPoiDao.isPoiFavorite(1) }
        coVerify { mockPoiDao.isPoiFavorite(2) }
    }

    @Test
    fun `discoverPois should handle pois with null optional fields`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val mockPoiDto = PoiDto(
            id = 1,
            name = "Minimal Place",
            url = null,
            primaryCategoryDisplayName = null,
            rating = null,
            imageUrl = null,
            location = null
        )
        val mockResponse = PoiDiscoverResponse(pois = listOf(mockPoiDto))
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse
        coEvery { mockPoiDao.isPoiFavorite(1) } returns false

        // When
        val result = poiRepository.discoverPois(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val pois = result.getOrNull()
        assertNotNull(pois)
        assertEquals(1, pois?.size)

        val poi = pois?.first()
        assertEquals("Minimal Place", poi?.name)
        assertNull(poi?.category)
        assertNull(poi?.rating)
        assertNull(poi?.imageUrl)
        assertNull(poi?.latitude)
        assertNull(poi?.longitude)

        coVerify { mockApiService.discoverPois(swCorner, neCorner, pageSize) }
        coVerify { mockPoiDao.isPoiFavorite(1) }
    }
}