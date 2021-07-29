package com.lctapp.lct.Activities

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.lctapp.lct.Classes.ExpandableListViewAdapter
import com.lctapp.lct.R

class FrequentlyAskedQuestions : AppCompatActivity() {
    val title:MutableList<String> = ArrayList()
    val subTitle:MutableList<MutableList<String>> = ArrayList()
    lateinit var expandableListView : ExpandableListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_frequently_asked_questions)

        expandableListView = findViewById(R.id.expandableListView)

        val diffQuestion:MutableList<String> = ArrayList()
        diffQuestion.add ("\n"+"Q:  Why does an LCT device display capture error when I want to capture a fingerprint?\n")

        // var diffAnswer:MutableList<String> = ArrayList()
        diffQuestion.add("\n" +
                "ANS:  When the finger is not placed properly on the scanner.\n" +
                "When the device has stayed for a while without use.\n" +
                "If the device scanner is dirty, therefore wipe it.\n" +
                "When the patient has oily fingers or an obstacle on their finger e.g.(Hinna) that prevents the sensors from reading patients fingerprints \n")
        diffQuestion.add("\n" +
                "Q:  During the verification process when the patient places the finger why doesn't it capture it directly?\n")

        diffQuestion.add("\n"+ "ANS:  This is because you have to tap on the capture button for it to fetch.\n")

        diffQuestion.add("\n" +
                "Q:  Do I have to register biometrics in every hospital I may visit?\n")

        diffQuestion.add("\n" +
                "ANS:  No, when your biometrics are captured at the hospital you visited earlier that's all. You only register biometrics once.\n")

        diffQuestion.add("\n" + "Q:  How many dependants is a principle member allowed to cover?\n")

        diffQuestion.add("\n" + "ANS:  A principal member is only allowed up-to four dependants.\n")

        title.add("Questions & Answers")
        // title.add("Answers")

        subTitle.add(diffQuestion)
        //subTitle.add(diffAnswer)

        expandableListView.setAdapter(ExpandableListViewAdapter(this@FrequentlyAskedQuestions,title,subTitle))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}