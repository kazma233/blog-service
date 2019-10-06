package com.kazma233.blog.config.handler;

import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.exception.parent.CustomizeException;
import com.kazma233.blog.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Controller
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @ExceptionHandler(Exception.class)
    public BaseResult exception(Exception e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return BaseResult.failed(Status.UN_SUPPORT_METHOD);
        }

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            ValidationUtils.checkFieldErrors(manve.getBindingResult());
        }

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(request.getRequestURI() + " has an error: ", e);
        return BaseResult.failed(Status.UN_KNOW_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = CustomizeException.class)
    public BaseResult myException(CustomizeException ve) {
        log.error(request.getRequestURI() + ": 自定义异常: ", ve);

        return BaseResult.failed(ve);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {ShiroException.class})
    public BaseResult shiroExceptionHandle(ShiroException e) {
        if (e instanceof AuthenticationException) {
            return BaseResult.failed(Status.LOGIN_ERROR);
        }

        log.error(request.getRequestURI() + ": shiro异常: ", e);

        return BaseResult.failed(Status.UN_AUTH_ERROR);
    }

}