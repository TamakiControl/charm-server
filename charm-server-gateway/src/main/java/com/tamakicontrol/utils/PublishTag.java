package com.tamakicontrol.utils;

import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field annotation that tells the POJOMapper which fields in a Class are to be published to the tag provider
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishTag {
    String name();
    DataType dataType();
}
