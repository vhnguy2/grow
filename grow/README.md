To build from terminal: `./gradlew clean shadowJar`
To run server from terminal: `java -jar build/libs/grow.jar server`

Known shortcomings:
1. Stand up a datastore to cache previously retrieved data from Wiki's public APIs. We want to minimize our dependencies on Wikipedia as much as possible. Since all of the data is time-series, the data is not expected to be altered after published.
2. Properly set up dependency injection in order to clean up some of the unnecessary boilerplate code and to have better separation of concerns.
3. Stand up a Docker container with the SDKs and libraries needed to run the server
4. Learn some CSS to make the website prettier

TODOs:
3. Write unit tests to test date conversion, date range computation, and picking top articles
4. Add documentation