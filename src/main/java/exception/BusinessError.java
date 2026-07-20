package exception;

public class BusinessError extends RuntimeException {
    private final ErrorType error;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public BusinessError(ErrorType error){
        this.error = error;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public String get_error_message(){
        return ErrorType.converti_error_to_message(error);
    }

}
