package uz.pdp.bazar.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String name){
        super(name);
    }
}
