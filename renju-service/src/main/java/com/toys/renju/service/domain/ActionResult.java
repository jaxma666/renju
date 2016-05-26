package com.toys.renju.service.domain;

import com.toys.renju.service.code.ErrorCode;

/**
 * Created by lingyao on 16/5/26.
 */

public class ActionResult<T> {

    /**
     * api调用是否成功
     */
    private boolean success = true;
    /**
     * 错误码
     */
    private ErrorCode errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 返回结果
     */
    private T result;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorCode.getDefaultMsg();
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setSuccessResult(T result) {
        success = true;
        this.result = result;
    }


}
