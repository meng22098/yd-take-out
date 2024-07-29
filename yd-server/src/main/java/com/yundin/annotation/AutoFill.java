package com.yundin.annotation;

import com.yundin.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//用在方法上
@Retention(RetentionPolicy.RUNTIME)//在什么时候有用
public @interface AutoFill {
    OperationType value();
}
