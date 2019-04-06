package me.xbones.reportplus.core.exception;

public class ReportPlusException extends RuntimeException {

    private ExceptionType type;

    public ReportPlusException(String errorMessage, Throwable err, ExceptionType type){
        super(errorMessage, err);
        this.type=type;
    }
    public ReportPlusException(String errorMessage, ExceptionType type){
        super(errorMessage);
        this.type=type;
    }

    public ExceptionType getType() {
        return type;
    }
}
