package translator.util;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import translator.BusinessLayer.Utils.WordUtil;
import translator.DataLayer.DataRetrievers.TopicRetriever;
import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DataRetrievers.UsersWordsRetriever;
import translator.DataLayer.DataRetrievers.WordRetriever;
import translator.DataLayer.DbEntities.DbTopic;
import translator.DataLayer.DbEntities.DbUser;
import translator.DataLayer.DbEntities.DbUserWord;
import translator.DataLayer.DbEntities.DbWord;

/**
 * Created by Lenovo on 10.06.2017.
 */
public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String username, password;
		BasicConfigurator.configure();
		UserRetriever userRetriever = new UserRetriever();
		TopicRetriever topicRetriever = new TopicRetriever();
		UsersWordsRetriever usersWordsRetriever = new UsersWordsRetriever();
		WordRetriever wordRetriever = new WordRetriever();
//		System.out.println(userRetriever.find(1));
//		System.out.println("=====");
//		Iterable<DbUser> users = userRetriever.getAll();
//		for (DbUser user : users) {
//			System.out.println(user);
//		}

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

		Iterable<DbTopic> topics = topicRetriever.getAll();
		for (DbTopic topic : topics) {
			System.out.println(topic);
		}

		Iterable<DbUserWord> userwords = usersWordsRetriever.getAll();
		for (DbUserWord userword : userwords) {
			System.out.println(userword);
		}

		Iterable<DbWord> words = wordRetriever.getAll();
		for (DbWord word : words) {
			System.out.println(word);
		}

		WordUtil wordUtil = new WordUtil(1,1);
		wordUtil.CheckWord();
	}
}
