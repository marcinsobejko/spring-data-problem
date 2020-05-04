/*
 * Copyright (c) {$DateInfo.year} Gigaset Communications GmbH
 *
 */

package com.sobejko.marcin.springdata.problem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RunOnCustomScheduler {
}