package ru.otus;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloOtus {

    public static void main(String[] args) throws Exception {
        TestLogging testLogging = new ByteBuddy()
                .subclass(TestLogging.class)
                .method(ElementMatchers.any())
                .intercept(Advice.to(LoggerAdvisor.class))
                .make()
                .load(TestLogging.class.getClassLoader())
                .getLoaded()
                .newInstance();
        testLogging.calculate(6);
    }
}







