package ru.otus;

import com.google.common.base.Joiner;

public class HelloOtus {
    public static void main(String... args) {

        Joiner on = Joiner.on(" ");
        System.out.println(on.join("O", "T", "U", "S"));
    }
}
