package com.beifeng.DataUtil;

import java.math.BigDecimal;

/**
 * Created by 胡志洁 on 2016/7/10.
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String str)
    {
        if(str == null || str.length() == 0)
        {
            return true;
        }

        return false;

    }

    public static float parseFloat(String numStr){
        if(StringUtil.isNullOrEmpty(numStr)){
            return new Float(0);
        }
        else{
            try{
                return Float.parseFloat(numStr);
            }
            catch (Exception e){
                return new Float(0);
            }
        }
    }

    public static float parseInt(String numStr){
        if(StringUtil.isNullOrEmpty(numStr)){
            return new Integer(0);
        }
        else{
            try{
                return Integer.parseInt(numStr);
            }
            catch (Exception e){
                return new Integer(0);
            }
        }
    }

    public static BigDecimal BigDecimalFormat(String numStr){

        if(StringUtil.isNullOrEmpty(numStr)){
            return new BigDecimal(0);
        }
        else{
            try{
                BigDecimal num = new BigDecimal(numStr);
                return num;
            }
            catch (Exception e){
                return new BigDecimal(0);
            }
        }
    }

}
