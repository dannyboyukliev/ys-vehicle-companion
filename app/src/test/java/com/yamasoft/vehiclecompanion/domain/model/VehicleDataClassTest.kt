package com.yamasoft.vehiclecompanion.domain.model

import org.junit.Test
import org.junit.Assert.*

class VehicleDataClassTest {

    @Test
    fun `vehicle creation with all required fields should succeed`() {
        // Given
        val name = "My Car"
        val make = "Toyota"
        val model = "Camry"
        val year = 2023

        // When
        val vehicle = Vehicle(
            name = name,
            make = make,
            model = model,
            year = year
        )

        // Then
        assertEquals(0L, vehicle.id) // Default value
        assertEquals(name, vehicle.name)
        assertEquals(make, vehicle.make)
        assertEquals(model, vehicle.model)
        assertEquals(year, vehicle.year)
        assertNull(vehicle.heroImagePath) // Default value
    }

    @Test
    fun `vehicle creation with custom id and hero image should succeed`() {
        // Given
        val id = 123L
        val name = "Sports Car"
        val make = "BMW"
        val model = "M3"
        val year = 2024
        val heroImagePath = "/path/to/image.jpg"

        // When
        val vehicle = Vehicle(
            id = id,
            name = name,
            make = make,
            model = model,
            year = year,
            heroImagePath = heroImagePath
        )

        // Then
        assertEquals(id, vehicle.id)
        assertEquals(name, vehicle.name)
        assertEquals(make, vehicle.make)
        assertEquals(model, vehicle.model)
        assertEquals(year, vehicle.year)
        assertEquals(heroImagePath, vehicle.heroImagePath)
    }

    @Test
    fun `vehicle creation with empty strings should succeed`() {
        // Given
        val name = ""
        val make = ""
        val model = ""
        val year = 0

        // When
        val vehicle = Vehicle(
            name = name,
            make = make,
            model = model,
            year = year
        )

        // Then
        assertEquals("", vehicle.name)
        assertEquals("", vehicle.make)
        assertEquals("", vehicle.model)
        assertEquals(0, vehicle.year)
    }

    @Test
    fun `vehicle creation with negative year should succeed`() {
        // Given
        val year = -1

        // When
        val vehicle = Vehicle(
            name = "Classic Car",
            make = "Ford",
            model = "Model T",
            year = year
        )

        // Then
        assertEquals(year, vehicle.year)
    }

    @Test
    fun `vehicle creation with very old year should succeed`() {
        // Given
        val year = 1886 // First automobile year

        // When
        val vehicle = Vehicle(
            name = "Historic Car",
            make = "Benz",
            model = "Patent-Motorwagen",
            year = year
        )

        // Then
        assertEquals(year, vehicle.year)
    }

    @Test
    fun `vehicle creation with future year should succeed`() {
        // Given
        val year = 2030

        // When
        val vehicle = Vehicle(
            name = "Future Car",
            make = "Tesla",
            model = "Cybertruck",
            year = year
        )

        // Then
        assertEquals(year, vehicle.year)
    }

    @Test
    fun `vehicle equality should work correctly`() {
        // Given
        val vehicle1 = Vehicle(
            id = 1L,
            name = "Car 1",
            make = "Honda",
            model = "Civic",
            year = 2022,
            heroImagePath = "/path/image.jpg"
        )
        val vehicle2 = Vehicle(
            id = 1L,
            name = "Car 1",
            make = "Honda",
            model = "Civic",
            year = 2022,
            heroImagePath = "/path/image.jpg"
        )
        val vehicle3 = Vehicle(
            id = 2L,
            name = "Car 1",
            make = "Honda",
            model = "Civic",
            year = 2022,
            heroImagePath = "/path/image.jpg"
        )

        // Then
        assertEquals(vehicle1, vehicle2)
        assertNotEquals(vehicle1, vehicle3)
        assertEquals(vehicle1.hashCode(), vehicle2.hashCode())
        assertNotEquals(vehicle1.hashCode(), vehicle3.hashCode())
    }

    @Test
    fun `vehicle toString should contain all properties`() {
        // Given
        val vehicle = Vehicle(
            id = 42L,
            name = "Test Car",
            make = "Nissan",
            model = "Altima",
            year = 2021,
            heroImagePath = "/test/path.jpg"
        )

        // When
        val toString = vehicle.toString()

        // Then
        assertTrue(toString.contains("42"))
        assertTrue(toString.contains("Test Car"))
        assertTrue(toString.contains("Nissan"))
        assertTrue(toString.contains("Altima"))
        assertTrue(toString.contains("2021"))
        assertTrue(toString.contains("/test/path.jpg"))
    }

    @Test
    fun `vehicle copy should work correctly`() {
        // Given
        val originalVehicle = Vehicle(
            id = 1L,
            name = "Original",
            make = "Mazda",
            model = "CX-5",
            year = 2020,
            heroImagePath = "/original.jpg"
        )

        // When
        val copiedVehicle = originalVehicle.copy(
            name = "Modified",
            year = 2021
        )

        // Then
        assertEquals(originalVehicle.id, copiedVehicle.id)
        assertEquals("Modified", copiedVehicle.name)
        assertEquals(originalVehicle.make, copiedVehicle.make)
        assertEquals(originalVehicle.model, copiedVehicle.model)
        assertEquals(2021, copiedVehicle.year)
        assertEquals(originalVehicle.heroImagePath, copiedVehicle.heroImagePath)
    }

    @Test
    fun `vehicle with special characters in strings should work`() {
        // Given
        val name = "My Car! @#$%^&*()"
        val make = "Audi-Volkswagen"
        val model = "A4 Quattro S-Line"

        // When
        val vehicle = Vehicle(
            name = name,
            make = make,
            model = model,
            year = 2023
        )

        // Then
        assertEquals(name, vehicle.name)
        assertEquals(make, vehicle.make)
        assertEquals(model, vehicle.model)
    }

    @Test
    fun `vehicle with unicode characters should work`() {
        // Given
        val name = "我的车"
        val make = "Peugeot"
        val model = "508"

        // When
        val vehicle = Vehicle(
            name = name,
            make = make,
            model = model,
            year = 2023
        )

        // Then
        assertEquals(name, vehicle.name)
        assertEquals(make, vehicle.make)
        assertEquals(model, vehicle.model)
    }

    @Test
    fun `vehicle with very long strings should work`() {
        // Given
        val longName = "A".repeat(1000)
        val longMake = "B".repeat(500)
        val longModel = "C".repeat(300)

        // When
        val vehicle = Vehicle(
            name = longName,
            make = longMake,
            model = longModel,
            year = 2023
        )

        // Then
        assertEquals(longName, vehicle.name)
        assertEquals(longMake, vehicle.make)
        assertEquals(longModel, vehicle.model)
    }
}
