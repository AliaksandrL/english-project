package translator.util;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import translator.BusinessLayer.LoginMenu;
import translator.BusinessLayer.TopicChoice;
import translator.BusinessLayer.Welcome;
import translator.web.Dispatcher;
import translator.web.HttpMethod;
import translator.web.ModelAndView;

/**
 * Created by Lenovo on 10.06.2017.
 */
public class Main {
	public static void main(String[] args) {
		//Scanner scanner = new Scanner(System.in);
//		String username, password;
//		BasicConfigurator.configure();
//		UserRetriever userRetriever = new UserRetriever();
//		TopicRetriever topicRetriever = new TopicRetriever();
//		UsersWordsRetriever usersWordsRetriever = new UsersWordsRetriever();
//		WordRetriever wordRetriever = new WordRetriever();
//		System.out.println(userRetriever.find(1));
//		System.out.println("=====");
//		Iterable<DbUser> users = userRetriever.getAll();
//		for (DbUser user : users) {
//			System.out.println(user);
//		}
//
//		System.out.println("Vvedite Username: ");
//		username = (scanner.next());
//		Iterable<DbUser> dbUsers = userRetriever.getByField(username);
//		if(!dbUsers.iterator().next().userName.equals(username))
//		{
//			System.out.println("Dannogo polzovatelya ne suwestvuet");
//			return;
//		}
//		System.out.println("Vvedite Password: ");
//		password = (scanner.next());
//
//		Iterable<DbTopic> topics = topicRetriever.getAll();
//		for (DbTopic topic : topics) {
//			System.out.println(topic);
//		}
//
//		Iterable<DbUserWord> userwords = usersWordsRetriever.getAll();
//		for (DbUserWord userword : userwords) {
//			System.out.println(userword);
//		}
//
//		Iterable<DbWord> words = wordRetriever.getAll();
//		for (DbWord word : words) {
//			System.out.println(word);
//		}
//		WordUtil wordUtil = new WordUtil(1,1);
//		wordUtil.CheckWord();
//
//		Map<String, String[]> paramMap = new HashMap<String, String[]>();
//		paramMap.put("arg0", new String[]{"sasha"});
//		Dispatcher dispatcher = Dispatcher.getInstance();
//		ModelAndView modelAndView = dispatcher.dispatch("/users/find", HttpMethod.GET, paramMap);
//		System.out.println(modelAndView.getParameters().get("user"));


		Welcome welcome = new Welcome();
		welcome.ViewWelcome();

		LoginMenu loginMenu = new LoginMenu();
		loginMenu.ViewLoginMenu();

		TopicChoice topicChoice = new TopicChoice();
		topicChoice.ViewTopicChoice();

	}
}
