# Testing "Hello, World!" with ISTQB CTFL & TestNG (Imperative Edition)

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

Each test in [`HelloWorldTest.java`](src/test/java/com/topchiyski/helloworld/HelloWorldTest.java) maps to a CTFL technique:

| ISTQB Category | Technique | Test Method(s) | What It Verifies |
| :--- | :--- | :--- | :--- |
| **Black-Box** | Equivalence Partitioning (EP) | `testValidEquivalencePartitions()`<br>`testInvalidEquivalencePartition()` | Valid partitions (`null`, blank, normal name) and the invalid partition (>20 chars) |
| **Black-Box** | Boundary Value Analysis (BVA) | `testValidBoundaryValues()`<br>`testInvalidUpperBoundaryValue()` | Exact edges of the length limit (`0`, `1`, `19`, `20`, and `21` characters) |
| **Black-Box** | Decision Table Testing | `testDecisionTableRules()` | All 4 combinations of `isFormal` and `isShouting` via TestNG `@DataProvider` |
| **Black-Box** | State Transition Testing | `testStateTransitions()` | Lifecycle transitions (`ON -> OFF -> ON`) and blocked actions in the `OFF` state |
| **White-Box** | Statement & Branch Coverage | `testFullBranchAndStatementCoverage()` | 100% execution of all statements and `if/else` branches |
| **Experience-Based** | Error Guessing | `testErrorGuessingWhitespaceAndPadding()` | Padded whitespace (`"   Alice   "`) and trimming before length validation |

## Getting Started

### Prerequisites

- **Java 21+**
- **Maven 3.8+**

### Run the Tests

```bash
mvn clean test
```

The TestNG suite is configured in [`src/test/resources/testng.xml`](src/test/resources/testng.xml) and runs automatically through the Maven Surefire plugin.

## Contact & Questions

Have a question, feedback, or an idea for another technique to cover? Reach out to me on LinkedIn:

👉 **[Stoyan Topchiyski on LinkedIn](https://www.linkedin.com/in/stoyan-topchiyski/)**

## License

This project is licensed under the [MIT License](LICENSE).
