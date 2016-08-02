package com.example.lappy.quizapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class ScoreActivity extends Activity {
    TextView resultView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		resultView = (TextView)findViewById(R.id.result_textView);
		Intent intent = getIntent();
		int quesNo = intent.getExtras().getInt("quesNo");
		int score = intent.getExtras().getInt("score");
		resultView.setText("Number of correct answers are " + score + " out of " + quesNo);
	}
}
