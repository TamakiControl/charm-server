package com.tamakicontrol.utils;

import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishTag {
    String name();
    DataType dataType();
}
