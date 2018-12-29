package OOP.Solution;

import java.lang.annotation.*;

/*
A method with this annotation may run only ones
It will run before any other method from the tests can run
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OOPSetup{}

