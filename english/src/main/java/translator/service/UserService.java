package translator.service;

import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DbEntities.DbUser;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class UserService {
	private UserRetriever userRetriever;

	public UserService(UserRetriever userRetriever) {
		this.userRetriever = userRetriever;
	}

	public DbUser findUserById(int id) {
		return userRetriever.find(id);
	}

	public Iterable<DbUser> findUserById(String userName) {
		return userRetriever.getByField(userName);
	}


	public String saveUser(String name, String password) {
		DbUser user = new DbUser();
		user.userName=name;
		user.password=password;

		boolean result = userRetriever.save(user);
		return result ? "User created" : "User not created";
	}
}
