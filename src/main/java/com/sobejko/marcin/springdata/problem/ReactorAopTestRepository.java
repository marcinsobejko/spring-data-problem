package com.sobejko.marcin.springdata.problem;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Repository
public class ReactorAopTestRepository {

    @RunOnCustomScheduler
    public Mono<String> interceptedMethod() {
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (Throwable tr) {
        }
        return Mono.just("value");
    }
}
