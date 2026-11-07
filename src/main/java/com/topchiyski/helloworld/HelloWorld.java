package com.topchiyski.helloworld;

/**
 * A beginner-friendly, testable "Hello, World!" class designed to demonstrate
 * ISTQB Foundation Level testing techniques using classic imperative Java.
 */
public class HelloWorld {

    public static final int MAX_LENGTH = 20;
    private boolean active = true;

    /**
     * Standard greeting method.
     *
     * @param name the person to greet (defaults to "World" if null or blank)
     * @return formatted greeting string
     */
    public String greet(String name) {
        return greetWithStyle(name, false, false);
    }

    /**
     * Greets a user with customizable formality and volume rules.
     *
     * @param name       the person to greet (max 20 characters after trimming spaces)
     * @param isFormal   if true, uses "Good day" instead of "Hello"
     * @param isShouting if true, converts output to UPPERCASE and uses "!" instead of "."
     * @return formatted greeting string
     */
    public String greetWithStyle(String name, boolean isFormal, boolean isShouting) {
        if (!active) {
            throw new IllegalStateException("Greeter is currently turned OFF.");
        }

        String cleanName;
        if (name == null || name.isBlank()) {
            cleanName = "World";
        } else {
            cleanName = name.strip();
        }

        if (cleanName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Name cannot exceed " + MAX_LENGTH + " characters.");
        }

        String prefix;
        if (isFormal) {
            prefix = "Good day";
        } else {
            prefix = "Hello";
        }

        String punctuation;
        if (isShouting) {
            punctuation = "!";
        } else {
            punctuation = ".";
        }

        String message = prefix + ", " + cleanName + punctuation;

        if (isShouting) {
            return message.toUpperCase();
        } else {
            return message;
        }
    }

    public void turnOn() {
        this.active = true;
    }

    public void turnOff() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}
