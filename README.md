#Test Leboncoin

## Tech stack
- Full kotlin :100:
- JetPack components (lifeCycleRuntinme , ViewModel , fragment-ktx) to be safe about configuration changes (rotation of screens ) :relaxed:
- Coroutines for manage asynchronious tasks and multithreading (change between main , io thread easly ) be safe :pray:
- testable Kotlin code (unit test) :robot:
- Clean Architecture (data, domain, presentation layers)
- Presentation layer built based on Unidirectional data flow (UDF) and single entry points
- Latest AGP 7+ ( be sure to setup Jvm 11) :warning:
- Dagger Hilt (after long time of using dagger , i guess Hilt come with many easy way to create our dependency graph :partying_face:)
- using Unit, Instrumentation and Integration tests

## Architecture

#### Clean Architecture Project
This is simple project consists of displaying list of albums fetched from WS and saved in DB to support offline mode 
the project containes three layer data -> domain (use case) -> presentation (mvvm) , we try to sperate responsability to be able to implement unit test easly 

#### Testing strategy

The data layer is tested through of unit tests covering:
-  Retrofit instance using Mockwebserver in order to test Retrofit network
-  Json converters
- Network data source ( ApiService , RemoteDataSource )
- Local data source ( Room , LocalDataSource )
- Repository

For the domain layer we use usecase ( we inject repository class inside each usecase)

Finally the presentation layer is tested using the following strategy
- Unit tests for VM (test flow emmited from VM to view)
- create fake DataSource by mock UseCase and for sur mock DataSource
