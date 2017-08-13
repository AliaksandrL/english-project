package translator.web;

/**
 * Created by Lenovo on 18.06.2017.
 */
public enum View {
	LOGIN("login"),
	MAIN("main"),
	ERROR("error"),
	USER("user"),
	USERWORD("UserWord"),
	TOPIC("Topic"),
	WORD("Word");

	private String name;

	View(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
