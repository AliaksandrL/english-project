package translator.controller;


import translator.DataLayer.DataRetrievers.UsersWordsRetriever;
import translator.DataLayer.DbEntities.DbUserWord;
import translator.annotation.RequestMapping;
import translator.web.Controller;
import translator.web.HttpMethod;
import translator.web.ModelAndView;
import translator.web.View;

/**
 * Created by Администратор on 12.08.2017.
 */
public class UserWordController implements Controller {
    private UsersWordsRetriever usersWordsRetriever;

    public UserWordController(UsersWordsRetriever usersWordsRetriever) {
        this.usersWordsRetriever = usersWordsRetriever;
    }

    @RequestMapping(url = "/userswords/find", method = HttpMethod.GET)
    public ModelAndView findUserWordsByUserId(int userId) {
        ModelAndView view = new ModelAndView(View.USERWORD);
        Iterable<DbUserWord> dbUserWords;
        dbUserWords = usersWordsRetriever.getByField(userId);
        view.addParameter(View.USERWORD.toString(), dbUserWords);
        return view;
    }

    @RequestMapping(url = "/userswords/update", method = HttpMethod.UPDATE)
    public ModelAndView updateUserWord(DbUserWord dbUserWord) {
        ModelAndView view = new ModelAndView(View.MAIN);
        if(usersWordsRetriever.update(dbUserWord))
            view.addParameter(View.USERWORD.toString(), dbUserWord);
        else
            view.addParameter(View.USERWORD.toString(), null);
        return view;
    }

    @RequestMapping(url = "/userswords/registernew", method = HttpMethod.POST)
    public ModelAndView registerNewUserWord(DbUserWord dbUserWord) {
        ModelAndView view = new ModelAndView(View.MAIN);
        if(usersWordsRetriever.save(dbUserWord))
            view.addParameter(View.USERWORD.toString(), dbUserWord);
        else
            view.addParameter(View.USERWORD.toString(), null);
        return view;
    }
}
