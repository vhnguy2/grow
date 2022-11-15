# Wiki Stats web app (Grow Therapy Interview)
The Wiki Stats webapp is built using React JS backed by a Java webservice (DropWizard). There is no database, although there should be. More on that in the "Next Steps" section.

## How to build and run the backend and React frontend
Execute the following commands from the main project directory:
```
./start_backend.sh
./start_frontend.sh
```

## How to run unit tests for backend server
```
$PROJECT_HOME/grow/.gradlew clean test
```
The full test report can be found `$PROJECT_HOME/grow/build/reports/tests/test/index.html`

## APIs
All of the APIs have been wrapped at `$PROJECT_ROOT/grow/src/main/java/com/viet/interview/web/resource/WikiResource.java`. The documentation for each API is available via JavaDocs there, but not generated.

## Next Steps (not in scope)
1. Stand up a datastore to cache previously retrieved data from Wiki's public APIs. We want to minimize our dependencies on Wikipedia as much as possible. Since all of the data is time-series, the data is not expected to be altered after published.
2. Properly set up dependency injection in order to clean up some of the unnecessary boilerplate code and to have better separation of concerns.
3. Stand up a Docker container with the SDKs and libraries needed to run the server
4. Learn some CSS to make the website prettier

## TODO (in scope)
1. Write unit tests to test date conversion, date range computation, and picking top articles
2. Add documentation
