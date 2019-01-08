package OOP.Solution;

import OOP.Provided.OOPExpectedException;

public class OOPExpectedExceptionImpl implements OOPExpectedException{

    private Class<? extends Exception> exp_exception; //field for the expected exception
    private String message;//field for the expected exception message

    private OOPExpectedExceptionImpl(){
        exp_exception = null;
        message = null;
    }

    @Override
    public Class<? extends Exception> getExpectedException() {
        return exp_exception;
    }

    @Override
    public OOPExpectedException expect(Class<? extends Exception> expected) {
        //if(expected != null) WAS NOT SET%TING NULL BOY
            exp_exception = expected;
        return this;
    }

    //"Can expect several messages" --> this is why I concatenated it and not just changed the string
    @Override
    public OOPExpectedException expectMessage(String msg) {
        //if(msg != null) WAS NOT SETTING NULL
            message = msg; // WWW says it works fine (implicitly makes a new string)
        return this;
    }

    /*
    A.isAssignableFrom(B) --> A is a superclass of B
    I used "contains" for the string; should check that is actually works because it wants to get
    a CharSequence as it's variable - string implements it so should be fine
    */
    @Override
    public boolean assertExpected(Exception e) {
        if(e == null){
            return (exp_exception == null);
        }
        try{
            String test = e.getMessage();
            if(exp_exception.isAssignableFrom(e.getClass())){

                if(e.getMessage()==null && message==null)
                    return true;
                if(e.getMessage()!= null && e.getMessage().contains(message))
                    return true;
            }
        } catch (NullPointerException nullDereference) {
            return message == null;
        }
        return false;
    }

    public static OOPExpectedExceptionImpl none() {
        return new OOPExpectedExceptionImpl();
    }
}
