package com.seable.potato.data.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
 @Retention(RetentionPolicy.RUNTIME)
 public @interface UseCase {
     public String description() default "no description";
 }