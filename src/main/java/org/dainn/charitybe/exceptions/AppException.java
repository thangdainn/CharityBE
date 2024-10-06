package org.dainn.charitybe.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.dainn.charitybe.enums.ErrorCode;

@Getter
@Setter
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
