package OOP.Solution;

import OOP.Provided.OOPResult;
import java.util.*;

public class OOPTestSummary {
    private Map<String,OOPResult> methodSumMap;

    public OOPTestSummary(Map<String,OOPResult> testMap){
        if(testMap != null){
            methodSumMap = new TreeMap<>();
            methodSumMap.putAll(testMap);
        }
    }

    private int getNum(OOPResult.OOPTestResult result){
        return (int)methodSumMap.values().stream().filter(a->(a.getResultType().equals(result))).count();

    }

    public int getNumSuccesses(){ return getNum(OOPResult.OOPTestResult.SUCCESS); }

    public int getNumFailures(){ return getNum(OOPResult.OOPTestResult.FAILURE); }

    public int getNumExceptionMismatches(){ return getNum(OOPResult.OOPTestResult.EXPECTED_EXCEPTION_MISMATCH);}

    public int getNumErrors(){ return getNum(OOPResult.OOPTestResult.ERROR); }
}
