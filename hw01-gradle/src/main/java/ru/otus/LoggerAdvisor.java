package ru.otus;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.bytebuddy.asm.Advice;

class LoggerAdvisor {
    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        if (method.getAnnotation(Log.class) != null) {
            System.out.println("executed method:  " + method.getName() + ",  param:  " + Arrays.toString(arguments));
        }
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
    }
}
