package ar.com.laboratory.besttravel.util.exceptions;

public class IdNotFoundException extends RuntimeException{

    private final static String ERROR_MESSAGE = "Record no exist in %s";

    public IdNotFoundException(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}
