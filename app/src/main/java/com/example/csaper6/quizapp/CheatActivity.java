package com.example.csaper6.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean hasCheated;
    public static final String EXTRA_CHEATED = "they cheated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat3);
        TextView questiontext = (TextView) findViewById(R.id.cheat_question_text);
        final TextView questionAnswerText = (TextView) findViewById(R.id.cheat_answer);
        Button cheatButton = (Button) findViewById(R.id.button_are_you_sure);
        hasCheated = false;

        //gathering up the Intent that brought us here
        Intent i = getIntent();

        //extracting the extra that was put
        //String message = i.getStringExtra(MainActivity.EXTRA_MESSAGE);
        int questionId = i.getIntExtra(MainActivity.EXTRA_CURRENT_QUESTION,-500);
        final boolean answer = i.getBooleanExtra(MainActivity.EXTRA_ANSWER,false);

        //using that data that was passed to the ???
        questiontext.setText(questionId);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionAnswerText.setText(" " + questionAnswerText.getText() + "" + answer);
                hasCheated = true;
                //just using this to box up the info
                //to send back, so don't need any
                // //addresses in the parameters
                Intent i = new Intent();
                i.putExtra(EXTRA_CHEATED, hasCheated);
                setResult(RESULT_OK, i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
