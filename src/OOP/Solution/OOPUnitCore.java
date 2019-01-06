package OOP.Solution;

import OOP.Provided.OOPAssertionFailure;
import OOP.Provided.OOPExpectedException;
import OOP.Provided.OOPResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class OOPUnitCore {
    private Object test_instance; //Bring Object Oriented brograming (arab) stuff
    private List<Method> tests;
    private List<Method> before;
    private List<Method> after;
    private Map<String,List<Method>> all_before;
    private Map<String,List<Method>> all_after;
    private Object[] back_up;
    private Map <String,OOPResult> test_summery;
    private OOPExpectedException expected_exception;
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
    public static void assertEquals(Object expected, Object actual){
        if(!expected.equals(actual)){
            throw new OOPAssertionFailure();
        }
    }

    /*
    Used to fail a test by throwing OOPAssertionFailure
     */
    public void fail() throws OOPAssertionFailure {
        throw new OOPAssertionFailure();
    }

    /*
    LOGIC: BEWARE - shit is complicated and has lots of pitfalls
V        * Check if the class is a test class
V        * Make an instance of the class
V        * Setup by using setup method
V        * In another function:
            make a collection for all the methods with annotation OOPTest
            filter tests by tag (if tagFlag is True)
            if OOPTestClassType is ORDERED order tests by order
            if some daddy is UNORDERED - order for them is 0
            ^(no need to check this - this is the default value)

V         * OOPBefore collection:
            using getAnnotated:
            for each method make collection of the OOPBefore
            reverse list to get it to be sorted from father to son

V         * OOPAfter collection:
            using getAnnotated:
            for each method make collection of the OOPBefore
            no need to reverse list (already sorted son to father)

V         * In another function:
            make map<testMethod,List of OOPBefore methods>

V         * Do the same for OOPAfter
            map<testMethod,List of OOPAfter methods> :

         * Make an OOPTestSummary object.

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

        * for each method in tests:
            *for each OOPBefore method in all_tests for this method:
                Backup fields
                run method in try catch
                if the method throws an exception
                    restore fields
                    add OOPTestResult.ERROR for this test method
                    the message will be the name of the Exception class
                    on to the next test
            *run method in try catch block: (some reference shit here:)
        * if there is a field with annotation @OOPExceptionRule save it in variable
        check that it is non static and has a return value for expect other than null

        #3Q: can this be an exception of another class? I mean - not OOPExpectedException?
        A: nope
        #4Q: The expect method has to be invoked in the test - does it mean in each
            test method? if it is - how do we check this?
        A: we put null in it before each test

        catch (expected exception)
            if the message is the expected message all good - SUCCESS
               message is: ***
            else - EXPECTED_EXCEPTION_MISMATCH
                    message is: OOPExceptionMismatch.getMessage()
        catch (OOPAssertionFailure)
            FAILURE
            message is: ***
        catch (...)
            ERROR
            message is the class of the caught exception
            if no exception but we expected one:
                ERROR
                message is the class of the expected exception

        * run OOPAfter:
            * for each OOPAfter method in all_tests for this method:
                Backup fields
                run method in try catch
                if a method throws an exception
                restore fields
                add OOPTestResult.ERROR for this test method
                    the message will be the name of the Exception class
                move on to the next test

            Add an entry to the summery with the name of the method and result
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
    private void getAnnotated(Class<?> testClass, List<Method> methods, Class<? extends  Annotation> annot){
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
     */
    private void backup() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        back_up = new Object[test_instance.getClass().getDeclaredFields().length];
        int i=0;
        for(Field f : test_instance.getClass().getDeclaredFields()){
            f.setAccessible(true);
            Object current_field = f.get(test_instance);
            if(current_field==null){
                back_up[i]=null;
                continue;
            }
            try {
                Method f_clone = current_field.getClass().getMethod("clone");
                f_clone.setAccessible(true);
                back_up[i] = f_clone.invoke(current_field);
            } catch (NoSuchMethodException clone){
                try {
                    //Think about what happens if the FATHER has a copy constructor but I dont (Impossible?)
                    Constructor f_copy_ctr = current_field.getClass().getDeclaredConstructor(current_field.getClass());
                    f_copy_ctr.setAccessible(true);
                    back_up[i] = f_copy_ctr.newInstance(current_field);
                } catch (NoSuchMethodException CopyCtr){
                    back_up[i] = current_field;
                }
            }
            i++;
        }
    }

    /*
    Restore the values of the fields in the test object
     */
    private void restore() throws IllegalAccessException{
        int i=0;
        for(Field f : test_instance.getClass().getDeclaredFields()){
            f.setAccessible(true);
            f.set(back_up[i++].getClass(),back_up[i++]);
        }
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
        getAnnotated(test_instance.getClass(),methods,OOPSetup.class);
        Collections.reverse(methods);
        for (Method m : methods) {
            m.setAccessible(true);
            m.invoke(test_instance);
        }
    }


    /*
    makes all the resources for runTest.
     */
    private void getAllTests(boolean tagFlag,String tag) throws IllegalAccessException,InvocationTargetException{
      tests = new ArrayList<>(); //list of all methods annotated @OOPtest going up the inheritance
      before = new ArrayList<>(); //list of all methods annotated @OOPBefore going up the inheritance
      after = new ArrayList<>(); //list of all methods annotated @OOPAfter going up the inheritance
      all_before = new TreeMap<>();//map of lists of all methods annotated @OOPBefore and will run before each test method
      all_after = new TreeMap<>(); //map of lists of all methods annotated @OOPAfter and will run after each test method
      getAnnotated(test_instance.getClass(),tests,OOPTest.class);
      getAnnotated(test_instance.getClass(),before,OOPBefore.class);
      getAnnotated(test_instance.getClass(),after,OOPAfter.class);
      Collections.reverse(before);

      /*
      Filtering out the tests that won't be done before making the maps
      so that the maps will have lists for only the relevant methods
       */
      if(tagFlag){
            tests = tests.stream().filter(p-> p.getAnnotation(OOPTest.class).
                    tag().equals(tag)).collect(Collectors.toList());
      }
      //this is so that in the set***Map we can just add elements into existing lists
      for(Method m : tests){
          all_before.put(m.getName(),new ArrayList<Method>());
          all_after.put(m.getName(),new ArrayList<Method>());
      }
      setBeforeMap();
      setAfterMap();
    }
 /*
    Makes a map in which the key is a test method and value is list of methods that
    need to be run before the test method
     */
    private void setBeforeMap(){
        for(Method m : tests){
            for(Method k : before){
                if(Arrays.asList(k.getAnnotation(OOPBefore.class).value()).contains(m.getName()))
                    all_before.get(m.getName()).add(k);
            }
        }
    }

    /*
    Makes a map in which the key is a test method and value is list of methods that
    need to be run after the test method
     */
    private void setAfterMap(){
        for(Method m : tests){
            for(Method k : after){
                if(Arrays.asList(k.getAnnotation(OOPBefore.class).value()).contains(m.getName()))
                    all_after.get(m.getName()).add(k);
            }
        }
    }

    private void invokeMethods(Method m, Map<String,List<Method>> map) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Method k : map.get(m.getName())){
            backup();
            k.invoke(test_instance);
        }
    }

    //maybe write a function that gets all of the methods with annotation "***"
    //if we need it ordered we can make it so on demand
    public OOPTestSummary runClass(Class<?> testClass)
            throws InstantiationException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{

        return runClassAux(testClass,false,"");
    }

    public OOPTestSummary runClass(Class<?> testClass, String tag)
            throws InstantiationException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{
       return runClassAux(testClass,true,tag);
    }

    //WRITE ONLY THIS ONE WITH THE WHOLE DAMN LOGIC
     private OOPTestSummary runClassAux(Class<?> testClass, boolean tagFlag, String tag)
             throws InstantiationException,InvocationTargetException,IllegalAccessException,NoSuchMethodException,IllegalArgumentException{
        if(tag==null || testClass==null   ||  !testClass.isAnnotationPresent(OOPTestClass.class)){
            throw new IllegalArgumentException();
        }
        testClass.getConstructor().setAccessible(true);
        test_instance = testClass.getConstructor().newInstance();
        setup();
        getAllTests(tagFlag,tag);
        if(testClass.getAnnotation(OOPTestClass.class).value().equals(OOPTestClass.OOPTestClassType.ORDERED)){
            tests = tests.stream().
                    sorted((k1,k2)-> k1.getAnnotation(OOPTest.class).order() - k2.getAnnotation(OOPTest.class).order()).
                    collect(Collectors.toList());
        }
        test_summery = new TreeMap<>();

        if(Arrays.stream(test_instance.getClass().getDeclaredFields()).anyMatch(f-> f.getAnnotation(OOPExceptionRule.class)!= null))
        {
            /*
            TODO: get the field that is of type OOPExpectedException and save reference into variable for future use
                important note:
                we need the field from test_instance itself and not just from it's class
             */
            Object[] expected_temp_array  = Arrays.stream(test_instance.getClass().getDeclaredFields()).filter(f-> f.getAnnotation(OOPExceptionRule.class)!= null).toArray();
            expected_exception = (OOPExpectedException) expected_temp_array[0];
        }
        for(Method m : tests){
            //TODO: define variable flag for knowing if there was an exepction = false
            try {
                invokeMethods(m,all_before);
            } catch (Throwable e){
                restore();
                test_summery.put(m.getName(),new OOPResultImpl(OOPResult.OOPTestResult.ERROR, e.getClass().getName()));
                continue;
            }
            try {
                //TODO: put null into expectedException - so that we know it was changed within this test
                m.invoke(test_instance);
            } catch (/*expected exception*/) {
                /*
                TODO:
                exeption flag = true
                if message is the expected message
                    OOResultImpl variable with:
                        SUCCESS
                        message is: null
                else
                     OOResultImpl variable with:
                        EXPECTED_EXCEPTION_MISMATCH
                        message is: OOPExceptionMismatch.getMessage()
                 */
            } catch (OOPAssertionFailure e) {
                /*
                TODO:
                exeption flag = true
                OOResultImpl variable with:
                    FAILURE
                    message is: OOPAssertionFailure.getMessage
                 */
            } catch (Throwable e) {
                /*
                TODO:
                exeption flag = true
                    e != null
                        OOResultImpl variable with:
                            EXPECTED_EXCEPTION_MISMATCH
                            message is: e.getMessage
                    else
                        OOResultImpl variable with:
                            ERROR
                            message is: e.class.name
                 */
            }
            /*
            TODO:
                if expectedException not null but no exception caugth (exeption flag = false)
                    OOResultImpl variable with:
                        ERROR
                        message is: the class of the expected exception
                if expectedException null and not exception caught (exception flag = false)
                    OOResultImpl variable with:
                        SUCCESS
                        message is: null
             */
            try {
                invokeMethods(m,all_after);
            } catch (Throwable e){
                restore();
                /*
                NOTE: we are fine with the fact that here we might override what we put
                    during the test, because if this fails, the test fails
                */
                test_summery.put(m.getName(),new OOPResultImpl(OOPResult.OOPTestResult.ERROR, e.getClass().getName()));
                continue;
            }
        }
     return new OOPTestSummary(test_summery);
    }
}
