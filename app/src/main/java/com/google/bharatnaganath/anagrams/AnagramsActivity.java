

package com.google.bharatnaganath.anagrams;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AnagramsActivity extends AppCompatActivity {

    public static final String START_MESSAGE = "Find as many words as possible that can be formed by adding one letter to <big>%s</big> (but that do not contain the substring %s).";
    private AnagramDictionary1 dictionary1;
    private AnagramDictionary2 dictionary2;
    private AnagramDictionary3 dictionary3;
    private String currentWord;
    private int choice=3;
    private ArrayList<String> anagrams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagrams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent i=getIntent();
        choice= i.getExtras().getInt("Choice");
        Log.i("TAG","Choice: "+choice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Anagrams - BN Workshop ||");
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            if(choice==1)
                dictionary1 = new AnagramDictionary1(new InputStreamReader(inputStream));
            else if(choice==2)
                dictionary2 = new AnagramDictionary2(new InputStreamReader(inputStream));
            else
            {   Log.i("TAG","case 3");

                dictionary3 = new AnagramDictionary3(new InputStreamReader(inputStream));}
            //dictionary = new AnagramDictionary(new InputStreamReader(inputStream));
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        // Set up the EditText box to process the content of the box when the user hits 'enter'
        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO || (
                        actionId == EditorInfo.IME_NULL && event != null && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    processWord(editText);
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void processWord(EditText editText) {
        TextView resultView = (TextView) findViewById(R.id.resultView);
        String word = editText.getText().toString().trim().toLowerCase();
        if (word.length() == 0) {
            return;
        }
        String color = "#cc0029";
        Boolean b;
        if(choice==1)
            b=dictionary1.isGoodWord(word, currentWord);
        else if(choice==2)
            b=dictionary2.isGoodWord(word, currentWord);
        else
            b=dictionary3.isGoodWord(word, currentWord);
        if (b && anagrams.contains(word)) {
            anagrams.remove(word);
            color = "#00aa29";
        } else {
            word = "X " + word;
        }
        resultView.append(Html.fromHtml(String.format("<font color=%s>%s</font><BR>", color, word)));
        editText.setText("");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anagrams, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean defaultAction(View view) {
        TextView gameStatus = (TextView) findViewById(R.id.gameStatusView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView resultView = (TextView) findViewById(R.id.resultView);
        if (currentWord == null) {
            if(choice==1)
            {
                currentWord = dictionary1.pickGoodStarterWord();
                anagrams = dictionary1.getAnagrams(currentWord);
            }
            else if(choice==2) {
                currentWord = dictionary2.pickGoodStarterWord();
                anagrams = dictionary2.getAnagramsWithOneMoreLetter(currentWord);
            }
            else
            {
                currentWord = dictionary3.pickGoodStarterWord();
                anagrams = dictionary3.getAnagramsWithOneMoreLetter(currentWord);
            }
            Log.i("TAG",currentWord);
            gameStatus.setText(Html.fromHtml(String.format(START_MESSAGE, currentWord.toUpperCase(), currentWord)));
            fab.setImageResource(android.R.drawable.ic_menu_help);
            fab.hide();
            resultView.setText("");
            editText.setText("");
            editText.setEnabled(true);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } else {
            editText.setText(currentWord);
            editText.setEnabled(false);
            fab.setImageResource(android.R.drawable.ic_media_play);
            currentWord = null;
            resultView.append(TextUtils.join("\n", anagrams));
            gameStatus.append(" Hit 'Play' to start again");
        }
        return true;
    }
}
