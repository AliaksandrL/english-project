package translator.controller;

import translator.DataLayer.DbEntities.DbUser;
import translator.annotation.RequestMapping;
import translator.entity.User;
import translator.service.UserService;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(url = "users/find")
	public DbUser findUserById(int id) {
		return userService.findUserById(id);
	}

	@RequestMapping(url = "users/find")
	public DbUser findUserByName(int id) {
		return userService.findUserById(id);
	}

	@RequestMapping(url = "users/create")
	public String createUser(String name, String age) {
		return userService.saveUser(name, age);
	}
}
