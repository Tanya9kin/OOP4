package OOP.Solution;

import java.lang.annotation.*;


@Target(ElementType.MODULE)
@Retention(RetentionPolicy.RUNTIME) // We need this to check whether some class has this annotation
public @interface OOPTestClass {
    enum OOPTestClassType {ORDERED,UNORDERED} // should be in the lowest acccess control, but unable to have it be private or proteteed
    OOPTestClassType value() default OOPTestClassType.UNORDERED;
}

