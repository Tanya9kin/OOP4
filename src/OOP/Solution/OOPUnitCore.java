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
            *       these methods are taken from all the tests for me and my daddys
            *       ordered by inheritance from father to son
            *       LOGIC:
            *           start with me and go up the inheritance -
            *           get all the OOPBefore which have the methods name in their list
            *           add them to this list
            *           if you see another method with the same name, don't put it in
            *           reverse list
            * map : for each method make collection of the OOPAfter same way only son to father order
            *
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
            *               the message will be the name of the Exception class
            *           on to the next test
            *   run method in try catch block: (some reference shit here:)
            SUCCESS -
                * there was no exception thrown or
                * if the exception that was thrown is expected and has the message
                    that we expected in OOPExpectedException in the tests class
                * a test method is expecting an exception if it has a non static field of the type OOPExpectedException
                    and has something other then null to return. (expect() method was done on it)
                * the expect() method needs to be invoked IN THE TEST  - very important
                * return null in this case

            if an unexpected exception was thrown:
            FAILURE -
                *if it was OOPAssertionFailure
            EXPECTED_EXCEPTION_MISMATCH -
                *if it wasn't OOPAssertionFailure
                * AND
                * it was an unexpected exception
                * OR  expected exception but the message was off (didn't exist or was wrong)
                * return OOPExceptionMismatch.getMessage()
            ERROR -
                *an exception was thrown that we did not expect
                * or no exception was thrown although we expected it
                * return the class of the exception (we expected or did not expect)

            AND THAT IS WHY LOGIC:
                * if there is a field with annotation @OOPExceptionRule save it in variable
                * check that it is non static and has a return value for expect other than null
                * #3Q: can this be an exception of another class? I mean - not OOPExpectedException?
                * A:
                * #4Q: The expect method has to be invoked in the test - does it mean in each
                *   test method? if it is - how do we check this?
                * A:
                *
                * catch (expected exception)
                *   if the message is the expected message all good - SUCCESS
                *       message is:
                *   else - EXPECTED_EXCEPTION_MISMATCH
                *       message is: OOPExceptionMismatch.getMessage()
                * catch (OOPAssertionFailure)
                *   FAILURE
                *   message is:
                * catch (...)
                *   ERROR
                *   message is the class of the caught exception
                * if no exception but we expected one:
                *   ERROR
                *   message is the class of the expected exception
                *
            *   run OOPAfter:
            *       Backup fields
            *       run method in try catch
            *       if a method throws an exception
            *           restore fields
            *           add OOPTestResult.ERROR for this test method
            *               the message will be the name of the Exception class
            *           move on to the next test
            *
            *
            *  Add an entry to the summery with the name of the method and result
            *
     */

    static getAnnotated(){

    }

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
