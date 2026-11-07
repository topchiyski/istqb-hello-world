package com.topchiyski.helloworld;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * TestNG test suite demonstrating how every ISTQB Foundation Level (CTFL)
 * test technique applies to a simple "Hello, World!" program.
 *
 * <p>Written in 100% classic imperative Java (zero lambdas, zero streams,
 * and zero functional interfaces) using declarative TestNG expectedExceptions
 * and explicit try-catch blocks.
 */
public class HelloWorldTest {

    private HelloWorld helloWorld;

    @BeforeMethod
    public void setUp() {
        helloWorld = new HelloWorld();
    }

    // =========================================================================
    // 1. EQUIVALENCE PARTITIONING (ISTQB Black-Box Technique)
    // =========================================================================

    /**
     * Applies <b>Equivalence Partitioning (EP)</b> to valid input groups.
     *
     * <p><b>How EP is applied to Hello World:</b>
     * Because testing every possible name is impossible (ISTQB Principle #2), we divide
     * all possible inputs into groups ("partitions") that the program treats the same way:
     * <ul>
     *   <li><b>Partition 1 (Null input):</b> Treated as default "World".</li>
     *   <li><b>Partition 2 (Blank spaces):</b> Treated as default "World".</li>
     *   <li><b>Partition 3 (Valid names, 1-20 chars):</b> Any normal name like "Alice" works the same.</li>
     * </ul>
     * Picking just one test value from each group gives us full confidence without writing
     * hundreds of repetitive tests.
     */
    @Test
    public void testValidEquivalencePartitions() {
        // Partition 1: Null input falls back to "World"
        assertEquals(helloWorld.greet(null), "Hello, World.");

        // Partition 2: Blank spaces fall back to "World"
        assertEquals(helloWorld.greet("   "), "Hello, World.");

        // Partition 3: A normal valid name (between 1 and 20 characters)
        assertEquals(helloWorld.greet("Alice"), "Hello, Alice.");
    }

    /**
     * Applies <b>Equivalence Partitioning (EP)</b> to the invalid input group.
     *
     * <p><b>How EP is applied to Hello World:</b>
     * Any name longer than 20 characters belongs to the "Invalid Oversized Partition".
     * Whether a name is 25, 35, or 100 characters long, the program should reject it
     * in the exact same way. Here we pick 25 characters as our representative value
     * and verify the exception using TestNG's declarative {@code expectedExceptions} attribute.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidEquivalencePartition() {
        String twentyFiveChars = "A".repeat(25);
        helloWorld.greet(twentyFiveChars);
    }

    // =========================================================================
    // 2. BOUNDARY VALUE ANALYSIS (ISTQB Black-Box Technique)
    // =========================================================================

    /**
     * Applies <b>Boundary Value Analysis (BVA)</b> to valid boundary edges (0, 1, 19, and 20 characters).
     *
     * <p><b>How BVA is applied to Hello World:</b>
     * Developers often make off-by-one mistakes at the exact edges of rules (like writing
     * {@code < 20} instead of {@code <= 20}). Since our valid name rule is 1 to 20 characters,
     * BVA tells us to test right on the valid edges:
     * <ul>
     *   <li><b>Lower edge:</b> 0 characters (empty -> falls back to "World") and 1 character ("A").</li>
     *   <li><b>Upper valid edge:</b> 19 characters (valid) and 20 characters (exact limit).</li>
     * </ul>
     */
    @Test
    public void testValidBoundaryValues() {
        // Lower boundary edge: 0 characters (triggers default) and 1 character (minimum custom name)
        assertEquals(helloWorld.greet(""), "Hello, World.");
        assertEquals(helloWorld.greet("A"), "Hello, A.");

        // Upper valid boundaries: 19 characters and 20 characters (exact maximum)
        String nineteenChars = "A".repeat(19);
        String twentyChars = "A".repeat(20);
        assertEquals(helloWorld.greet(nineteenChars), "Hello, " + nineteenChars + ".");
        assertEquals(helloWorld.greet(twentyChars), "Hello, " + twentyChars + ".");
    }

    /**
     * Applies <b>Boundary Value Analysis (BVA)</b> to the upper invalid boundary (21 characters).
     *
     * <p><b>How BVA is applied to Hello World:</b>
     * Completes the upper boundary test by passing 21 characters (just 1 character over the
     * maximum limit of 20) and verifying that {@link IllegalArgumentException} is thrown.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidUpperBoundaryValue() {
        String twentyOneChars = "A".repeat(21);
        helloWorld.greet(twentyOneChars);
    }

    // =========================================================================
    // 3. DECISION TABLE TESTING (ISTQB Black-Box Technique)
    // =========================================================================

    @DataProvider(name = "styleDecisionTable")
    public Object[][] styleDecisionTable() {
        return new Object[][]{
                // isFormal, isShouting, Expected Output
                {false, false, "Hello, Bob."},     // Rule 1: Casual + Calm
                {false, true, "HELLO, BOB!"},     // Rule 2: Casual + Shouting
                {true, false, "Good day, Bob."},  // Rule 3: Formal + Calm
                {true, true, "GOOD DAY, BOB!"}   // Rule 4: Formal + Shouting
        };
    }

    /**
     * Applies <b>Decision Table Testing</b> to verify all combinations of business rules.
     *
     * <p><b>How Decision Table Testing is applied to Hello World:</b>
     * When an output depends on multiple true/false conditions working together ({@code isFormal}
     * and {@code isShouting}), a Decision Table lists every possible combination of rules so
     * none are forgotten. TestNG's {@code @DataProvider} maps each row of the decision table
     * directly to a test run.
     *
     * @param isFormal   condition 1: whether the greeting is formal
     * @param isShouting condition 2: whether the greeting is shouted in uppercase
     * @param expected   the expected result for that specific rule combination
     */
    @Test(dataProvider = "styleDecisionTable")
    public void testDecisionTableRules(boolean isFormal, boolean isShouting, String expected) {
        String actual = helloWorld.greetWithStyle("Bob", isFormal, isShouting);
        assertEquals(actual, expected);
    }

