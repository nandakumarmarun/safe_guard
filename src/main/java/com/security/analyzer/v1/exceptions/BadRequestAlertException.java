package com.security.analyzer.v1.exceptions;

import java.net.URI;

import com.security.analyzer.v1.exceptions.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ProblemDetail;

public class BadRequestAlertException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(HttpStatus.BAD_REQUEST, defaultMessage, null);
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public ProblemDetail getProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, getReason());
        problemDetail.setType(ErrorConstants.DEFAULT_TYPE);
        problemDetail.setTitle(getReason());
        problemDetail.setProperty("message", "error." + errorKey);
        problemDetail.setProperty("params", entityName);
        return problemDetail;
    }
}
