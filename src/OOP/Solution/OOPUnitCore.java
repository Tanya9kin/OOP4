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
    LOGIC: BEWARE - shit is complicated and has lots of pitfalls
        * Setup:
            * make an unordered collection of all the OOPSetup methods of me and my daddys
            * run them
        * Test:
            * (probably in another function for readability)
            * make a collection for all the methods with annotation OOPTest
            * map : for each method make collection of the OOPBefore (unordered)
            *   these methods are taken from all the tests for me and my daddys
            * map : for each method make collection of the OOPAfter (unordered)
            * filter tests by tag (if tagFlag is True)
            * order tests by order if OOPTestClassType is ORDERED
            *       if some daddy is UNORDERED - order for them is 0 (default value)
            * for each method:
            *   run OOPBefore:
            *
            *   run method
            *   run OOPAfter
            *
     */

    //maybe write a function that gets all of the methods with annotation "***"
    //if we need it ordered we can make it so on demand
    static OOPTestSummery runClass(Class<?> testClass){
        runClassAux(testClass,false,"");
    }

    static OOPTestSummery runClass(Class<?> testClass, String tag){
        runClassAux(testClass,true,tag);
    }

    //WRITE ONLY THIS ONE WITH THE WHOLE DAMN LOGIC
    static OOPTestSummery runClassAux(Class<?> testClass, boolean tagFlag, String tag){

    }
}