    // =========================================================================
    // 4. STATE TRANSITION TESTING (ISTQB Black-Box Technique)
    // =========================================================================

    /**
     * Applies <b>State Transition Testing</b> to verify the ON/OFF states of the greeter.
     *
     * <p><b>How State Transition Testing is applied to Hello World:</b>
     * Programs don't always respond the same way to the same input; their response often
     * depends on their current <i>state</i>. Here we test:
     * <ol>
     *   <li>Starting in the {@code ON} state (greeting succeeds).</li>
     *   <li>Transitioning from {@code ON -> OFF} (greeting is blocked with an exception).</li>
     *   <li>Transitioning from {@code OFF -> ON} (greeting works again).</li>
     * </ol>
     */
    @Test
    public void testStateTransitions() {
        // 1. Initial State: ON
        assertTrue(helloWorld.isActive());
        assertEquals(helloWorld.greet("World"), "Hello, World.");

        // 2. Transition: ON -> OFF (Invalid state for greeting, verified via classic try-catch)
        helloWorld.turnOff();
        assertFalse(helloWorld.isActive());
        try {
            helloWorld.greet("World");
            fail("Expected an IllegalStateException when greeter is turned OFF.");
        } catch (IllegalStateException expected) {
            assertEquals(expected.getMessage(), "Greeter is currently turned OFF.");
        }

        // 3. Transition: OFF -> ON (Recovered state)
        helloWorld.turnOn();
        assertTrue(helloWorld.isActive());
        assertEquals(helloWorld.greet("World"), "Hello, World.");
    }

    // =========================================================================
    // 5. STATEMENT & BRANCH COVERAGE (ISTQB White-Box Technique)
    // =========================================================================

    /**
     * Applies <b>White-Box Statement and Branch Testing</b> by inspecting the internal code structure.
     *
     * <p><b>How White-Box Testing is applied to Hello World:</b>
     * Unlike Black-Box testing (where we only look at the rules), White-Box testing looks directly
     * at the Java {@code if/else} statements inside {@link HelloWorld#greetWithStyle(String, boolean, boolean)}
     * to ensure every line (Statement) and every {@code true/false} decision (Branch) is executed at least once:
     * <ul>
     *   <li>Branch 1: {@code if (!active)} -> tested both {@code true} and {@code false}.</li>
     *   <li>Branch 2: {@code if (name == null || name.isBlank())} -> tested {@code null}, blank, and real text.</li>
     *   <li>Branch 3: {@code if (cleanName.length() > MAX_LENGTH)} -> tested both valid and oversized names.</li>
     *   <li>Branch 4 & 5: {@code if (isFormal)} and {@code if (isShouting)} -> tested both {@code true} and {@code false}.</li>
     * </ul>
     */
    @Test
    public void testFullBranchAndStatementCoverage() {
        // Branch path 1: active == true, normal name, formal == false, shouting == false
        assertEquals(helloWorld.greetWithStyle("Dev", false, false), "Hello, Dev.");

        // Branch path 2: name == null, formal == true, shouting == true
        assertEquals(helloWorld.greetWithStyle(null, true, true), "GOOD DAY, WORLD!");

        // Branch path 3: cleanName.length() > MAX_LENGTH (true)
        try {
            helloWorld.greetWithStyle("A".repeat(21), false, false);
            fail("Expected IllegalArgumentException for name over 20 characters.");
        } catch (IllegalArgumentException expected) {
            assertEquals(expected.getMessage(), "Name cannot exceed 20 characters.");
        }

        // Branch path 4: !active (true)
        helloWorld.turnOff();
        try {
            helloWorld.greetWithStyle("Dev", false, false);
            fail("Expected IllegalStateException when greeter is OFF.");
        } catch (IllegalStateException expected) {
            assertEquals(expected.getMessage(), "Greeter is currently turned OFF.");
        }
    }

    // =========================================================================
    // 6. ERROR GUESSING (ISTQB Experience-Based Technique)
    // =========================================================================

    /**
     * Applies <b>Error Guessing</b> based on a tester's intuition about common coding mistakes.
     *
     * <p><b>How Error Guessing is applied to Hello World:</b>
     * Experienced testers know that users frequently copy-paste text with accidental spaces
     * at the beginning or end (e.g., {@code "   Alice   "}). Furthermore, a classic developer bug
     * is checking the length of a string <i>before</i> trimming those spaces.
     * Here we pass a 20-character name wrapped in extra spaces (24 characters total) to verify
     * that our program trims the spaces first rather than falsely rejecting the input.
     */
    @Test
    public void testErrorGuessingWhitespaceAndPadding() {
        // Common mistake 1: Spaces around a normal name aren't stripped in the output
        assertEquals(helloWorld.greet("   Alice   "), "Hello, Alice.");

        // Common mistake 2: Checking max length BEFORE stripping spaces (20 'A's + 4 spaces = 24 raw chars)
        String paddedMaxName = "  " + "A".repeat(20) + "  ";
        assertEquals(helloWorld.greet(paddedMaxName), "Hello, " + "A".repeat(20) + ".");
    }
}
