- Assignment architecture and other components are mentioned below.
- Architecture
    - Used MVVM with clean code.
    - Three layers of clean code philosophy data, domain, presentation.
    - Domain Layer
        - Define use cases for to define abstract layer of the tasks in the application.
        - This layer contains mostly interfaces and use cases with no dependencies on other layers
          of the application.
        - Use cases in the domain layers are GetUserDetailsUseCase, GetUserInfoFromDbUseCase,
          GetVehicleDetailsFromDbUseCase, GetVehicleLocationUseCase, StoreUserDetailsUseCase.
        - Defined repository interfaces which use cases utilize to perform the given task.
        - Repositories includes GetUserDetailsRepository, GetVehicleInformationRepository,
          GetVehicleLocationRepository, StoreUserDetailsRepository.
    - Data Layer
        - It has Retrofit API interfaces defined
        - Dependency injections modules for repositories, network and database.
        - Dao layers for data base is also defined in the data layer.
        - Response data classes as well as db entities are defined in this layer.
        - Repository implementation classes are also part of this layer as it is dealing with the
          data.
    - Presentation Layer
      - All UI related implementation is part of this layer.
      - Here all the activities are defined.
      - View models are also part of this layer.