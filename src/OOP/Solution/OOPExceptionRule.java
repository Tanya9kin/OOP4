package OOP.Solution;

import java.lang.annotation.*;

/*
This annotation is used only to annotate Fields of the type OOPExpectedException
NOTE TO SELF: need to check if this will only e used on such fields or do we need to
                make Target be only this type - is it even possible?
*/
@Target(ElementType.FIELD) //should this be OOPExpectedException? didn't see a way to do that
@Retention(RetentionPolicy.RUNTIME)
public @interface OOPExceptionRule{
}
