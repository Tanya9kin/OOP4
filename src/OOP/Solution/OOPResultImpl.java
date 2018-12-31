package OOP.Solution;

import OOP.Provided.OOPResult;

public class OOPResultImpl implements OOPResult {

    private OOPTestResult result_type;
    private String message;

    public OOPResultImpl(OOPTestResult new_result, String new_message){
        result_type = new_result;
        message = new_message;
    }
    /*
    for later.....implementation:
    get the expected exceptions of the test class and the message for it
    check that it was
    SUCCESS -
            * there was no exception thrown or
            * if the exception that was thrown is expected and has the message
            that we expected in OOPExpectedException in the tests class
            *a test method is expecting an exception if it has a non static field of the type OOPExpectedException
            and has something other then null to return. (expect() method was done on it)
            * the expect() method needs to be invoked IN THE TEST  - very important
            * return null in this case

    if an unexpected exception was thrown
    FAILURE -
            *if it was OOPAssertionFailure
    EXCPECTED_EXCEPTION_MISMATCH -
            *if it wasn't OOPAssertionFailure
            * AND
            * it was an unexpected exception
            * OR  expected exception but the message was off (didn't exist or was wrong)
            * return OOPExceptionMismatch.getMessage()
    ERROR -
            *an exception was thrown that we did not expect
            * or no exception was thrown although we expected it
            * return the class of the exception (we expected or did not expect)
     */
    @Override
    public OOPTestResult getResultType() { return result_type; }

    @Override
    public String getMessage() {
        return message;
    }

    //need to use getMessage() and getResultType() in this
    @Override
    public boolean equals(Object obj){
        if(obj == null || !obj.getClass().equals(OOPResultImpl.class)){
            return false;
        }
        OOPResultImpl real_obj = (OOPResultImpl)obj;
        if(real_obj.getResultType() == result_type && real_obj.getMessage().equals(message)){
            return true;
        }
    };
}