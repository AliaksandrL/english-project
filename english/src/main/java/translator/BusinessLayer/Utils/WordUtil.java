package translator.BusinessLayer.Utils;

import translator.BusinessLayer.Word;
import translator.DataLayer.DataRetrievers.UsersWordsRetriever;
import translator.DataLayer.DataRetrievers.WordRetriever;
import translator.DataLayer.DbEntities.DbUserWord;
import translator.DataLayer.DbEntities.DbWord;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Администратор on 29.06.2017.
 */
public class WordUtil {
    private ArrayList<Word> words = new ArrayList<Word>();

    private Scanner scanner = new Scanner(System.in);

    private static Random randomGenerator = new Random();

    private Word getRandomWord()
    {
        int index = randomGenerator.nextInt(words.size());
        return words.get(index);
    }

    public WordUtil(int userId, int topicId)
    {
        WordRetriever wordRetriever = new WordRetriever();
        UsersWordsRetriever usersWordsRetriever = new UsersWordsRetriever();
        ArrayList<DbWord> dbWords = (ArrayList<DbWord>)wordRetriever.getByField(topicId);
        ArrayList<DbUserWord> dbUserWords = (ArrayList<DbUserWord>)usersWordsRetriever.getByField(userId);
        for (int i=0; i<words.size(); i++)
        {
            if(dbUserWords.get(i).countCorrect<6)
            {
                Word curWord=new Word();
                curWord.englishWord=dbWords.get(i).englishWord;
                curWord.russianWord=dbWords.get(i).russianWord;
                curWord.countCurrent=dbUserWords.get(i).countCorrect;
                words.add(curWord);
            }
        }
    }

    public void CheckWord()
    {
        Word word = this.getRandomWord();
        System.out.println(word.englishWord);
        String rusEquivalent = (scanner.next());
        if(rusEquivalent == word.russianWord)
        {
            word.countCurrent++;
        }
    }
}
