package com.example.w0143446.quizbuilder;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 BusinessEngine Class handles data processing
 and analysis
 Created by w0143446 on 10/19/2016.
 */
public class BusinessEngine {
    private static BusinessEngine instance = null;
    private int score = 0; //your current cumulative score(0-100%)
    protected int card = 1; //the current question/answer from list (1-10).
    private HashMap<String, String> hmData; //the question/answer data in a hashmap (key, val)
    private ArrayList<String> alQuestions; //arrayList of questions
    private ArrayList<String> alAnswers; //arrayList of answers

    protected BusinessEngine(HashMap<String, String> hmData){
        //false constructor, called internally only
        this.hmData = hmData;
        this.setQuestionArray(hmData);
        this.setAnswerArray(hmData);
    }
    public synchronized static BusinessEngine getInstance(HashMap<String, String> hmData) {
        //defacto construction method (lazy loading)
        if (instance == null) {
            instance = new BusinessEngine(hmData);

            //http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
        }
        return instance;
    }

    //Getters and setters to create arraylists of q&a
    private void setQuestionArray(HashMap<String, String> hmData) {
        /*
        Sets up the ArrayList of QUESTIONS property alQuestions
        based on the hashmap created by the loadCSVData method.
        This is a private method that is called automatically by
        the loadCSVData function.

        If the app was expanded to allow
        users to do CRUD functions then this architecture may change.
        */

        ArrayList alData = new ArrayList();
        Iterator it = hmData.entrySet().iterator();

        for (Map.Entry<String, String> entry : hmData.entrySet())
        {
            alData.add(entry.getKey()); //questions are key
        }

        this.alQuestions = alData;
    }
    private void setAnswerArray(HashMap<String, String> hmData) {
         /*
        Sets up the ArrayList of ANSWERS property alAnswers
        based on the hashmap created by the loadCSVData method.
        This is a private method that is called automatically by
        the loadCSVData function.

        If the app was expanded to allow
        users to do CRUD functions then this architecture may change.
        */

        ArrayList alData = new ArrayList();
        Iterator it = hmData.entrySet().iterator();

        for (Map.Entry<String, String> entry : hmData.entrySet())
        {
            //System.out.println(entry.getKey());
            alData.add(entry.getValue()); //answers are val
        }

        this.alAnswers = alData;
    }
    public ArrayList<String> getQuestionArray(){
        return this.alQuestions;
    }
    public ArrayList<String> getAnswerArray(){
        return this.alAnswers;
    }

    protected String getCountText(Context appContext) {
        /*
            Returns the card count and
            formats the resulting count
            as text ([int] out of [int])
        */
        return this.card + " " + appContext.getString(R.string.tvQCount_text) + " " + alQuestions.size();
    }
    public ArrayList<String> getFourAnswers() {
        /*
        This is the method to return an ArrayList of
        four answers: three correct, one wrong.
        They are shuffled and returned in a
        random order.
        Result is a string array length 4
         */

        String rightAnswer = getRightAnswer();
        String[] wrongAnswers = new String[3];
        wrongAnswers = getThreeWrongAnswers(rightAnswer);
        ArrayList<String> fourAnswersList = new ArrayList<>();
        fourAnswersList.add(rightAnswer);
        for (int x=0;x<3;x++) {
            fourAnswersList.add(wrongAnswers[x]);
            //System.out.println(fourAnswersList.get(x));
        }
        Collections.shuffle(fourAnswersList);
        return fourAnswersList;
    }
    protected String getRightAnswer() {
        return this.alAnswers.get(card);
    }
    private String[] getThreeWrongAnswers(String correctAnswer) {
        /*
        Returns an array of 3 random wrong answers
        Process does involve some while loops to keep
        trying until a unique value found but I figure that
        this is easier [better?] than creating another special
        list
         */
        String[] threeWrongAnswersArray =  new String[3];
        Random rand = new Random();

        for (int i=0;i<3;i++){
            String wrongAnswer = this.alAnswers.get(rand.nextInt(9));
            while (wrongAnswer.equals(correctAnswer) ||
                    Arrays.asList(threeWrongAnswersArray).contains(wrongAnswer)) {
                //repeat if the answer is same as correct
                wrongAnswer = this.alAnswers.get(rand.nextInt(9));
            }

            threeWrongAnswersArray[i] = wrongAnswer;
        }
        return threeWrongAnswersArray;
    }
    public String getMotivationMsg(String name) {
        double total = (float) score / alQuestions.size();
        Integer result = (int)Math.round(total * 100);
        if (result < 50) {
            return result + "%, Better luck next time " + name;
        }
        else if (result < 70) {
            return result + "%, Not bad. Keep working on it " + name;
        }
        else if (result < 90) {
            return result + "%, Great Work.";
        }
        else if (result >= 90) {
            return result + "%, Excellent Job. You're a quizmaster " + name;
        }
        else {
            return "Something went wrong " + name;
        }
    }

    protected String updateResult(String checkedAnswer, Context appContext) {
        if (getRightAnswer().equals(checkedAnswer)) {
            score += 1;
            return appContext.getString(R.string.txtFeedbackGood);
        }
        else {
            return appContext.getString(R.string.txtFeedbackBad);
        }
    }
    //Getters, setters
    public int getScore() {
        return this.score;
    }
    public void setScore(int newScore) {
        this.score = newScore;
    }
    public String[] getCard(int mynum) {
        /*
        function that returns a question/answer pair.
        the question is taken from the next index of the
        array, while the answer is taken from the hashmap.
         */
        System.out.println(this.alQuestions.get(mynum));
        System.out.println(this.hmData.get(this.alQuestions.get(mynum)));
        String[] card = {this.alQuestions.get(mynum), this.hmData.get(this.alQuestions.get(mynum))};
        return card;
    }
    public int nextCard() {
        this.card += 1;
        if (this.card >= alQuestions.size()) {
            this.card = -1; //[todo: make this trigger a done screen]
        }
        return this.card;
    }
    public void resetDeck(){
        score = 0;
        card = 1;
    }
    public HashMap<String, String> getHashMap(){
        return this.hmData;
    }
}
