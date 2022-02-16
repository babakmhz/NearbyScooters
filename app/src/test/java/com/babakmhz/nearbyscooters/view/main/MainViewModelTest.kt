package com.babakmhz.nearbyscooters.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babakmhz.nearbyscooters.appUtil.LocationUiState
import com.babakmhz.nearbyscooters.appUtil.MainUiState
import com.babakmhz.nearbyscooters.data.RepositoryHelper
import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.location.LocationHelper
import com.babakmhz.nearbyscooters.testUtil.CoroutineTestRule
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule(coroutineDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: MainViewModel
    private lateinit var repoHelper: RepositoryHelper
    private lateinit var locationHelper: LocationHelper

    private val networkDelay = 1000L

    @Before
    fun setup() {
        repoHelper = mockk()
        locationHelper = spyk()
        viewModel = MainViewModel(repoHelper, locationHelper)
    }

    @Test
    fun `test data cannot be fetched from remote source should change live data  state to Error`() =
        runBlockingTest {
            // given
            val returnError = Throwable()

            //mocking response from fetchScooters Method
            coEvery {
                repoHelper.fetchScootersFromRemoteSource(
                    ofType(),
                    ofType()
                )
            } coAnswers {
                throw returnError
            }

            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                // invoking forth lambda argument
                this.arg<(LatLng) -> Unit>(3).invoke(LatLng(0.0, 0.0))
            }

            viewModel.scooterLiveData.observeForever {}

            // we can make fetchScooters() method internal and VisibleForTesting but
            // i prefer to get the desired result with calling parent methods and mocking
            // viewModel.fetchScooters()
            viewModel.getLastLocation()

            // then
            assertNotNull(viewModel.scooterLiveData.value)
            assertEquals(viewModel.scooterLiveData.value, MainUiState.Error(returnError))
        }

    @Test
    fun `test data fetched from remote source should change live data from loading state to success`() =
        coroutineDispatcher.runBlockingTest {
            //given
            val response = arrayListOf<Scooter>()

            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                // invoking forth lambda argument
                this.arg<(LatLng) -> Unit>(3).invoke(LatLng(0.0, 0.0))
            }

            coEvery { repoHelper.fetchScootersFromRemoteSource(ofType(), ofType()) } coAnswers {
                delay(networkDelay)
                response
            }
            viewModel.scooterLiveData.observeForever {
            }

            //when
            viewModel.getLastLocation() // we could also call viewModel.requestLocationUpdates() method, the goal is invoking viewModel.fetchScooters() method

            //then
            assertNotNull(viewModel.scooterLiveData.value)
            assertTrue(viewModel.scooterLiveData.value is MainUiState.Loading)
            advanceTimeBy(networkDelay)
            assertTrue(viewModel.scooterLiveData.value is MainUiState.Success<*>)
        }


    @Test
    fun `testing request lastLocation Loading should change liveData State to Loading`() =
        coroutineDispatcher.runBlockingTest {

            //given
            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                // invoking forth lambda argument
                this.arg<() -> Unit>(2).invoke()
            }


            viewModel.locationLiveData.observeForever {
                println("location state $it")
            }

            //when
            viewModel.getLastLocation()
            //then
            assertNotNull(viewModel.locationLiveData.value)
            assertTrue(viewModel.locationLiveData.value is LocationUiState.Loading)
        }

    @Test
    fun `testing request lastLocation onPermissionFailed should change liveData State to OnPermissionFailed`() =
        coroutineDispatcher.runBlockingTest {

            //given
            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                // invoking forth lambda argument
                this.arg<() -> Unit>(0).invoke()
            }


            viewModel.locationLiveData.observeForever {}

            //when
            viewModel.getLastLocation()

            //then
            assertNotNull(viewModel.locationLiveData.value)
            assertTrue(viewModel.locationLiveData.value is LocationUiState.OnPermissionFailed)
        }

    @Test
    fun `testing request lastLocation onProviderDisabled should change liveData State to onProviderDisabled`() =
        coroutineDispatcher.runBlockingTest {

            //given

            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                // invoking forth lambda argument
                this.arg<() -> Unit>(1).invoke()
            }


            viewModel.locationLiveData.observeForever {}

            //when
            viewModel.getLastLocation()

            //then
            assertNotNull(viewModel.locationLiveData.value)
            assertTrue(viewModel.locationLiveData.value is LocationUiState.OnProviderDisabled)
        }

    @Test
    fun `testing request lastLocation Success should change liveData State to Success`() =
        coroutineDispatcher.runBlockingTest {

            val response = LatLng(0.0, 0.0)
            //given
            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                // invoking forth lambda argument
                this.arg<(LatLng) -> Unit>(3).invoke(response)
            }


            viewModel.locationLiveData.observeForever {}

            //when
            viewModel.getLastLocation()

            //then
            assertNotNull(viewModel.locationLiveData.value)
            assertTrue(viewModel.locationLiveData.value is LocationUiState.Success)
        }

    @Test
    fun `testing request lastLocation Error should change liveData State to Error`() =
        coroutineDispatcher.runBlockingTest {

            val response = Exception()
            //given
            every {
                locationHelper.requestLastLocation(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers  {
                // invoking forth lambda argument
                this.arg<(Exception) -> Unit>(4).invoke(response)
            }


            viewModel.locationLiveData.observeForever {}

            //when
            viewModel.getLastLocation()

            //then
            assertNotNull(viewModel.locationLiveData.value)
            assertTrue(viewModel.locationLiveData.value is LocationUiState.Error)
        }
}