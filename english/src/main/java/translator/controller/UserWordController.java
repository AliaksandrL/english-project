package translator.controller;

import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DataRetrievers.UsersWordsRetriever;
import translator.DataLayer.DbEntities.DbUser;
import translator.DataLayer.DbEntities.DbUserWord;
import translator.annotation.RequestMapping;
import translator.web.Controller;
import translator.web.HttpMethod;
import translator.web.ModelAndView;
import translator.web.View;

/**
 * Created by Администратор on 12.08.2017.
 */
public class DbUserWordController implements Controller {
    private UsersWordsRetriever usersWordsRetriever;

    public DbUserWordController(UsersWordsRetriever usersWordsRetriever) {
        this.usersWordsRetriever = usersWordsRetriever;
    }

    @RequestMapping(url = "/userswords/find", method = HttpMethod.GET)
    public ModelAndView findUserByName(int userId) {
        ModelAndView view = new ModelAndView(View.USERWORD);
        Iterable<DbUserWord> dbUserWords;
        dbUserWords = usersWordsRetriever.getByField(userId);
        view.addParameter(View.USERWORD.toString(), dbUserWords);
        return view;
    }

    @RequestMapping(url = "/userswords/update", method = HttpMethod.UPDATE)
    public ModelAndView updateUser(DbUserWord dbUserWord) {
        ModelAndView view = new ModelAndView(View.MAIN);
        if(usersWordsRetriever.update(dbUserWord))
            view.addParameter(View.USERWORD.toString(), dbUserWord);
        else
            view.addParameter(View.USERWORD.toString(), null);
        return view;
    }

    @RequestMapping(url = "/userswords/registernew", method = HttpMethod.POST)
    public ModelAndView registerNewUser(DbUserWord dbUserWord) {
        ModelAndView view = new ModelAndView(View.MAIN);
        if(usersWordsRetriever.save(dbUserWord))
            view.addParameter(View.USERWORD.toString(), dbUserWord);
        else
            view.addParameter(View.USERWORD.toString(), null);
        return view;
    }
}