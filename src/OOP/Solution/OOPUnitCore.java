package OOP.Solution;

public class OOPUnitCore {

    /*
    #1 Q: why static?
    A:

    #2 Q:what if the two objects are null? is it true?
    A:

    This method checks if the two objects are equal value-wise
    LOGIC:
        * if they are null - return value bases on #2
        * if they are of same class - continue else assertionFailure
        * if the answer to their equals function (maybe to sided) = false
          assertionFailure
     */
    static void assertEquals(Object expected, Object actual){

    }

    /*
    Used to fail a test by throwing OOPAssertionFailure
     */
    static void fail(){

    }

    /*

     */
    static OOPTestSummery runClass(Class<?> testClass){

    }

    /*
    if tag is null or testClass doesn't have OOPTestClass annotation on it:
        throw IllegalArgumentException

     */
    static OOPTestSummery runClass(Class<?> testClass, String tag){

    }
}
