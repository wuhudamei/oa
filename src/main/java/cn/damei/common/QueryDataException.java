package cn.damei.common;
@SuppressWarnings("all")
public class QueryDataException extends Exception{
    public QueryDataException(String message) {
        super( message );
    }
    public QueryDataException() {
        super();
    }
    public QueryDataException(String message, Throwable cause) {
        super( message, cause );
    }
    public QueryDataException(Throwable cause) {
        super( cause );
    }
}
