## Back end project "Exchange rates"
***
Author - [Volodymyr Lipovskiy](https://www.linkedin.com/in/volodymyr-lipovskiy-2b0955266/)
***

### This API provides two methods to retrieve exchange rates from various sources and calculate their average:

1. GET /exchange-rates - Returns a list of exchange rates for all sources, along with the average market rates.
2. GET /exchange-rates/period - Returns a list of exchange rates for all sources for the specified period.
***
## Technologies Used
This API has been implemented using the following technologies:

* Java
* Tomcat
* Spring Boot
* Hibernate
* PostgreSQL
* GIT
* Cron job
* Swagger

***

## API Interface
The following table describes the interface for the implemented API:

| Method  | Endpoint              | Parameters   | Description                                                                            |
|-------|-----------------------|--------------|----------------------------------------------------------------------------------------|
| GET  | /exchange-rates       | None    | Returns a list of exchange rates for all<br/> sources, along with the average market rates. |
| GET | /exchange-rates/period | start_date, <br/>end_date        | Returns a list of exchange rates for all sources <br/>for the specified period.             |

***

### API Implementations
This API has been implemented to support three different sources of exchange rates:

1. MonoBank
2. MinFin
3. PrivatBank

Each of these sources has been implemented as a separate module, but they all share a common interface. 
This makes it easy to add new sources of exchange rates in the future.

***

### Data Synchronization
A cron job has been implemented to periodically synchronize data from the API providers into the local database. This ensures that the API always has the most up-to-date exchange rate data available.

***

### Build and Run
To build and run this API, follow these steps:

* Clone the GIT repository.
* Install PostgreSQL and create a new database.
* Update the application.properties file with your database credentials.
* Run the application using Gradle or your preferred IDE.

***

### Swagger
This API documentation is generated using Swagger. <br>
The Swagger UI can be accessed at http://localhost:8080/swagger-ui.html.