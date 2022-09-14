package com.jerry.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jerry.myapp.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

public class ScoreActivity extends BaseActivity {

    private Integer orderId;
    private CommonTitleBar titleBar;
    private TextView tip1;
    private TextView tip2;
    private TextView tip3;
    private RatingBar score1;
    private RatingBar score2;
    private RatingBar score3;
    private EditText et_describe;
    private Button submit;

    @Override
    protected int initLayout() {
        return R.layout.activity_score;
    }

    @Override
    protected void initView() {
        titleBar = findViewById(R.id.titlebar);
        tip1 = findViewById(R.id.describe_tip1);
        tip2 = findViewById(R.id.describe_tip2);
        tip3 = findViewById(R.id.describe_tip3);
        score1 = findViewById(R.id.describe_score1);
        score2 = findViewById(R.id.describe_score2);
        score3 = findViewById(R.id.describe_score3);
        et_describe = findViewById(R.id.et_reason);
        submit = findViewById(R.id.bt_submit);
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        orderId = bd.getInt("orderId");
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

        score1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String score_text1 = String.valueOf(rating);
                String scoreState1 = fun_getScoreState(score_text1);
                tip1.setText(scoreState1);
            }
        });

        score2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String score_text2 = String.valueOf(rating);
                String scoreState2 = fun_getScoreState(score_text2);
                tip2.setText(scoreState2);
            }
        });

        score3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String score_text3 = String.valueOf(rating);
                String scoreState3 = fun_getScoreState(score_text3);
                tip3.setText(scoreState3);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDescribe();
            }
        });
    }
    //根据分数转换成文字展示
    private String fun_getScoreState(String score) {
        String rating = "";
        switch (score) {
            case "1.0":
                rating = "差";
                break;
            case "2.0":
                rating = "较差";
                break;
            case "3.0":
                rating = "一般";
                break;
            case "4.0":
                rating = "好";
                break;
            case "5.0":
                rating = "极好";
                break;
        }
        return rating;
    }

    private void submitDescribe(){

    }
}