package org.dainn.charitybe.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("Uncategorized error", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("You do not have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),

    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),

    ROLE_EXISTED("Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED("Role not existed", HttpStatus.NOT_FOUND),

    EDUCATION_EXISTED("Education existed", HttpStatus.BAD_REQUEST),
    EDUCATION_NOT_EXISTED("Education not existed", HttpStatus.NOT_FOUND),

    EDUCATION_TYPE_EXISTED("Education type existed", HttpStatus.BAD_REQUEST),

    FINANCIAL_REPORT_EXISTED("Financial report existed", HttpStatus.BAD_REQUEST),
    FINANCIAL_REPORT_NOT_EXISTED("Financial report not existed", HttpStatus.NOT_FOUND),

    STUDENT_EXISTED("Student existed", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_EXISTED("Student not existed", HttpStatus.NOT_FOUND),
    STUDENT_ID_REQUIRED("Student must be set when campaign_for is STUDENT", HttpStatus.BAD_REQUEST),
    STUDENT_ID_NOT_REQUIRED("Student must be null when campaign_for is not STUDENT", HttpStatus.BAD_REQUEST),

    CATEGORY_EXISTED("Category existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED("Category not existed", HttpStatus.NOT_FOUND),

    SPONSOR_EXISTED("Sponsor existed", HttpStatus.BAD_REQUEST),
    SPONSOR_NOT_EXISTED("Sponsor not existed", HttpStatus.NOT_FOUND),

    PROJECT_EXISTED("Project existed", HttpStatus.BAD_REQUEST),
    PROJECT_NOT_EXISTED("Project not existed", HttpStatus.NOT_FOUND),

    DONATION_SPONSOR_NOT_EXISTED("donation-sponsor not existed", HttpStatus.NOT_FOUND),
    PRODUCT_SIZE_NOT_EXISTED("product-size not existed", HttpStatus.NOT_FOUND),

    PRODUCT_EXISTED("Product existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED("Product not existed", HttpStatus.NOT_FOUND),
    PRODUCT_CODE_EXISTED("Product code existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_EXISTED("Product name existed", HttpStatus.BAD_REQUEST),

    UPLOAD_IMAGE_FAILED("Upload image failed", HttpStatus.BAD_REQUEST),

    EMAIL_IS_INCORRECT("Email is incorrect", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_INCORRECT("Password is incorrect", HttpStatus.BAD_REQUEST),

    REFRESH_NOT_EXISTED("Refresh token not existed", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("Refresh token expired", HttpStatus.BAD_REQUEST),

    CART_ITEM_NOT_EXISTED("Cart item not existed", HttpStatus.NOT_FOUND),
    GOOGLE_LOGIN_FAILED("Authentication with Google fail", HttpStatus.BAD_REQUEST),;
    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
