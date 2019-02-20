

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 6;
    private Random random = new Random();
    private ArrayList<String> wordlist;
    private HashSet wordSet;
    private HashMap lettersToWord;
    private HashMap sizeToWords;
    private static int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        ArrayList<String> temp = new ArrayList<>();
        wordlist = new ArrayList();
        wordSet = new HashSet();
        lettersToWord = new HashMap();
        sizeToWords = new HashMap();

        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
            wordSet.add(word);
            //MileStone 3 Additions STARTS
            if (!sizeToWords.containsKey(word.length())) {
                ArrayList<String> arsize = new ArrayList();
                arsize.add(word);
                sizeToWords.put(word.length(), arsize);
            } else {
                temp = (ArrayList) sizeToWords.get(word.length());
                temp.add(word);
                sizeToWords.put(word.length(), temp);
            }
            //MileStone 3 Additions ENDS
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

    private String sortLetters(String string) {
        char[] temp = string.toCharArray();
        Arrays.sort(temp);
        String key = new String(temp);
        return (key);
    }

    public boolean isGoodWord(String word, String base) {

        if (!word.contains(base) && wordSet.contains(word)) {
            return true;
        }

        return false;
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
        for (char i = 'a'; i <= 'z'; i++) {
            String newWord = word + i;
            String newKey = sortLetters(newWord);
            if (lettersToWord.containsKey(newKey) && !word.contains(newWord)) {
                ArrayList<String> restemp;
                restemp = (ArrayList) lettersToWord.get(newKey);
                for (int j = 0; j < restemp.size(); j++)
                    result.add(restemp.get(j));

            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int rnum;
        String rword;
        while (true) {


            ArrayList<String> sizeAl = (ArrayList) sizeToWords.get(wordLength);

            rnum = random.nextInt(sizeAl.size()) + 1;
            rword = sizeAl.get(rnum);
            ArrayList<String> tempal = (ArrayList) lettersToWord.get(sortLetters(rword));
            Log.i("TAG",""+tempal.size());
            if (tempal.size() >= MIN_NUM_ANAGRAMS) {
                if (wordLength < MAX_WORD_LENGTH)
                    wordLength++;

                break;
            }



        }
        return rword;
    }

}
