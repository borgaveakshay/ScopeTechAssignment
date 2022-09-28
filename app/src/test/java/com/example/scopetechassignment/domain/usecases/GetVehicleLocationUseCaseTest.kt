package com.example.scopetechassignment.domain.usecases

import com.example.scopetechassignment.data.models.network.VehicleLocationModel
import com.example.scopetechassignment.data.models.network.VehicleLocationResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.domain.repositories.GetVehicleLocationRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetVehicleLocationUseCaseTest {
    private lateinit var repository: GetVehicleLocationRepository
    private lateinit var getVehicleLocationUseCase: GetVehicleLocationUseCase

    @Before
    fun setUp() {
        repository = mockkClass(GetVehicleLocationRepository::class)
        getVehicleLocationUseCase = GetVehicleLocationUseCase(repository)
    }

    @Test
    fun `given response from repository use case should returns successful response`() {
        //GIVEN
        coEvery { repository.getVehicleLocation(any()) } returns Result.success(
            givenVehicleLocations()
        )
        //WHEN
        var result: Result<VehicleLocationResponse>
        runBlocking {
            result = getVehicleLocationUseCase("1")
        }
        //THEN
        Truth.assertThat(result.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(result).isEqualTo(Result.success(givenVehicleLocations()))
    }

    @Test
    fun `given empty response from repository use case should returns empty response`() {
        //GIVEN
        coEvery { repository.getVehicleLocation(any()) } returns Result.success(
            VehicleLocationResponse(emptyList())
        )
        //WHEN
        var result: Result<VehicleLocationResponse>
        runBlocking {
            result = getVehicleLocationUseCase("1")
        }
        //THEN
        Truth.assertThat(result.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(result).isEqualTo(Result.success(VehicleLocationResponse(emptyList())))
    }

    @Test
    fun `when user id is null use case should throw error`() {
        //GIVEN
        val givenError = "User Id cannot be null"
        //WHEN
        var result: Result<VehicleLocationResponse>
        runBlocking {
            result = getVehicleLocationUseCase(null)
        }
        //THEN
        Truth.assertThat(result.status).isEqualTo(Status.ERROR)
        Truth.assertThat(result.errorMessage).isEqualTo(givenError)
    }

    private fun givenVehicleLocations(): VehicleLocationResponse {

        val vehicle1 = VehicleLocationModel(84.5655, 89.5685, 1)
        val vehicle2 = VehicleLocationModel(81.5655, 19.5685, 2)


        return VehicleLocationResponse(listOf(vehicle1, vehicle2))

    }
}