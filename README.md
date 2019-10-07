# Activity monitoring

This is a simple web application for monitoring activities logged in a CSV file. Its back-end is built on top of Spring Framework
(in accordance to the DDD paradigm) and the front-end on React.js. 

## Building

In order to build and test the app, type

```
mvn verify
```

## Running

You need JDK12 and Maven to run this application. Additionally, put `dummy.csv` with the monitoring data into the current directory
before running the application.

In order to run the application, type

```
mvn spring-boot:run
``` 

The application will be accessible via `http://localhost:8080/`.

There are three users (all identifiable by `password` password):
* `userA` - with an access to the "Today" chart
* `userB` - with an access to the "Last 6 days" chart
* `userC` - with an access to both charts