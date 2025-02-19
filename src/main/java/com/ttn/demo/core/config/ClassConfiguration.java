package com.ttn.demo.core.config;



import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Class Configuration", description = "Configuration for class limits and passing marks")
public @interface ClassConfiguration {
    int studentLimit() default 30;
    int passingMarks() default 40;
}

