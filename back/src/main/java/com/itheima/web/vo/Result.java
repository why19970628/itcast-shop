package com.itheima.web.vo;

import com.itheima.constants.Global;

public class Result {

    private int code;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(Global.RESULT_CODE_SUCCESS);
        result.setData(data);
        return result;
    }
    public static Result success(){
        Result result = new Result();
        result.setCode(Global.RESULT_CODE_SUCCESS);
        return result;
    }
    public static Result fail(){
        Result result = new Result();
        result.setCode(Global.RESULT_CODE_FAIL);
        return result;
    }
    public static Result fail(Object data){
        Result result = new Result();
        result.setCode(Global.RESULT_CODE_FAIL);
        result.setData(data);
        return result;
    }
    public static Result needlogin(){
        Result result = new Result();
        result.setCode(Global.RESULT_CODE_NEED_LOGIN);
        return result;
    }
    public static Result authFail(){
        Result result = new Result();
        result.setCode(Global.RESULT_CODE_NO_AUTH);
        return result;
    }

}
