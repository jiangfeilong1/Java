package com.example.demo.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@ControllerAdvice(basePackages = {"com.example.demo"})
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler({BusException.class})
    @ResponseBody
    public ErrorInfo defaultExceptionHandler(HttpServletRequest request,Exception ex) throws Exception{
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setUrl(request.getRequestURI());
        errorInfo.setMessage(ex.getMessage());
        errorInfo.setCode(ErrorInfo.getSUCCESS());
        return errorInfo;
    }
}
