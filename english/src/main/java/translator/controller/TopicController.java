package translator.controller;

import translator.DataLayer.DataRetrievers.TopicRetriever;
import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DbEntities.DbTopic;
import translator.DataLayer.DbEntities.DbUser;
import translator.annotation.RequestMapping;
import translator.web.Controller;
import translator.web.HttpMethod;
import translator.web.ModelAndView;
import translator.web.View;

/**
 * Created by Администратор on 13.08.2017.
 */
public class TopicController implements Controller {
    private TopicRetriever topicRetriever;

    public TopicController(TopicRetriever topicRetriever) {
        this.topicRetriever = topicRetriever;
    }

    @RequestMapping(url = "/topics/find", method = HttpMethod.GET)
    public ModelAndView findUserByName(long topicId) {
        ModelAndView view = new ModelAndView(View.TOPIC);
        DbTopic topic =  topicRetriever.find(topicId);
        if(topic != null)
            view.addParameter(View.TOPIC.toString(), topic);
        else
            view.addParameter(View.TOPIC.toString(), null);
        return view;
    }

    @RequestMapping(url = "/topics/all", method = HttpMethod.GET)
    public ModelAndView getAllTopics() {
        ModelAndView view = new ModelAndView(View.TOPIC);
        Iterable<DbTopic> allTopics = topicRetriever.getAll();
        if(allTopics!=null)
            view.addParameter(View.TOPIC.toString(), allTopics);
        else
            view.addParameter(View.TOPIC.toString(), null);
        return view;
    }
}
