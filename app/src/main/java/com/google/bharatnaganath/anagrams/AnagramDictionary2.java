package com.google.bharatnaganath.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Vinut S Naganath on 26.10.17.
 */

public class AnagramDictionary2 {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordlist;
    private HashSet wordSet;
    private HashMap lettersToWord=new HashMap();
    public AnagramDictionary2(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        ArrayList<String> temp=new ArrayList<>();
        wordlist = new ArrayList();
        wordSet = new HashSet();
        lettersToWord = new HashMap();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
            wordSet.add(word);
            String Key = sortLetters(word);
            if (!lettersToWord.containsKey(Key)) {
                ArrayList<String> ar = new ArrayList();
                ar.add(word);
                lettersToWord.put(Key, ar);
            } else {
                temp = (ArrayList) lettersToWord.get(Key);
                temp.add(word);
                lettersToWord.put(Key, temp);

            }

        }


    }
    private String sortLetters(String string)
    {
        char[] temp=string.toCharArray();
        Arrays.sort(temp);
        String key= new String(temp);
        return(key);
    }

    public boolean isGoodWord(String word, String base) {
        boolean toReturn=false;
        if(!word.contains(base)&&wordSet.contains(word))
        {
            toReturn=true;
        }

        return toReturn;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        for (String s : wordlist) {
            {


                if (s.length() == targetWord.length()) {
                    if (sortLetters(s).equals(sortLetters(targetWord))) {
                        result.add(s);
                        Log.i("TAG", s);
                    }

                }
            }
        }

        Log.i("TAG", "DONE");


        return result;

    }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char i='a';i<='z';i++)
        {
            String newWord=word+i;
            String newKey=sortLetters(newWord);
            if(lettersToWord.containsKey(newKey)&&!word.contains(newWord))
            {
                ArrayList<String> restemp;
                restemp = (ArrayList) lettersToWord.get(newKey);
                for(int j=0;j<restemp.size();j++)
                    result.add(restemp.get(j));

            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int rnum;
        String rword;
        while(true) {

            rnum=random.nextInt(62995)+1;
            rword = wordlist.get(rnum);
            ArrayList<String> tempal = (ArrayList) lettersToWord.get(sortLetters(rword));
            if(tempal.size()>=MIN_NUM_ANAGRAMS&&rword.length()<=MAX_WORD_LENGTH)
                break;
        }

        return rword;
    }
}
