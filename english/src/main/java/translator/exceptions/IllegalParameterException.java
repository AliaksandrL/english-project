package translator.exceptions;

/**
 * Created by Lenovo on 17.06.2017.
 */
public class IllegalParameterException extends IllegalRequestException {
	private String illegalParameter;

	public IllegalParameterException(String message, String illegalParameter) {
		super(message);
		this.illegalParameter = illegalParameter;
	}
}
