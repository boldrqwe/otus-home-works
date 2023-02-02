package homework;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class AnnotationTest {

    String string = null;

    @Before
    void before() {
        System.out.println("before");
        string = "go";
    }

    @Test
    void test1test() {
        System.out.println(string + " test");

    }

    @Test
    void test2test() {
        throw new RuntimeException("exception");
    }

    @Test
    void test3test() {
        System.out.println(string + " test");
    }

    @After
    void after() {
        System.out.println("after");
        string = null;
    }

}
