package OOP.Solution;

import OOP.Provided.OOPResult;

public class OOPResultImpl implements OOPResult {

    private OOPTestResult result_type;
    private String message;

    public OOPResultImpl(OOPTestResult new_result, String new_message){
        result_type = new_result;
        message = new_message;
    }

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
        return false;
    };

    //TO DO:
    // need to write hash
    //need to check if in equals we need to call the equals of super
}
