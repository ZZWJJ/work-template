//package com.zzw.learning.global;
//
//import com.zzw.learning.exception.ServiceException;
//import com.zzw.learning.response.DCResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.ValidationException;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author : shenjue
// * @Description: TODO
// * @date : 2019/11/26 16:41
// **/
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandlerResolver {
//    /**
//     * 表单验证方式的异常处理
//     *
//     * @param exception 异常
//     * @return 异常消息
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public DCResponse bodyValidException(MethodArgumentNotValidException exception) {
//        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//        return DCResponse.error(HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage());
//    }
//
//    /**
//     * 表单验证方式的异常处理  两个标记的表单验证失败会报这个异常
//     * form-data
//     *
//     * @param exception 异常
//     * @return 异常消息
//     */
//    @ExceptionHandler(BindException.class)
//    public DCResponse formValidException(BindException exception) {
//        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//        return DCResponse.error(HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage());
//    }
//
//        /**
//     * 表单参数验证直接写@NotNull 之类的,只取最近一个 @validate
//     * @param exception
//     * @return 验证失败返回
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public DCResponse handle(ValidationException exception) {
//        if (exception instanceof ConstraintViolationException) {
//            ConstraintViolationException exs = (ConstraintViolationException) exception;
//            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
//            for (ConstraintViolation<?> item : violations) {
//                /**打印验证不通过的信息*/
//                System.out.println(item.getMessage());
//            }
//            return DCResponse.error(HttpStatus.BAD_REQUEST.value(), violations.iterator().next().getMessage());
//        }
//        return DCResponse.error(HttpStatus.BAD_REQUEST.value(), "bad request ");
//    }
//
//    /**
//     * 业务的异常处理
//     *
//     * @param exception 异常
//     * @return 异常消息
//     */
//    @ExceptionHandler(ServiceException.class)
//    public DCResponse serviceExceptionHandle(ServiceException exception) {
//        return DCResponse.error(exception.getCode(),exception.getErrorMessage());
//    }
//
//}
