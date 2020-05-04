package com.sobejko.marcin.springdata.problem;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

public class RunOnCustomSchedulerPointcut implements Pointcut {

    private final AnnotationMethodMatcher annotationMethodMatcher
            = new AnnotationMethodMatcher(RunOnCustomScheduler.class, false);

    @Override
    @NonNull
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return new StaticMethodMatcher() {
            @Override
            public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass) {
                if (annotationMethodMatcher.matches(method, targetClass)) {
                    if (!method.getReturnType().equals(Mono.class)) {
                        throw new IllegalArgumentException("@RunOnCustomScheduler annotated methods should return Mono<T>");
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        };
    }
}

