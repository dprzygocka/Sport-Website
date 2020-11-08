package server.sport.exception;

public class ForbiddenActionException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ForbiddenActionException(String msg){
        super(msg);
    }
}
