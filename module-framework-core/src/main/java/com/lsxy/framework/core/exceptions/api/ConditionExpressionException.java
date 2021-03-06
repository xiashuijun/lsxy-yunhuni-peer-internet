package com.lsxy.framework.core.exceptions.api;

/**
 * Created by liups on 2016/11/14.
 */
public class ConditionExpressionException extends YunhuniApiException {
    public ConditionExpressionException(Throwable t) {
        super(t);
    }

    public ConditionExpressionException() {
        super();
    }

    public ConditionExpressionException(String context) {
        super(context);
    }

    public ConditionExpressionException(ExceptionContext context){
        super(context);
    }

    @Override
    public ApiReturnCodeEnum getApiExceptionEnum() {
        return ApiReturnCodeEnum.ConditionExpression;
    }
}
