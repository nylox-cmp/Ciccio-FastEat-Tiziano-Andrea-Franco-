package exception;


public class BusinessError extends RuntimeException {
    private final ErrorType error;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     * Instantiates a new Business error.
     *
     * @param error the error
     */
    public BusinessError(ErrorType error){
        this.error = error;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    /**
     * @author Tiziano
     * Get error message string.
     *
     * @return the string
     */
    public String get_error_message(){
        return ErrorType.converti_error_to_message(error);
    }

}
