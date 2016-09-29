package com.example.csaper6.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //This is the Presenter, linking POJO and xml
    private Button trueButton, falseButton, nextButton, previousButton, cheatButton;
    private ImageButton backButton, forwardButton;
    private TextView questionTextView, scoreTextView;
    private boolean[] ifCheated = {false, false, false, false, false};
    private boolean[] ifAnswered = {false, false, false, false, false};
    private Question[] questionBank = {
            new Question(R.string.question_bear,true),
            new Question(R.string.question_bird,true),
            new Question(R.string.question_cats,true),
            new Question(R.string.question_food,true),
            new Question(R.string.question_rain,false)};

    private boolean cheatingEnabled;
    private int currentIndex;
    private int currentScore;

    //for the extras sent in the intent
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CURRENT_QUESTION = "current question";
    public static final String EXTRA_ANSWER = "current answer";
    //for the result
    public static final int REQUEST_CHEATED = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire buttons and textview
        trueButton = (Button) findViewById(R.id.button_true);
        falseButton = (Button) findViewById(R.id.button_false);
        nextButton = (Button) findViewById(R.id.button_next);
        previousButton = (Button) findViewById(R.id.button_previous);
        backButton = (ImageButton) findViewById(R.id.imageButton_back);
        forwardButton = (ImageButton) findViewById(R.id.imageButton_forward);
        questionTextView = (TextView) findViewById(R.id.textview_question);
        cheatButton = (Button) findViewById(R.id.button_cheat);
        scoreTextView = (TextView) findViewById(R.id.textview_score);


        currentIndex = 0;
        currentScore = 0;

        //create a new question object from the String resources
        //make a question object and pass string resource and ans. int he constructor
        questionTextView.setText(questionBank[0].getQuestionId());//overloaded

        //call the string with R.string. etc.
        //set the textview's text to the question's text
        //make a View.OnClickListener for each button using anonymous inner class way of doing things.

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifAnswered[currentIndex] = true;
                checkAnswer(true);
                updateScore(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifAnswered[currentIndex] = true;
                checkAnswer(false);
                updateScore(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateQuestion();
            }
        });


        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                previousQuestion();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                previousQuestion();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateQuestion();
            }
        });




        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use Intents to go from one activity to another
                Intent openCheatActivity = new Intent(MainActivity.this, CheatActivity.class);
                //load up the intent with extra information to take
                //to the new activity
                //if follows the key: value pair format where
                //you have a key to identify the extra & a value that is stored with it
                //openCheatActivity.putExtra(EXTRA_MESSAGE,questionBank[currentIndex].getQuestionId());
                openCheatActivity.putExtra(EXTRA_CURRENT_QUESTION,
                        questionBank[currentIndex].getQuestionId());
                openCheatActivity.putExtra(EXTRA_ANSWER,
                        questionBank[currentIndex].isAnswerTrue());
                //startActivity(openCheatActivity);
                //make a constant for the request
                // to identify what result we're trying to get
                startActivityForResult(openCheatActivity, REQUEST_CHEATED);
            }
        });

        cheatingEnabled = false;
        cheatButton.setVisibility(View.GONE);


    }
        //Inside each button, call the question's checkAnswer method and make an appropriate toast


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: do something with the data returned
        if(resultCode == RESULT_OK && requestCode == REQUEST_CHEATED){
            //TODO: extract the information from the intent data
                    ifCheated[currentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_CHEATED,false);
            //do something with it. maybe set an instance variable if they cheated
            //true if they cheated

        }
    }

        private void updateQuestion() {

            if (currentIndex != questionBank.length - 1)
            {
                currentIndex = (currentIndex + 1) % questionBank.length;
                questionTextView.setText(questionBank[currentIndex].getQuestionId());
            }
            else{
                Toast.makeText(MainActivity.this, "No more question for you!", Toast.LENGTH_SHORT).show();
            }

        }

        private void previousQuestion() {
            if(currentIndex != 0)
            {
                currentIndex = (currentIndex - 1) % questionBank.length;
                questionTextView.setText(questionBank[currentIndex].getQuestionId());
            }
        }

        private void checkAnswer(boolean userresponse)
        {
            if (questionBank[currentIndex].checkAnswer(userresponse))
            {
                Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "That's wrong", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_options, menu);
            return true;
        }




        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.menu_cheat:
                    toggleCheating();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    private void toggleCheating() {
        if(cheatingEnabled){
            //disable cheating
            cheatButton.setVisibility(View.GONE);
        }
        else{
            cheatButton.setVisibility(View.VISIBLE);
        }

    }


    private void updateScore(boolean userAns){
        if (ifCheated[currentIndex] == false && questionBank[currentIndex].checkAnswer(userAns))
        {
            currentScore++;
            scoreTextView.setText("Score: " + currentScore);
            Toast.makeText(MainActivity.this, "You get one point!", Toast.LENGTH_SHORT).show();
            ifAnswered[currentIndex] = true;
        }
        else if(ifCheated[currentIndex] == true && questionBank[currentIndex].checkAnswer(userAns))
        {
            Toast.makeText(MainActivity.this, "No point for you! You cheater!", Toast.LENGTH_SHORT).show();
        }
    }


}


