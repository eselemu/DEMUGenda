package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import Pojo.JobDAO;
import Pojo.Subject;
import Pojo.SubjectDAO;

public class LoadActivity extends AppCompatActivity {

    ImageView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        animationView = findViewById(R.id.viewAnimation_LoadActivity);
        JobDAO jobDAO = new JobDAO();
        jobDAO.GetJobs(this, true);
        SubjectDAO subjectDAO = new SubjectDAO();
        subjectDAO.GetSubjects(this);
        animationView.setImageResource(R.drawable.creature_animation);
        AnimationDrawable creatureAnimation = (AnimationDrawable)animationView.getDrawable();
        creatureAnimation.start();

    }
}