package translator.util;

import translator.BusinessLayer.LoginMenu;
import translator.BusinessLayer.TopicChoice;
import translator.BusinessLayer.Welcome;


/**
 * Created by Lenovo on 10.06.2017.
 */
public class Main {
	public static void main(String[] args) {

		Welcome welcome = new Welcome();
		welcome.ViewWelcome();

		LoginMenu loginMenu = new LoginMenu();
		loginMenu.ViewLoginMenu();

		TopicChoice topicChoice = new TopicChoice();
		topicChoice.ViewTopicChoice();

	}
}
