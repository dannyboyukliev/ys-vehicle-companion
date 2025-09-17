package com.yamasoft.vehiclecompanion.data.service

import com.yamasoft.vehiclecompanion.data.remote.api.RoadtrippersApiService
import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDiscoverResponse
import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Response

class PlaceServiceTest {

    private lateinit var mockPlaceApiService: RoadtrippersApiService
    private lateinit var placeService: PlaceService

    @Before
    fun setup() {
        mockPlaceApiService = mockk()
        placeService = PlaceService(mockPlaceApiService)
    }

    @Test
    fun `getPlaces should return success with places when api call is successful`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val mockPoiDto = PoiDto(
            id = 1,
            name = "Test Restaurant",
            url = "https://example.com/restaurant",
            primaryCategoryDisplayName = "Restaurant",
            rating = 4.5,
            imageUrl = "https://example.com/restaurant.jpg",
            location = listOf(40.7128, -74.0060)
        )
        val mockResponse = PoiDiscoverResponse(pois = listOf(mockPoiDto))
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val places = result.getOrNull()
        assertNotNull(places)
        assertEquals(1, places?.size)
        assertEquals("Test Restaurant", places?.first()?.name)
        assertEquals("Restaurant", places?.first()?.primaryCategoryDisplayName)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `getPlaces should return success with empty list when api returns empty response`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val emptyResponse = PoiDiscoverResponse(pois = emptyList())
        val successfulResponse = Response.success(emptyResponse)

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val places = result.getOrNull()
        assertNotNull(places)
        assertTrue(places?.isEmpty() ?: false)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `getPlaces should return success with empty list when api returns null body`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val successfulResponseWithNullBody = Response.success<PoiDiscoverResponse>(null)

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponseWithNullBody

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val places = result.getOrNull()
        assertNotNull(places)
        assertTrue(places?.isEmpty() ?: false)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `getPlaces should return failure when api call returns error response`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val errorResponse = Response.error<PoiDiscoverResponse>(
            404,
            okhttp3.ResponseBody.create(null, "Not Found")
        )

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } returns errorResponse

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception?.message?.contains("404") ?: false)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `getPlaces should return failure when api call throws network exception`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50
        val networkException = Exception("Network timeout")

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } throws networkException

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertEquals(networkException, exception)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `getPlaces should handle multiple places in successful response`() = runTest {
        // Given
        val swCorner = "40.7128,-74.0060"
        val neCorner = "40.7589,-73.9851"
        val pageSize = 50

        val mockPoiDto1 = PoiDto(
            id = 1,
            name = "Restaurant A",
            url = "https://example.com/restaurant-a",
            primaryCategoryDisplayName = "Restaurant",
            rating = 4.5,
            imageUrl = "https://example.com/restaurant-a.jpg",
            location = listOf(40.7128, -74.0060)
        )
        val mockPoiDto2 = PoiDto(
            id = 2,
            name = "Shop B",
            url = "https://example.com/shop-b",
            primaryCategoryDisplayName = "Shopping",
            rating = 3.8,
            imageUrl = "https://example.com/shop-b.jpg",
            location = listOf(40.7200, -74.0100)
        )
        val mockResponse = PoiDiscoverResponse(pois = listOf(mockPoiDto1, mockPoiDto2))
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val places = result.getOrNull()
        assertNotNull(places)
        assertEquals(2, places?.size)

        val restaurant = places?.find { it.id == 1 }
        val shop = places?.find { it.id == 2 }

        assertEquals("Restaurant A", restaurant?.name)
        assertEquals("Restaurant", restaurant?.primaryCategoryDisplayName)
        assertEquals("Shop B", shop?.name)
        assertEquals("Shopping", shop?.primaryCategoryDisplayName)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }

    @Test
    fun `getPlaces should handle places with null optional fields`() = runTest {
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

        coEvery { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) } returns successfulResponse

        // When
        val result = placeService.getPlaces(swCorner, neCorner, pageSize)

        // Then
        assertTrue(result.isSuccess)
        val places = result.getOrNull()
        assertNotNull(places)
        assertEquals(1, places?.size)

        val place = places?.first()
        assertEquals("Minimal Place", place?.name)
        assertNull(place?.url)
        assertNull(place?.primaryCategoryDisplayName)
        assertNull(place?.rating)
        assertNull(place?.imageUrl)
        assertNull(place?.location)

        coVerify { mockPlaceApiService.discoverPois(swCorner, neCorner, pageSize) }
    }
}