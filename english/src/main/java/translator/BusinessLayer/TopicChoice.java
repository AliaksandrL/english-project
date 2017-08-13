package translator.web;

import translator.util.WordUtil;
import translator.DataLayer.DataRetrievers.TopicRetriever;
import translator.DataLayer.DataRetrievers.WordRetriever;
import translator.DataLayer.DbEntities.DbTopic;
import translator.DataLayer.DbEntities.DbWord;
import translator.util.StaticValues;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Администратор on 08.07.2017.
 */
public class TopicChoice {
    public void ViewTopicChoice() {
        WordRetriever wordRetriever = new WordRetriever();
        TopicRetriever topicRetriever = new TopicRetriever();
        Iterable<DbTopic> topics = topicRetriever.getAll();
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
            topic = topicRetriever.find((long) n);

            if (topic == null) {
                System.out.println("!!!Попробуй еще раз!!! =)");
                System.out.println("");
                continue;
            }

            System.out.println("    ==================================================");
            System.out.println("    Список слов для изучения:");
            for (DbWord dbWord : wordRetriever.getByField(n)) {
                System.out.println(String.format("%s - %s", dbWord.englishWord, dbWord.russianWord));
            }
            System.out.println("    ==================================================");
            scanner.next();

            WordUtil wordUtil = new WordUtil(StaticValues.getAuthenticatedUserId(), n);
            wordUtil.topicName = topicRetriever.find(n).topicName;
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