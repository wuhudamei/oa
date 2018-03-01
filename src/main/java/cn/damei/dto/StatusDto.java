package cn.damei.dto;
public class StatusDto<T> {
    private final static String SUCCESS_MSG = "Success!";
    private final static String FAILURE_MSG = "failure!";
    private final static String SUCCESS_CODE = "1";
    private final static String FAILURE_CODE = "0";
    protected String code;
    protected String message;
    protected T data;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public boolean isSuccess() {
        if ("1".equals(code))
            return true;
        return false;
    }
    public StatusDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static StatusDto buildSuccess() {
        return new StatusDto(SUCCESS_CODE, SUCCESS_MSG, null);
    }
    public static StatusDto buildSuccess(String message) {
        return new StatusDto(SUCCESS_CODE, message, null);
    }
    public static <E> StatusDto<E> buildSuccess(E data) {
        return new StatusDto(SUCCESS_CODE, SUCCESS_MSG, data);
    }
    public static <E> StatusDto<E> buildSuccess(String message, E data) {
        return new StatusDto(SUCCESS_CODE, message, data);
    }
    public static StatusDto buildFailure() {
        return new StatusDto(FAILURE_CODE, FAILURE_MSG, null);
    }
    public static StatusDto buildFailure(String message) {
        return new StatusDto(FAILURE_CODE, message, null);
    }
    public static <E> StatusDto<E> buildFailure(E data) {
        return new StatusDto(FAILURE_CODE, SUCCESS_MSG, data);
    }
    public static <E> StatusDto<E> buildFailure(String message, E data) {
        return new StatusDto(FAILURE_CODE, message, data);
    }
    public static StatusDto buildWithCode(String code, String message) {
        return new StatusDto(code, message, null);
    }
}