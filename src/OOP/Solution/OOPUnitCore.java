package OOP.Solution;

import OOP.Provided.OOPAssertionFailure;

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
     LOGIC:
        instantiate the back_up array with new map
        for each field:
            set accessible
            in a try-catch:
               try clone and put into map
               try copy constructor and put into map
            if all fail: save the object itself (reference) into map
     Q: does it have to be a map? Maybe this can be an array of Objects?
        because every time we do getFields we have the same answer..?
     A:
     */
    /*
    Backup the objects fields (without going up the inheritance)
        backup by using one of the following (order matters):
            1.if the object has clone, make a clone of it
            2.if the object has copy constructor use it
            3.otherwise save the actual object
     LOGIC:
        instantiate the back_up array with new array to erase the last backup
        for each field:
            set accessible
            in a try-catch:
               try clone and put into map
               try copy constructor and put into map
            if all fail: save the object itself (reference) into map
     Q: does it have to be a map? Maybe this can be an array of Objects?
        because every time we do getFields we have the same answer..?
     A: yep
     */
    private void backup() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        back_up = new Object[test_instance.getClass().getDeclaredFields().length];
        int i=0;
        for(Field f : test_instance.getClass().getDeclaredFields()){
            f.setAccessible(true);
            Object current_field = f.get(test_instance);
            try {
                Method f_clone = current_field.getClass().getMethod("clone");
                f_clone.setAccessible(true);
                back_up[i] = f_clone.invoke(current_field);
            } catch (NoSuchMethodException clone){
                try {
                    Constructor f_copy_ctr = current_field.getClass().getDeclaredConstructor(f.getClass());
                    f_copy_ctr.setAccessible(true);
                    back_up[i] = f_copy_ctr.newInstance(current_field);
                } catch (NoSuchMethodException CopyCtr){
                    back_up[i] = f;
                }
            }
            i++;
        }
    }

    /*
    Restore the values of the fields in the test object
    LOGIC:
        for each field in the field array (private variable of unitCore)
            set accessible
            fields[i] = backup[i]
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
    tests - list of all methods annotated @OOPtest going up the inheritance
    before - list of all methods annotated @OOPBefore going up the inheritance
    after -  list of all methods annotated @OOPAfter going up the inheritance
    all_before - map of lists of all methods annotated @OOPBefore
                 and will run before each test method
    all_after - map of lists of all methods annotated @OOPAfter
                 and will run after each test method

     */
    private void getAllTests(boolean tagFlag,String tag) throws IllegalAccessException,InvocationTargetException{
      tests = new ArrayList<>();
      before = new ArrayList<>();
      after = new ArrayList<>();
      all_before = new TreeMap<>();
      all_after = new TreeMap<>();
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

    //Tanyas version - which she thinks is a bit clearer and less error prone
    //              also doesn't throw and may be easier to debug
    /*
    Makes a map in which the key is a test method and value is list of methods that
    need to be run before the test method
    LOGIC:
        for each test method
            for each before method
                put into the list in the map that ccorrelates with the method name
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
    LOGIC:
        for each test method
            for each before method
                put into the list in the map that ccorrelates with the method name
     */
    private void setAfterMap(){
        for(Method m : tests){
            for(Method k : after){
                if(Arrays.asList(k.getAnnotation(OOPBefore.class).value()).contains(m.getName()))
                    all_after.get(m.getName()).add(k);
            }
        }
    }

    /*
    //Nimrods version
    private void setBeforeAfterMaps(Map<String,List<Method>> map, List<Method> list, Class<? extends Annotation> annot)
            throws IllegalAccessException, InvocationTargetException{
        for(Method m : tests){
            List<Method> temp = new ArrayList<Method>();
            List<String> vals = new ArrayList<String>();
            for(Method k : list){
                for(Method t : k.getAnnotation(annot).annotationType().getDeclaredMethods()){
                    if(t.getName().equals("value")) {
                        t.setAccessible(true); // Just for safety.
                        vals.addAll(Arrays.asList((String[])t.invoke(annot)));
                    }
                }
                if(vals.contains(m.getName())){
                    temp.add(k);
                }
            }
            map.put(m.getName(),temp);
        }
    }
    */


    private void invokeMethods(Method m, Map<String,List<Method>> map) throws IllegalAccessException,InvocationTargetException{
        for(Method k : map.get(m)){
            k.invoke(test_instance);
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
     private  OOPTestSummery runClassAux(Class<?> testClass, boolean tagFlag, String tag)
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
        backup();

        //Brain at 5am - Nope. I know this is missing shit. Fix me when either one of us is up.
        for(Method m : tests){
            try {
                invokeMethods(m,all_before);
                backup();
                m.invoke(test_instance);
                invokeMethods(m,all_after);
            } catch (Exception e){
                restore();
            }
        }


        OOPTestSummery p = new OOPTestSummery();
     return p;
    }
}
