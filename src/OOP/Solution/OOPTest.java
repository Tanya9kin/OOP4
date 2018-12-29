package OOP.Solution;

import java.lang.annotation.*;

/*
A method with this annotation is an actual test
order - indicated when this method will run if the class of the test
is annotated with OOPTestClass(OOPTestClassType = ORDERD)
this field does not matter if the class is annotated with UNORDERED

The actual order always starts with 1 and is always continuous
(no need to check this)

tag - a string which helps if we want to run only this method
or only methods with this tag
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OOPTest{
    int order();
    String tag() default "";
}

