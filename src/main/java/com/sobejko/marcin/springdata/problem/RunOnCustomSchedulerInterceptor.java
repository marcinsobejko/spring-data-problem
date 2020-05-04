package com.sobejko.marcin.springdata.problem;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

public class RunOnCustomSchedulerInterceptor implements MethodInterceptor {

    private final Scheduler scheduler;

    @Autowired
    public RunOnCustomSchedulerInterceptor(@Qualifier("customScheduler") Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        return Mono
                .fromSupplier(() -> invokeMethod(invocation))
                .flatMap(Function.identity())
                .subscribeOn(scheduler)
                .publishOn(Schedulers.parallel());
    }

    private Mono<?> invokeMethod(MethodInvocation invocation) {
        try {
            return (Mono<?>) invocation.proceed();
        } catch (Throwable t) {
            throw Exceptions.propagate(t);
        }
    }
}

