package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Exceptions;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(){}

    public InvalidUsernameException(String message){
        super(message);
    }
}
