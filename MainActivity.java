package pl.edu.uwr.pum.flagi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button buttonEuropa;
    private Button buttonAfryka;
    private Button buttonAmerykaN;
    private Button buttonAmerykaS;
    private Button buttonAzja;
    private Button buttonAustralia;

    //private DatabaseHelper mDBHelper;
    //private SQLiteDatabase mDb;
    public String table_name;
    public Integer question_number;
    private SQLiteDatabase mDb;
    Integer EuropaPunkty;
    Integer AfrykaPunkty;
    Integer AmerykaNPunkty;
    Integer AmerykaSPunkty;
    Integer AzjaPunkty;
    Integer AustaliaPunkty;

    String mEuropaPunkty;
    String mAfrykaPunkty;
    String mAmerykaNPunkty;
    String mAmerykaSPunkty;
    String mAzjaPunkty;
    String mAustaliaPunkty;

    String nazwaE;
    String nazwaAf;
    String nazwaAmN;
    String nazwaAmS;
    String nazwaAz;
    String nazwaAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseHelper mDBHelper = new DatabaseHelper(MainActivity.this);
        Cursor mPunkty;
        try{
            mDBHelper.createDataBase();
        }
        catch (IOException ioe){
            throw new Error("Unable to create database");
        }
        try{
            mDBHelper.openDataBase();
        }
        catch (SQLException sqle) {
            throw sqle;
        }
        //////to trzeba pozniej usunac
        //Boolean upPoint = mDBHelper.updatePoint("Europa",0);
        //Boolean upPoint2 = mDBHelper.updatePoint("Afryka",0);
        //Boolean upPoint3 = mDBHelper.updatePoint("AmerykaS",0);
        //Boolean upPoint4 = mDBHelper.updatePoint("AmerykaN",0);
        //Boolean upPoint5 = mDBHelper.updatePoint("Azja",0);
        //Boolean upPoint6 = mDBHelper.updatePoint("AustraliaOceania",0);




        mPunkty = mDBHelper.queryPunkty("Punkty",null,null,null,null,null,null);
        if (mPunkty.moveToFirst()){
            do{
                AfrykaPunkty = mPunkty.getInt(0);
                AmerykaNPunkty = mPunkty.getInt(1);
                AmerykaSPunkty = mPunkty.getInt(2);
                AustaliaPunkty = mPunkty.getInt(3);
                AzjaPunkty = mPunkty.getInt(4);
                EuropaPunkty = mPunkty.getInt(5);

            }while (mPunkty.moveToNext());
        }

        mDBHelper.close();


        mEuropaPunkty = Integer.toString(EuropaPunkty);
        mAfrykaPunkty = Integer.toString(AfrykaPunkty);
        mAmerykaNPunkty = Integer.toString(AmerykaNPunkty);
        mAmerykaSPunkty = Integer.toString(AmerykaSPunkty);
        mAzjaPunkty = Integer.toString(AzjaPunkty);
        mAustaliaPunkty = Integer.toString(AustaliaPunkty);


        nazwaE = "Europa: ";
        nazwaAf = "Afryka: ";
        nazwaAmN = "Ameryka Północna: ";
        nazwaAmS = "Ameryka Południowa: ";
        nazwaAz = "Azja: ";
        nazwaAO = "Australia i Oceania: ";


        buttonEuropa = (Button) findViewById(R.id.Europa);
        buttonAfryka = (Button) findViewById(R.id.Afryka);
        buttonAmerykaN = (Button)findViewById(R.id.AmerykaN);
        buttonAmerykaS = (Button)findViewById(R.id.AmerykaS);
        buttonAzja = (Button)findViewById(R.id.Azja);
        buttonAustralia = (Button)findViewById(R.id.Australia);


        buttonEuropa.setText(nazwaE + mEuropaPunkty);
        buttonAfryka.setText(nazwaAf + mAfrykaPunkty);
        buttonAmerykaN.setText(nazwaAmN + mAmerykaNPunkty);
        buttonAmerykaS.setText(nazwaAmS + mAmerykaSPunkty);
        buttonAzja.setText(nazwaAz + mAzjaPunkty);
        buttonAustralia.setText(nazwaAO + mAustaliaPunkty);


        buttonEuropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "Europa";
                question_number = EuropaPunkty;
                openQuiz();

            }
        });

        buttonAfryka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "Afryka";
                question_number = AfrykaPunkty;
                openQuiz();

            }
        });

        buttonAmerykaN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "AmerykaN";
                question_number = AmerykaNPunkty;
                openQuiz();

            }
        });

        buttonAmerykaS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "AmerykaS";
                question_number = AmerykaSPunkty;
                openQuiz();

            }
        });

        buttonAzja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "Azja";
                question_number = AzjaPunkty;
                openQuiz();

            }
        });

        buttonAustralia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "AustraliaOceania";
                question_number = AustaliaPunkty;
                openQuiz();

            }
        });

    }

    public void openQuiz() {
        Intent intent = new Intent(this,Quiz.class);
        //startActivityForResult();
        intent.putExtra("Tabela",table_name);
        intent.putExtra("Numer_Pytania",question_number);
        startActivity(intent);

    }
}
