package server.sport.exception;

public class EntityCannotBeProcessedExecption extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntityCannotBeProcessedExecption(String msg){
        super(msg);
    }
}
