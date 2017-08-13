package translator.BusinessLayer;

import translator.util.WordUtil;
import translator.DataLayer.DbEntities.DbTopic;
import translator.DataLayer.DbEntities.DbWord;
import translator.util.StaticValues;
import translator.web.Dispatcher;
import translator.web.HttpMethod;
import translator.web.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Администратор on 08.07.2017.
 */
public class TopicChoice {
    private Dispatcher dispatcher = Dispatcher.getInstance();

    public void ViewTopicChoice() {
        Iterable<DbTopic> topics = (ArrayList<DbTopic>) dispatcher.dispatch("/topics/all", HttpMethod.GET, null).getParameter(View.TOPIC.toString());
        for(;;) {
            Iterator<DbTopic> iterator = topics.iterator();
            DbTopic topic;
            Scanner scanner = new Scanner(System.in);
            int n;

            System.out.println("    *** Выберите действие которое хотите совершить: ***");
            System.out.println("    ==================================================");
            while (iterator.hasNext()) {
                topic = iterator.next();
                System.out.println(String.format("        %d. %s", topic.topicId, topic.topicName));
                System.out.println(" ");
            }
            System.out.println("    ==================================================");
            System.out.println("0 - ВЫХОД");
            n = scanner.nextInt();
            if(n==0)
                break;
            topic = (DbTopic) dispatcher.dispatchGeneric("/topics/find", HttpMethod.GET, (long)n).getParameter(View.TOPIC.toString());

            if (topic == null) {
                System.out.println("!!!Попробуй еще раз!!! =)");
                System.out.println("");
                continue;
            }

            System.out.println("    ==================================================");
            System.out.println("    Список слов для изучения:");
            ArrayList<DbWord> words = (ArrayList<DbWord>) dispatcher.dispatchGeneric("/words/find", HttpMethod.GET, n).getParameter(View.WORD.toString());
            for (DbWord dbWord : words) {
                System.out.println(String.format("%s - %s", dbWord.englishWord, dbWord.russianWord));
            }
            System.out.println("    ==================================================");
            scanner.next();

            WordUtil wordUtil = new WordUtil(StaticValues.getAuthenticatedUserId(), n);
            wordUtil.topicName = ((DbTopic) dispatcher.dispatchGeneric("/topics/find", HttpMethod.GET, n).getParameter(View.TOPIC.toString())).topicName;
            boolean learnedCompletely = false;
            if (!wordUtil.isAnyUnlearnedWord())
                learnedCompletely = true;
            for (; wordUtil.isAnyUnlearnedWord(); ) {
                wordUtil.checkWord();
            }
            System.out.println(String.format("Вы выучили все слова в теме %s", wordUtil.topicName));
            if (!learnedCompletely) {
                System.out.println("Хотите сохранить результаты? 1-да, 2-нет.");
                if (scanner.nextInt() == 1)
                    if (!wordUtil.saveChanges())
                        System.out.println("Произошла ошибка при сохранении ваших результатов.");
            }
        }
    }
}