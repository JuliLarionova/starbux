package com.bstlr.starbux.web;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.common.Errors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private static final String EXCEPTION_TYPE = "exception_type";
    private static final String EXCEPTION_MESSAGE = "exception_message";
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String ERROR_ID = "errorId";
    private static final String GOTO_WMS_SUPPORT_SERVICE_ADVICE = "Системная ошибка № %s. Обратитесь в тех. поддержку";
    private static final String OUTDATED_ENTITY_VERSION = "Данные были обновлены другим пользователем. Пожалуйста, обновите данные и повторите операцию";

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<String> handleClientException(ClientException ex, WebRequest request) {
        var response = Errors.builder()
                .message(ex.getMessage())
                .code(ex.getCode())
                .build();
        //logException("ClientException", String.format("ClientException %s", response), request, response, ex, log::info);
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }
}
/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Errors> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        MessageInterpolator msgInterpolator = beanValidator.getMessageInterpolator();
        var messages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> new Error(msgInterpolator.interpolate(error.getDefaultMessage(), null)))
                .collect(Collectors.toList());
        return handleValidationException(ex, request, messages);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    protected ResponseEntity<Errors> handleWebExchangeBindException(WebExchangeBindException ex,
                                                                    WebRequest request) {
        var messages = ex.getAllErrors().stream()
                .map(e -> new Error(e.getDefaultMessage()))
                .collect(Collectors.toList());
        return handleValidationException(ex, request, messages);
    }

    private ResponseEntity<Errors> handleValidationException(Exception ex, WebRequest request,
                                                             List<Error> messages) {
        var response = Errors.clientErrors(messages);

        logException("ValidationException",
                String.format("ValidationException %s", response),
                request,
                response,
                ex,
                log::info);
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Errors> handleException(Throwable ex, WebRequest request) {
        String errorId = UUID.randomUUID().toString();
        var response = Errors
                .serverError(new Error(String.format(GOTO_WMS_SUPPORT_SERVICE_ADVICE, errorId)));
        MDC.put(ERROR_ID, errorId);

        logException("Unexpected error",
                String.format("Unexpected error %s, %s", errorId, response),
                request,
                response,
                ex,
                log::error);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private ResponseEntity<Errors> handleRestClientException(List<Error> clientErrors, String remoteService,
                                                             Exception ex, WebRequest request) {
        var response = Errors.clientErrors(clientErrors);
        logException("ClientException", format("ClientException %s from rest client %s", response, remoteService),
                request, response, ex, log::info);
        return ResponseEntity.badRequest().body(response);
    }

    private void logException(String type, String message, WebRequest request, Errors response, Throwable ex,
                              BiConsumer<String, Throwable> logFunction) {
        addParamsTypeToMDC(type, request, response, ex.getMessage());
        logFunction.accept(message, ex);
        MDC.clear();
    }

}*/
