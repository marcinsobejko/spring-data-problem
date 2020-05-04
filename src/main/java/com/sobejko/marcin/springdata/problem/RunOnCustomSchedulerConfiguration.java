package com.sobejko.marcin.springdata.problem;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class RunOnCustomSchedulerConfiguration {

    @Bean("customScheduler")
    public Scheduler scheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(30, new ThreadFactoryBuilder()
                .setNameFormat("custom-scheduler-%s")
                .build()));
    }

    @Bean
    public RunOnCustomSchedulerPointcut runOnCustomSchedulerPointcut() {
        return new RunOnCustomSchedulerPointcut();
    }

    @Bean
    public RunOnCustomSchedulerInterceptor runOnCustomSchedulerInterceptor(@Qualifier("customScheduler") Scheduler scheduler) {
        return new RunOnCustomSchedulerInterceptor(scheduler);
    }

    @Bean
    public PointcutAdvisor runOnCustomSchedulerPointcutAdvisor(RunOnCustomSchedulerPointcut pointcut,
                                                               RunOnCustomSchedulerInterceptor interceptor) {
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
