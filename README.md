Binance Ticker API Testing
This project is focused on validating the Ticker API and verifying its responses according to specified test scenarios. 
We are performing end-to-end (E2E) tests to check the correctness of the API data returned by Binance's Ticker and Exchange APIs. 
The project uses Cucumber for behavior-driven development (BDD) with Gherkin syntax for writing feature files.


Prerequisites
Java (version 8 or higher)
Maven (for managing dependencies)
Rest-Assured (for API testing)
Cucumber (for BDD and Gherkin support)

Project Structure

API/
├── src
│   ├── main
│   │   └── java
│   │       └── resources
│   │           ├── APIResources.java
│   │           ├── Utils.java
│   │           └── global.properties
│   ├── test
│   │   └── java
│   │       ├── cucumber.Options
│   │       │   └── TestRunner.java
│   │       ├── features
│   │       │   └── TickerAPI.feature
│   │       └── stepDefinitions
│   │           └── TickerAPISteps.java
├── target
│   ├── cucumber-html-reports
│   ├── jsonReports
│   ├── surefire-reports
│   └── API-0.0.1-SNAPSHOT.jar
├── pom.xml
└── README.md

Key Directories and Files

src/main/java/resources:
APIResources.java: Contains methods to define the various resources API endpoints.
Utils.java: Contains utility methods such as configuration loading, HTTP request methods and other commonly used methods
global.properties: Contains global properties such as API base URLs and configurations.


src/test/java/cucumber.Options:
TestRunner.java: The Cucumber test runner class used to execute the test cases.

src/test/java/features:
TickerAPI.feature: The feature file written in Gherkin syntax to define the test scenarios.


src/test/java/stepDefinitions:
TickerAPISteps.java: Step definition file where the Gherkin steps are implemented.

target: This directory holds generated reports such as cucumber-html-reports and surefire-reports after running tests.

Features
The current test suite validates the Ticker API responses and checks if the data adheres to the expected conditions.

Scenarios
Scenario 1: Verify Count for Symbols Containing "XRP" and FirstId > 0
In this scenario, the API response is validated to ensure that:
For any symbol that contains "XRP," the firstId is above zero.
The count should be above zero for such symbols.

Scenario 2: Verify Symbol Name as a Concatenation of baseAsset and quoteAsset
This scenario verifies:
The symbol field in the exchange API response should be a concatenation of baseAsset and quoteAsset.


How to Run

1. Clone the Repository
git clone repo url

2. Install Dependencies
mvn clean install

3. Run the Tests
mvn test verify

4. View Test Reports
After running the tests, the reports will be available in the target/cucumber-html-reports and target/surefire-reports directories.

Additional Information
Logging: All logs are stored in Logging.txt
Reports: The JSON reports can be found in the target/jsonReports directory.



