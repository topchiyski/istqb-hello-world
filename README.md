# Testing "Hello, World!" with ISTQB CTFL & TestNG (Imperative Edition)

[![CI](https://github.com/topchiyski/istqb-hello-world/actions/workflows/ci.yml/badge.svg)](https://github.com/topchiyski/istqb-hello-world/actions/workflows/ci.yml)

> Every developer writes `"Hello, World!"` on day one. Almost no one learns how to test it on day two.

## About This Repository

This is a **companion repository** to my LinkedIn article:

📄 **[ISTQB Is Dead Weight Until You Code It](https://www.linkedin.com/pulse/istqb-dead-weight-until-you-code-stoyan-topchiyski-irtpf)**

It shows how every core software testing technique from the **ISTQB Certified Tester Foundation Level (CTFL v4.0)** syllabus applies to a beginner-friendly `"Hello, World!"` program in **Java** using **TestNG**.

The code is written in **100% classic imperative style**: no lambdas, no functional interfaces, and explicit `if/else` control flow. That way the focus stays on the testing techniques, not on language features.

## Table of Contents

- [About This Repository](#about-this-repository)
- [Project Structure](#project-structure)
- [Business Rules Under Test](#business-rules-under-test)
- [ISTQB Traceability Matrix](#istqb-traceability-matrix)
- [Getting Started](#getting-started)
- [Contact & Questions](#contact--questions)
- [License](#license)

## Project Structure

```text
.
├── .github/                         # CI workflow and Dependabot configuration
├── .mvn/wrapper/                    # Maven Wrapper config (pins the Maven version)
├── mvnw                             # Maven Wrapper script (macOS / Linux)
├── mvnw.cmd                         # Maven Wrapper script (Windows)
├── pom.xml
└── src
    ├── main/java/com/topchiyski/helloworld
    │   └── HelloWorld.java          # System under test
    └── test
        ├── java/com/topchiyski/helloworld
        │   └── HelloWorldTest.java  # ISTQB technique-driven tests
        └── resources
            └── testng.xml           # TestNG suite configuration
```

## Business Rules Under Test

Defined in [`HelloWorld.java`](src/main/java/com/topchiyski/helloworld/HelloWorld.java):

| # | Rule | Behaviour |
| :--- | :--- | :--- |
| 1 | **Default** | If `name` is `null` or blank, greet `"World"`. |
| 2 | **Length** | A custom name must be **1 to 20 characters** long (after trimming leading/trailing spaces). |
| 3 | **Style** | `isFormal = true` uses `"Good day"` instead of `"Hello"`.<br>`isShouting = true` converts the greeting to `UPPERCASE` and ends with `"!"` instead of `"."`. |
| 4 | **State** | The greeter can be turned `ON` or `OFF`. Calling `greet()` while `OFF` throws an `IllegalStateException`. |

## ISTQB Traceability Matrix

Each test in [`HelloWorldTest.java`](src/test/java/com/topchiyski/helloworld/HelloWorldTest.java) maps to a CTFL technique. The syllabus also covers exploratory testing, checklist-based testing and collaboration-based approaches; those happen with people rather than in a unit test, so they are not coded here.

| ISTQB Category | Technique | Test Method(s) | What It Verifies |
| :--- | :--- | :--- | :--- |
| **Black-Box** | Equivalence Partitioning (EP) | `testValidEquivalencePartitions()`<br>`testInvalidEquivalencePartition()` | Valid partitions (`null`, blank, normal name) and the invalid partition (>20 chars) |
| **Black-Box** | Boundary Value Analysis (BVA) | `testValidBoundaryValues()`<br>`testInvalidUpperBoundaryValue()` | Exact edges of the length limit (`0`, `1`, `19`, `20`, and `21` characters) |
| **Black-Box** | Decision Table Testing | `testDecisionTableRules()` | All 4 combinations of `isFormal` and `isShouting` via TestNG `@DataProvider` |
| **Black-Box** | State Transition Testing | `testStateTransitions()`<br>`testStateSelfTransitions()` | Every valid transition (`ON -> OFF`, `OFF -> ON`, `ON -> ON`, `OFF -> OFF`) and blocked actions in the `OFF` state |
| **White-Box** | Statement & Branch Coverage | `testFullBranchAndStatementCoverage()` | Every statement and `if/else` branch in `greetWithStyle()`; [JaCoCo](https://www.jacoco.org/jacoco/) measures 100% line and branch coverage of `HelloWorld` for the whole suite and fails the build below it |
| **Experience-Based** | Error Guessing | `testErrorGuessingWhitespaceAndPadding()` | Padded whitespace (`"   Alice   "`) and trimming before length validation |

## Getting Started

### Prerequisites

- **Java 21 (LTS)**: a JDK, not just a JRE. Check with `java -version` and make sure `JAVA_HOME` points to it.

That's it. You do **not** need to install Maven. The project ships with the [Maven Wrapper](https://maven.apache.org/tools/wrapper/), which downloads the correct Maven version automatically on first run.

### Run the Tests

macOS / Linux:

```bash
./mvnw clean verify
```

Windows (Command Prompt):

```bat
mvnw.cmd clean verify
```

Windows (PowerShell):

```powershell
.\mvnw.cmd clean verify
```

Expected result: `Tests run: 12, Failures: 0, Errors: 0, Skipped: 0`, `All coverage checks have been met.` and `BUILD SUCCESS`.

`verify` runs the tests and then the coverage gate. To run only the tests, use `clean test` instead.

> Already have Maven installed? `mvn clean verify` works too.

### Coverage Report

After `verify`, open `target/site/jacoco/index.html` to see line and branch coverage of `HelloWorld`, line by line.

The TestNG suite is configured in [`src/test/resources/testng.xml`](src/test/resources/testng.xml) and runs automatically through the Maven Surefire plugin.

## Contact & Questions

Have a question, feedback, or an idea for another technique to cover? Reach out to me on LinkedIn:

👉 **[Stoyan Topchiyski on LinkedIn](https://www.linkedin.com/in/stoyan-topchiyski/)**

## License

This project is licensed under the [MIT License](LICENSE).
