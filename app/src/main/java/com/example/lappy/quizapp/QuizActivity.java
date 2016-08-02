package com.example.lappy.quizapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	TextView ques_text,ans_text; //textView of question and answer
	RadioGroup radioGp;
	RadioButton op1,op2,op3,op4; // four options for each question
	Button nextBtn,submitBtn,previousBtn; //NEXT, SUBMIT & PREVIOUS buttons
	int quesNo = 0; //keeps count of question number
	int	score = 0; //keeps current score
	JSONArray arr; //array to save questions, answers & options

	public void createArray(){
		JSONParser parser = new JSONParser();
		Resources res = getResources();
		InputStream is = res.openRawResource(R.raw.q_a);
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    Object obj = null;

	    try{
	     obj = parser.parse(br);
	    }catch(Exception e){
	    	e.printStackTrace();
	    } 
	    arr = (JSONArray)obj;
	} //createArray function ends

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		createArray();
		ques_text = (TextView) findViewById(R.id.question_text);
		ans_text = (TextView) findViewById(R.id.answer_text);
		op1 = (RadioButton) findViewById(R.id.radio_Button1);
		op2 = (RadioButton) findViewById(R.id.radio_Button2);
		op3 = (RadioButton) findViewById(R.id.radio_Button3);
		op4 = (RadioButton) findViewById(R.id.radio_Button4);
		nextBtn = (Button) findViewById(R.id.next_button);
		submitBtn = (Button) findViewById(R.id.submit_button);
		previousBtn = (Button) findViewById(R.id.previous_button);
		radioGp = (RadioGroup) findViewById(R.id.radioGroup1);

		//to start the quiz after clicking on START QUIZ buttton
		JSONObject object = (JSONObject) arr.get(0);
		ques_text.setText((String)object.get("ques"));
		final String[] options = ((String) object.get("ops")).split(","); //parsing the options to display
		op1.setText(options[0]);
		op2.setText(options[1]);
		op3.setText(options[2]);
		op4.setText(options[3]);

		//on clicking the SUBMIT button
		OnClickListener l = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (radioGp.getCheckedRadioButtonId() != -1) {
					String optionChosen = ((RadioButton) findViewById(radioGp.getCheckedRadioButtonId())).getText().toString();
					JSONObject ob = (JSONObject) arr.get(quesNo);
					if (optionChosen.equals(ob.get("ans"))) {
						score = score + 1;
					}
					ans_text.setText("Correct answer is: " + ob.get("ans"));
				} else {
					Toast.makeText(getApplicationContext(), "Please choose an option", Toast.LENGTH_LONG).show();
				}
			}
		};
		submitBtn.setOnClickListener(l);

		//on clicking the NEXT button
		OnClickListener l1 = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			ans_text.setText("");
			quesNo=quesNo+1;
			if (quesNo < arr.size()) {
				JSONObject object2 = (JSONObject) arr.get(quesNo);
				ques_text.setText((String) object2.get("ques"));
				if (radioGp.getCheckedRadioButtonId() != -1) {
					radioGp.clearCheck();
				}
				String[] options = ((String) object2.get("ops")).split(",");
				op1.setText(options[0]);
				op2.setText(options[1]);
				op3.setText(options[2]);
				op4.setText(options[3]);
			} else {
				Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
				intent.putExtra("score", score);
				intent.putExtra("quesNo", quesNo);
				startActivity(intent);
			}
		}};
		nextBtn.setOnClickListener(l1);

		//on clicking the PREVIOUS button
		OnClickListener l2 = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (quesNo == 0) {
					Toast.makeText(getApplicationContext(),"No previous question", Toast.LENGTH_SHORT).show();
				}
				else {
					quesNo = quesNo - 1;
					JSONObject object3 = (JSONObject)arr.get(quesNo);
					ques_text.setText((String) object3.get("ques"));
					ans_text.setText("Correct answer is: " + object3.get("ans"));
					String[] options1 = ((String) object3.get("ops")).split(",");
					op1.setText(options1[0]);
					op2.setText(options1[1]);
					op3.setText(options1[2]);
					op4.setText(options1[3]);
					radioGp.clearCheck();
				}
			}
		};
		previousBtn.setOnClickListener(l2);
	}
}
