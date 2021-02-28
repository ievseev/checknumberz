# Autotests for Magic service

* [Test datasets](#test-datasets)
* [Running tests](#running-tests)
* [Generating the test report](#generating-the-test-report)
* [Current bugs](#current-bugs)

## Test datasets

* integers: -2147483648, -100, 0, 100, 2147483647
* real numbers: 1.7976931348623157E308, -100.1, 0.0, 100.1, 4.9E-324
* strings: "", "c", "string", "xxi"
* request with incorrect field name ("unknown")
* request without body

## Running tests

The test suites can be run directly by your IDE or by command line:

- `gradlew clean test` (for Windows) - all the tests will execute

## Generating the test report

This project uses Allure Report to automatically generate the test report.

You can use the command line to generate it:

* `gradlew allureServe` (for Windows)  - will open the HTML report into the browser

## Current bugs

I've found only one bug =(
- If we send request with incorrect field name {"unknown":10} - service responds with 500 error. It would be better to respond with 400 status
and informative message, for example: "Bad Request"


