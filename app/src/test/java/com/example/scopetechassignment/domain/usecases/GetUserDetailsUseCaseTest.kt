package com.example.scopetechassignment.domain.usecases

import com.example.scopetechassignment.data.models.Data
import com.example.scopetechassignment.data.models.Owner
import com.example.scopetechassignment.data.models.UserListResponse
import com.example.scopetechassignment.data.models.Vehicle
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserDetailsUseCaseTest {

    lateinit var repository: GetUserDetailsRepository
    lateinit var getUserDetailsUseCase: GetUserDetailsUseCase

    @Before
    fun setUp() {
        repository = mockkClass(GetUserDetailsRepository::class)
        getUserDetailsUseCase = GetUserDetailsUseCase(repository)
    }

    @Test
    fun `given response from repository use case should returns successful response`() {
        //GIVEN
        coEvery { repository.getUserList() } returns Result.success(givenUserDetails())
        //WHEN
        var result: Result<UserListResponse>
        runBlocking {
            result = getUserDetailsUseCase()
        }
        //THEN
        Truth.assertThat(result.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(result).isEqualTo(Result.success(givenUserDetails()))
    }

    @Test
    fun `given response from repository use case should returns empty response`() {
        //GIVEN
        coEvery { repository.getUserList() } returns Result.success(UserListResponse(emptyList()))
        //WHEN
        var result: Result<UserListResponse>
        runBlocking {
            result = getUserDetailsUseCase()
        }
        //THEN
        Truth.assertThat(result.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(result).isEqualTo(Result.success(UserListResponse(emptyList())))
    }

    @Test
    fun `given response from repository use case should returns error on unsuccessful execution`() {
        //GIVEN
        val givenError = "Something went wrong"
        coEvery { repository.getUserList() } returns Result.error(givenError)
        //WHEN
        var result: Result<UserListResponse>
        runBlocking {
            result = getUserDetailsUseCase()
        }
        //THEN
        Truth.assertThat(result.status).isEqualTo(Status.ERROR)
        Truth.assertThat(result.errorMessage).isEqualTo(givenError)
    }

    private fun givenUserDetails(): UserListResponse {

        val vehicleOne = Vehicle(
            color = "Red",
            photo = "",
            make = "VW",
            model = "Polo",
            vehicleId = 1,
            vin = "",
            year = "2012"
        )

        val vehicleTwo = Vehicle(
            color = "Blue",
            photo = "",
            make = "Majda",
            model = "CX one",
            vehicleId = 2,
            vin = "",
            year = "2010"
        )
        val vehicleThree = Vehicle(
            color = "White",
            photo = "",
            make = "Honda",
            model = "Accord",
            vehicleId = 3,
            vin = "",
            year = "2016"
        )

        val ownerOne = Owner(photo = "", name = "Akshay", surname = "Borgave")
        val ownerTwo = Owner(photo = "", name = "Deepak", surname = "Borgave")
        val ownerThree = Owner(photo = "", name = "Vivek", surname = "Borgave")

        val userIdOne = 1
        val userIdTwo = 2
        val userIdThree = 1

        val dataOne = Data(ownerOne, userIdOne, listOf(vehicleOne))
        val dataTwo = Data(ownerTwo, userIdTwo, listOf(vehicleOne, vehicleTwo))
        val dataThree = Data(ownerThree, userIdThree, listOf(vehicleThree))

        return UserListResponse(listOf(dataOne, dataTwo, dataThree))

    }

}