package translator.util;

import org.apache.log4j.Logger;

/**
 * Created by Lenovo on 08.06.2017.
 */
public final class Validator {
	private static Logger logger = Logger.getLogger(Validator.class);

	private Validator() {
	}

	public static Integer validateInt(String string) {
		assert string != null;
		return Integer.parseInt(string);
	}

	public static long validateLong(String string) {
		assert string != null;
		return Long.parseLong(string);
	}
}
