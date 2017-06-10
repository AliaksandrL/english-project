package translator.service;

import translator.dao.UserDAO;
import translator.entity.User;

import static translator.util.Validator.*;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class UserService {
	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User findUserById(String id) {
		return userDAO.find(validateLong(id));
	}

	public String saveUser(String name, String age) {
		User user = new User();
		user.setName(name);
		user.setAge(validateInt(age));

		boolean result = userDAO.save(user);
		return result ? "User created" : "User not created";
	}
}
