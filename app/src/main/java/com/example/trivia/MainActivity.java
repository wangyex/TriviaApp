package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 1;
    private  int scoreCounter = 0;
    private Score score;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        score = new Score();

        questionList = new Repository().getQuestions(questionArrayList ->{
                    binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                    updateCounter(questionArrayList);

                }



                );

        binding.buttonNext.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();
            updateCounter((ArrayList<Question>) questionList);
                });



        binding.buttonRandom.setOnClickListener(view -> {
            //currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            randomQuestion();
            updateQuestion();
            updateCounter((ArrayList<Question>) questionList);
        });

        binding.buttonRst.setOnClickListener(view -> {
            //currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            randomQuestion();
            updateQuestion();
            scoreCounter = 0;
            score.setScore(scoreCounter);
            binding.hiScoreText.setText(String.valueOf(score.getScore()));

            updateCounter((ArrayList<Question>) questionList);
        });

        binding.buttonFalse.setOnClickListener(view -> {
            checkAnswer(true);


        });

        binding.buttonTrue.setOnClickListener(view -> {
            checkAnswer(false);

        });
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnserTrue();
        // visual cue after chosen the answer
        int snackMessageId = 0;
        if (userChoseCorrect == answer) {
            snackMessageId = R.string.correct_answer;
            addPoints();
            updateQuestion();
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateCounter((ArrayList<Question>) questionList);
        }else{
            snackMessageId = R.string.incorrect;
            updateQuestion();
            deductPoints();
            shakeAnimation();
            shakeItBaby();
        }

        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();

    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewQuestionCounter.setText("Question: " + currentQuestionIndex +"/" + questionArrayList.size());
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
    }

    private void randomQuestion(){
        currentQuestionIndex = new Random().nextInt(questionList.size());

    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

    }
    public void shakeItBaby() {
        int DURATION = 250; // you can change this according to your need
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(DURATION);
        }
    }

    private void addPoints(){
        scoreCounter += 100;
        score.setScore(scoreCounter);
        binding.hiScoreText.setText(String.valueOf(score.getScore()));

    }
    private void deductPoints(){

        if (scoreCounter > 0){
            scoreCounter -= 100;
            score.setScore(scoreCounter);
            binding.hiScoreText.setText(String.valueOf(score.getScore()));
        }else {
            scoreCounter = 0;
            score.setScore(scoreCounter);

        }

    }
}