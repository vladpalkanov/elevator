package exceptions;

public class CannotTakePlaceException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CannotTakePlaceException(){}
	
	public CannotTakePlaceException(String message){
		super(message);
	}
	
	public String getMessage(){
		return super.getMessage();
	}
}
