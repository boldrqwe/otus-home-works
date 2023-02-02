package annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import homework.AnnotationTest;

public class TestRunner {

    public static void main(String[] args) {
        parse(AnnotationTest.class);
    }

    public static void parse(Class<?> clazz) {

        Method[] declaredMethods = clazz.getDeclaredMethods();
        var count = 0;
        var pass = 0;
        var fail = 0;

        Optional<Method> before =
                Arrays.stream(declaredMethods)
                        .filter(method -> method.isAnnotationPresent(Before.class))
                        .findFirst();

        Optional<Method> after =
                Arrays.stream(declaredMethods)
                        .filter(method -> method.isAnnotationPresent(After.class))
                        .findFirst();

        var tests =
                Arrays.stream(declaredMethods)
                        .filter(method -> method.isAnnotationPresent(Test.class))
                        .toList();
        Optional<Object> beforeClass;
        for (var method : tests) {
            method.setAccessible(true);
            beforeClass = before.map(b -> runMethod(clazz, b));

            ++count;
            try {
                System.out.println("test № " + count);
                System.out.println("");
                Object o = beforeClass.get();
                method.invoke(o);
                ++pass;
                after.ifPresent(a -> runMethod(o.getClass(), a));
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            } catch (InvocationTargetException e) {
                ++fail;
                Throwable cause = e.getCause();
                System.out.println(Arrays.toString(cause.getStackTrace()));
                System.out.println("");
                System.out.println(cause.getMessage());
                System.out.println("");
            }
        }
        System.out.printf("total: %d \nfailed: %d \npassed: %d", tests.size(), fail, pass);

    }

    private static Object getNewInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object runMethod(Class<?> clazz, Method method) {
        method.setAccessible(true);
        Object obj = null;
        try {
            obj = clazz.newInstance();
            method.invoke(obj);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("");
        return obj;
    }
}



