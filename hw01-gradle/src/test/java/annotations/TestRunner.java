package annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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

        Optional<Method> before = getMethodByAnnotation(declaredMethods, Before.class).findFirst();

        Optional<Method> after = getMethodByAnnotation(declaredMethods, After.class).findFirst();

        var tests = getMethodByAnnotation(declaredMethods, Test.class).toList();
        for (var method : tests) {
            method.setAccessible(true);
            Object newInstance = getNewInstance(clazz);
            before.map(b -> runMethod(newInstance, b));

            ++count;
            try {
                System.out.println("test № " + count);
                System.out.println("");
                method.invoke(newInstance);
                ++pass;
                runMethod(newInstance, after.get());
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
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Method> getMethodByAnnotation(Method[] declaredMethods,
                                                        Class<? extends Annotation> annotationClass) {
        return Arrays.stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(annotationClass));
    }

    private static Object runMethod(Object instance, Method method) {
        method.setAccessible(true);
        try {
            method.invoke(instance);
            return instance;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("");
        return instance;
    }
}



