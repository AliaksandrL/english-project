package translator.controller;

import translator.DataLayer.DataRetrievers.WordRetriever;
import translator.DataLayer.DbEntities.DbWord;
import translator.annotation.RequestMapping;
import translator.web.Controller;
import translator.web.HttpMethod;
import translator.web.ModelAndView;
import translator.web.View;

/**
 * Created by Администратор on 13.08.2017.
 */
public class WordController implements Controller {
    private WordRetriever wordRetriever;

    public WordController(WordRetriever wordRetriever) {
        this.wordRetriever = wordRetriever;
    }

    @RequestMapping(url = "/words/find", method = HttpMethod.GET)
    public ModelAndView findWordByTopicId(int topicId) {
        ModelAndView view = new ModelAndView(View.WORD);
        Iterable<DbWord> dbWords;
        dbWords = wordRetriever.getByField(topicId);
        view.addParameter(View.WORD.toString(), dbWords);
        return view;
    }
}
