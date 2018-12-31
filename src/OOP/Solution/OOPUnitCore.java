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
            * make an ordered collection of all the OOPSetup methods of me and my daddys
            * order by inheritence from father to son
            * if there is an override - use the most recent one
            * LOGIC:
            *       start with me and go up the inheritance -
            *       get all the methods annotated @OOPSetup and put in a list
            *           if you see another method with same name, don't put it in
            *       reverse list
            *       run all by order
        * Test:
            * (probably in another function for readability)
            * make a collection for all the methods with annotation OOPTest
            * map : for each method make collection of the OOPBefore
            *       ordered by inheritance from father to son
            *   these methods are taken from all the tests for me and my daddys
            * map : for each method make collection of the OOPAfter (unordered) same way
            * filter tests by tag (if tagFlag is True)
            * order tests by order if OOPTestClassType is ORDERED
            *       if some daddy is UNORDERED - order for them is 0 (default value)
            * Make an OOPTestSummery object.
            * for each method:
            *   Make backup collection\object\have no idea yet
            *   Probably in another function
            *   Backup the objects fields (without going up the inheritance)
            *       backup by using one of the following (order matters):
            *       1.if the object has clone, make a clone of it
            *       2.if the object has copy contructor use it
            *       3.otherwise save it as it is (reference semantics)
            *   for each OOPBefore method:
            *       Backup fields
            *       run method in try catch
            *       if a method throws an exception
            *           restore fields
            *           add OOPTestResult.ERROR for this test method
            *           on to the next test
            *   run method:
            *
            *   run OOPAfter:
            *       Backup fields
            *       run method in try catch
            *       if a method throws an exception
            *           restore fields
            *           add OOPTestResult.ERROR for this test method
            *           move on to the next test
            *
            *
            *  Add an entry to the summery with the name of the method and result
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
