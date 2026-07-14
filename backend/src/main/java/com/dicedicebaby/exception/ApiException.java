package com.dicedicebaby.exception;

import com.dicedicebaby.enums.ApiErrorCode;

public class ApiException extends RuntimeException {

  private final ApiErrorCode errorCode;

  public ApiException(ApiErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ApiErrorCode getErrorCode() {
    return errorCode;
  }
}
