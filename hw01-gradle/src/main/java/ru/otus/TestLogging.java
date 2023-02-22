package ru.otus;

public class TestLogging implements TestLoggingInterface {
    @Log
    public int calculate(int value) {
        System.out.println("calculate: " + value);
        return value;
    }

    public int bar(int value) {
        System.out.println("bar: " + value);
        return value;
    }
}