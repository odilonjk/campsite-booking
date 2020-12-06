# Booking Campsite

This project uses:
- JDK 11
- Spring Boot 2.4
- Gradle 6.7
- JUnit 5
- H2 Database
- Docker

### How to run it?

 Building the docker image: `$ make docker-build`
 
 Running the docker image: `$ make docker-run`
 
 Stopping the docker image: `$ make docker-stop`
 
### How to use it?

You can use the available features by using the endpoints below:

- `GET /bookings/available-dates?startDate=2020-12-20&endDate=2020-12-30` to get all available dates.
Parameters are optional.

- `GET /bookings/<id>` to get details from a booking.

- `POST /bookings` to create a new booking. Below you can find a body example.
    ```
    {
        "username": "Foo Bar",
        "email": "foo@bar",
        "startDate": "2020-12-25",
        "endDate": "2020-12-27"
    }
    ``` 

- `PUT /bookings/<id>` to update a booking. Below you can find a body example.
    ```
    {
        "username": "Foo Bar",
        "email": "foo@bar",
        "startDate": "2020-12-25",
        "endDate": "2020-12-28"
    }
    ```

- `DELETE /bookings/<id>` to cancel a booking.
