package translator.util;

import translator.util.Word;
import translator.DataLayer.DataRetrievers.UsersWordsRetriever;
import translator.DataLayer.DataRetrievers.WordRetriever;
import translator.DataLayer.DbEntities.DbUserWord;
import translator.DataLayer.DbEntities.DbWord;
import translator.util.StaticValues;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Администратор on 29.06.2017.
 */
public class WordUtil {
    private static int needsToBeTranslated = 1;
    private static Random randomGenerator = new Random();
    private ArrayList<Word> unlearnedWords = new ArrayList<Word>();
    private ArrayList<Word> words = new ArrayList<Word>();
    private  UsersWordsRetriever usersWordsRetriever  = new UsersWordsRetriever();
    private Scanner scanner = new Scanner(System.in);
    public String topicName;

    private Word getRandomWord()
    {
        int index;
        index = randomGenerator.nextInt(unlearnedWords.size());
        return unlearnedWords.get(index);
    }

    public WordUtil(int userId, int topicId)
    {
        WordRetriever wordRetriever = new WordRetriever();
        ArrayList<DbWord> dbWords = (ArrayList<DbWord>)wordRetriever.getByField(topicId);
        ArrayList<DbUserWord> dbUserWords = (ArrayList<DbUserWord>)usersWordsRetriever.getByField(userId);
        for (int i=0; i<dbWords.size(); i++)
        {
            Word curWord=new Word();
            curWord.wordId=dbWords.get(i).wordId;
            curWord.englishWord=dbWords.get(i).englishWord;
            curWord.russianWord=dbWords.get(i).russianWord;
            curWord.countCurrent=0;
            for (DbUserWord curDbUserWord:dbUserWords) {
                if(curDbUserWord.wordId == dbWords.get(i).wordId)
                    curWord.countCurrent = curDbUserWord.countCorrect;
            }
            //try{curWord.countCurrent=dbUserWords.get(i).countCorrect;}
            //catch (IndexOutOfBoundsException e)
            //{
            //    curWord.countCurrent=0;
            //}
            if(curWord.countCurrent<needsToBeTranslated) {
                this.words.add(curWord);
                this.unlearnedWords.add(curWord);
            }
        }
    }

    public void checkWord()
    {
        Word word = this.getRandomWord();
        System.out.println(word.englishWord);
        String rusEquivalent = (scanner.next());
        if(rusEquivalent.equals(word.russianWord))
        {
            System.out.println("Верно!");
            word.countCurrent++;
            for(int i=0; i<this.unlearnedWords.size();i++){
                if(unlearnedWords.get(i).englishWord.equals(word.englishWord)
                        && word.countCurrent>=needsToBeTranslated)
                    unlearnedWords.remove(i);
            }
        }
        else{
            System.out.println(String.format("Неверно! %s - %s", word.englishWord, word.russianWord));
        }
    }

    public boolean saveChanges() {
        boolean saveResult = true;
        for (int i=0; i<words.size(); i++){
            DbUserWord dbUserWord = new DbUserWord();
            dbUserWord.countCorrect = words.get(i).countCurrent;
            dbUserWord.wordId = words.get(i).wordId;
            dbUserWord.userId = StaticValues.getAuthenticatedUserId();
            ArrayList<DbUserWord> dbUserWords = (ArrayList<DbUserWord>)usersWordsRetriever.getByField(dbUserWord.userId);
            boolean updateFlag = false;
            for (DbUserWord dbUserWordDB:dbUserWords) {
                if(dbUserWordDB.userId == dbUserWord.userId && dbUserWordDB.wordId==dbUserWord.wordId) {
                    saveResult &= this.usersWordsRetriever.update(dbUserWord);
                    updateFlag=true;
                    break;
                }
            }
            if(!updateFlag)
                saveResult &= this.usersWordsRetriever.save(dbUserWord);
        }
        return  saveResult;
    }

    public boolean isAnyUnlearnedWord()
    {
        return !this.unlearnedWords.isEmpty();
    }
}
