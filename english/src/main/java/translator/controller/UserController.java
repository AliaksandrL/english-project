package translator.controller;

import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DbEntities.DbUser;
import translator.annotation.RequestMapping;
import translator.web.Controller;
import translator.web.HttpMethod;
import translator.web.ModelAndView;
import translator.web.View;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class UserController implements Controller{
	private UserRetriever userRetriever;

	public UserController(UserRetriever userRetriever) {
		this.userRetriever = userRetriever;
	}

	@RequestMapping(url = "/users/find", method = HttpMethod.GET)
	public ModelAndView findUserByName(String username) {
		ModelAndView view = new ModelAndView(View.USER);
		Iterable<DbUser> users;
		DbUser user = null;
		users = userRetriever.getByField(username);
		if(users.iterator().hasNext())
			user = users.iterator().next();
		view.addParameter("user", user);
		return view;
	}

	@RequestMapping(url = "/users/registernew", method = HttpMethod.POST)
	public ModelAndView registerNewUser(DbUser newUser) {
		ModelAndView view = new ModelAndView(View.MAIN);
		if(userRetriever.save(newUser))
			view.addParameter("user", newUser);
		else
			view.addParameter("user", null);
		return view;
	}

	@RequestMapping(url = "/users/update", method = HttpMethod.UPDATE)
	public ModelAndView updateUser(DbUser updatedUser) {
		ModelAndView view = new ModelAndView(View.MAIN);
		if(userRetriever.update(updatedUser))
			view.addParameter("user", updatedUser);
		else
			view.addParameter("user", null);
		return view;
	}

	@RequestMapping(url = "/users/all", method = HttpMethod.GET)
	public ModelAndView getAllUsers() {
		ModelAndView view = new ModelAndView(View.MAIN);
		Iterable<DbUser> allUsers = userRetriever.getAll();
		if(allUsers!=null)
			view.addParameter("allUsers", allUsers);
		else
			view.addParameter("allUsers", null);
		return view;
	}
}
