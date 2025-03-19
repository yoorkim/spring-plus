package org.example.expert.domain.s3.exception;

public class S3ServiceException extends RuntimeException {
    public S3ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
