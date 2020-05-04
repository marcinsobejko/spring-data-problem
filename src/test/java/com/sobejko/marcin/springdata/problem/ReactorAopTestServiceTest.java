package com.sobejko.marcin.springdata.problem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactorAopTestServiceTest {

    @Autowired
    ReactorAopTestRepository reactorAopTestRepository;

    @Test
    @Repeat(3000)
    public void test() {
        Mono<String> result = reactorAopTestRepository.interceptedMethod();

        Mono.zip(result, result.flatMap(val -> reactorAopTestRepository.interceptedMethod())).block();
    }
}
