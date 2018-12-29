package OOP.Solution;

import java.lang.annotation.*;

/*
A method with this annotation will run after all the methods
in the array of method names it got
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OOPAfter{
    String[] value() default ""; //the default value is only to be able to check if it's empty
}

