package translator;

/**
 * Created by Lenovo on 17.06.2017.
 */
public class IllegalRequestException extends RuntimeException {
	public IllegalRequestException(String message) {
		super(message);
	}
}
