

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordlist;
    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordlist = new ArrayList();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);


    }
    int n=wordlist.size();
    Log.i("TAG", ""+n);
    }
    private String sortLetters(String string)
    {
        char[] temp=string.toCharArray();
        Arrays.sort(temp);
        String key= new String(temp);
        return(key);
    }

    public boolean isGoodWord(String word, String base) {
        return true;
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


    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
