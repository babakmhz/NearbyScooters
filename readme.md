# Nearby Scooters
##### The project that collects the scooters data in germany with a mock api, finds the nearest possible scooter and display them in a proper way on the map

## Screenshots
![Alt text](screenshots/1.png?raw=true)
![Alt text](screenshots/1.png?raw=true)
![Alt text](screenshots/1.png?raw=true)

## Architecture
- MVVM
#### Tools & libraries/Technologies

- kotlin as the main programming language
- Jetpack architecture components
- Hilt
- Fused Location provider
- kotlin-coroutines
- Junit
- Retrofit

#### Packages
- di: Dependency injection module of the application
- data: Represents Models, Network, Repository classes/interfaces
- utils: Utils, helper methods of the application
- view: View layer of MVVM architecture
#### Key Classes And Functions
- `LocationHelper.kt`: An interface with location related methods
- `LocationProvider.kt`: Implementation for LocationHelper.kt
- `RepositoryHelper.kt`: Interface to manage behavior of RepositoryImpl class
- `RepositoryImpl.kt`: Implementation of RepositoryHelper interface which is responsible for feeding viewModel with data
    * `override suspend fun fetchScootersFromRemoteSource(
        userLatLng: LatLng,
        locationHelper: LocationHelper
    ): List<Scooter>`: Fetches the data retrieved from remote source and returns `List<Scooter>`
    - `ScooterNetworkToDomainMapper.kt`: Here's a mapper for mapping the network response to domain model

- `ApiService.kt`: Network API calls: responsible for calling the remote source

- `MainViewModel.kt`: Application ViewModel
    * `fun fetchScooters(userLatLng: LatLng)`: Retrieves Scooters from remote source and checks if response is successful to change LiveData "State" associated with Places.
    * `fun getLastLocation()`: Gets user's last location via FusedLocationProviderClient
    * `requestLocationUpdates(cancellationToken: CancellationToken)`: Requests location updates from FusedLocationProviderClient
- `ClusterManagerRenderer.kt`: A custom renderer for cluster manager to customize rendering the markers
- `AuthenticationInterceptor.kt`: For adding the `ApiKey` header to requests in generic way
- `LiveDataWrapper.kt`: Contains Wrappers for live data to manage Ui State
- `ExtensionFunctions.kt`: The place that common extension functions live


Cheers!