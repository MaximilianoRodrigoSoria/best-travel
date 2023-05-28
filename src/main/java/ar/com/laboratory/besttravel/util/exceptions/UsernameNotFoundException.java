package ar.com.laboratory.besttravel.util.exceptions;

public class UsernameNotFoundException extends RuntimeException{

    private final static String ERROR_MESSAGE = "This user not exist";

    public UsernameNotFoundException(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}
