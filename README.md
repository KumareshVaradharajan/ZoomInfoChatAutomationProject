# ZoomInfo ChatBot Automation Project

![Automation Tests](https://img.shields.io/badge/tests-automated-success)
![Selenium](https://img.shields.io/badge/Selenium-4.11.0-blue)
![TestNG](https://img.shields.io/badge/TestNG-7.8.0-green)
![Allure](https://img.shields.io/badge/Allure-2.20.1-yellow)
![Java](https://img.shields.io/badge/Java-11%2B-orange)

## Introduction

This repository contains the automation framework for testing a web-based chatbot. It demonstrates how to automate chatbot interactions, validate responses, and generate test reports.

**Features**:
- Selenium WebDriver for browser automation.
- TestNG for test management.
- Allure for test reporting.
- Data-driven testing for various scenarios.
- Page Object Model (POM) design pattern.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: Ensure you have Java 8 or higher installed. You can download it [here](https://www.oracle.com/java/technologies/javase-downloads.html).

- **Apache Maven**: Make sure you have Maven installed. You can download it [here](https://maven.apache.org/download.cgi).

- **WebDriver Executable**: Download the WebDriver executable for your preferred browser (e.g., Chrome, Firefox). Place it in the `drivers` directory.

## Getting Started

Follow these steps to get the project up and running:

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/chatbot-automation.git
   cd chatbot-automation

2. **Install Dependencies**:

   ```bash
   mvn clean install
   ```

3. **Run Tests**:

   ```bash
   mvn test
   ```

4. **Generate Allure Reports** (Optional):

   ```bash
   allure serve allure-results
   ```

## Project Structure

The project structure is organized as follows:

- `src/test/java`: Contains test classes and automation code.
- `src/main/java`: Houses utility or helper classes.
- `src/test/resources`: Stores configuration files, test data, and property files.
- `drivers`: Contains WebDriver executables.

## Test Data Management

Test data can be managed in the `src/test/resources` directory. You can use external data sources like Excel or JSON files to provide test data to your test methods.

## Configuration

- Store URLs and other configuration parameters in a `config.properties` file in the `src/test/resources` directory.
- Configure the WebDriver executable path and other properties in the `config.properties` file.

## Reports

Test reports are generated using Allure. After running the tests, you can generate Allure reports to visualize test results.

```
