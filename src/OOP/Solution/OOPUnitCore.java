package OOP.Solution;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class OOPUnitCore {
    private Object boobs; //Bring Object Oriented brograming (arab) stuff
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
        * Check if the class is a test class
        * Make an instance of the class
        * Setup by using setup method
        * Test:
            * In another function:
            *   by using getAnnotated:
            *       make a collection for all the methods with annotation OOPTest
            *   filter tests by tag (if tagFlag is True)
            *   if OOPTestClassType is ORDERED order tests by order
            *       if some daddy is UNORDERED - order for them is 0
            *       ^(no need to check this - this is the default value)
            *
            * OOPBefore collection:
            *   using getAnnotated:
            *       for each method make collection of the OOPBefore
            *   reverse list to get it to be sorted from father to son
            *
            * OOPAfter collection:
            *   using getAnnotated:
            *       for each method make collection of the OOPBefore
            *   no need to reverse list (already sorted son to father)
            *
            * In another function:
            *   make map<testMethod,List of OOPBefore methods>
            *
            * Do the same for OOPAfter
            *   map<testMethod,List of OOPAfter methods> :
            *
            * Make an OOPTestSummery object.
            * for each method:
            *   Make backup collection\object\have no idea yet
            *   By using the backUp method
            *
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

    /*
    MAKES A LIST WHICH CONSISTS OF ALL THE METHODS WITH annot IN testClass
    AND IT'S DADDYS. THE LIST IS SORTED FROM SON TO FATTHER
    LOGIC:
        get new empty list - I can trust this because I am the only one using this
        go over the testClass methods,
        if has annot and we didn't have this one already in the list:
            put it into the list
        do the same for daddy of testClass recursively, until we get to the answer to #5
        #5Q: how do we know when to stop? at Object?
            do we go up the inheritance only if the father has @OOPTestClass annotation?
        A: Yes, Object will return null as superclass
        #6Q: is it ok to declare List<Method>?
        A: I think so
     */
    static void getAnnotated(Class<?> testClass, List<Method> methods, Class<? extends  Annotation> annot){
            if(testClass==null){
                return;
            }

            methods.addAll(Arrays.stream(testClass.getDeclaredMethods()).
                    filter(m-> !methods.contains(m)&& m.isAnnotationPresent(annot)).collect(Collectors.toList()));
            getAnnotated(testClass.getSuperclass(),methods,annot);
    }

    /*
    Backup the objects fields (without going up the inheritance)
        backup by using one of the following (order matters):
            1.if the object has clone, make a clone of it
            2.if the object has copy constructor use it
            3.otherwise save the actual object
     LOGIC:
        **maybe this should be done in runClass and not here:
            **(probably as privet field for unitCore) make array of fields (getFields)
        make array of objects (this will be the backup)

        for each field, put into backup array:
            set accessible
            in a try-catch:
               try clone
               try copy constructor
            if all fail: save the object itself (reference)
     */

    static void backup(){

    }

    /*
    Restore the values of the fields in the test object
    LOGIC:
        for each field in the field array (private variable of unitCore)
            set accessible
            fields[i] = backup[i]
     */
    private void restore(){

    }

    /*
     * Setup:
     * by using getAnnotated:
     *   make an ordered collection of all the OOPSetup methods of me and my daddys
     *   methods ordered from son to father
     * reverse list and get the same ordered from father to son
     * run all by order
     */
    private void setup()
            throws IllegalAccessException,InvocationTargetException{
        List<Method> methods = new ArrayList<Method>();
        getAnnotated(boobs.getClass(),methods,OOPSetup.class);
        Collections.reverse(methods);
        for (Method m : methods) {
            m.setAccessible(true);
            m.invoke(boobs);
        }
    }
    //maybe write a function that gets all of the methods with annotation "***"
    //if we need it ordered we can make it so on demand
    public OOPTestSummery runClass(Class<?> testClass)
            throws InstantiationException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{

        return runClassAux(testClass,false,"");
    }

    public OOPTestSummery runClass(Class<?> testClass, String tag)
            throws InstantiationException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{
       return runClassAux(testClass,true,tag);
    }

    //WRITE ONLY THIS ONE WITH THE WHOLE DAMN LOGIC
     private OOPTestSummery runClassAux(Class<?> testClass, boolean tagFlag, String tag)
             throws InstantiationException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{
        OOPTestSummery p;
        testClass.getConstructor().setAccessible(true);
        boobs = testClass.getConstructor().newInstance();
     return p;
    }
}
