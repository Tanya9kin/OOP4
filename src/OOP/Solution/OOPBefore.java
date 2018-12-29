package OOP.Solution;

import java.lang.annotation.*;

/*
A method which is annotated with this annotation will run before
all the methods in the array of method names it gets
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OOPBefore{
    String[] value() default ""; //just to have a way to check if it's empty
}

