package pl.edu.uwr.pum.flagi;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Quiz extends AppCompatActivity {
    //private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String tabela;
    Integer nr_pytania;
    Integer quest_nr;
    Integer mPunkty;
    String mCountry;
    String mPodpowiedz1;
    String mPodpowiedz2;
    byte[] mFlaga;
    Bitmap mFlagaBitmap;
    Boolean check_odpowiedz;
    Integer liczba_pytan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        View viewQuiz = (View) findViewById(R.id.quiz);
        final ImageView mFlagaObrazek = (ImageView) findViewById(R.id.imageView);
        Button buttonPodpowiedz1 = (Button)findViewById(R.id.Podpowiedz1);
        Button buttonPodpowiedz2 = (Button)findViewById(R.id.Podpowiedz2);
        final Button buttonSprawdz = (Button)findViewById(R.id.Sprawdz);
        final EditText editPanstwo = (EditText)findViewById(R.id.editPanstwo);
        final Button buttonNastepne = (Button)findViewById(R.id.Nastepny);
        Button buttonPowrot = (Button)findViewById(R.id.Poprzedni);



        check_odpowiedz = Boolean.FALSE;
        buttonNastepne.setEnabled(false);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null)
        {
            tabela =(String) b.get("Tabela");
            nr_pytania =(Integer)b.get("Numer_Pytania");

        }

        final DatabaseHelper mDBHelper = new DatabaseHelper(Quiz.this);
        Cursor c;
        final Cursor question_country;
        Cursor question_country1;
        mPunkty = nr_pytania;

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
        quest_nr = nr_pytania+1;

        if (tabela.equals("Europa")){
            question_country1 = mDBHelper.queryQuestionEuropa(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countEuropa("Europa", null,null,null,null,null,null);
            getSupportActionBar().setTitle("Flagi - Europa");
            viewQuiz.setBackgroundColor(Color.argb(255, 128, 229, 255));
        }
        else if (tabela.equals("Afryka")){
            question_country1 = mDBHelper.queryQuestionAfryka(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countAfryka("Afryka", null,null,null,null,null,null);
            getSupportActionBar().setTitle("Flagi - Afryka");
            viewQuiz.setBackgroundColor(Color.argb(255, 191, 191, 191));
        }
        else if (tabela.equals("Azja")){
            question_country1 = mDBHelper.queryQuestionAzja(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countAzja("Azja", null,null,null,null,null,null);
            getSupportActionBar().setTitle("Flagi - Azja");
            viewQuiz.setBackgroundColor(Color.argb(255, 255, 255, 153));
        }
        else if(tabela.equals("AmerykaN")){
            question_country1 = mDBHelper.queryQuestionAmerykaN(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countAmerykaN("AmerykaN", null,null,null,null,null,null);
            getSupportActionBar().setTitle("Flagi - Ameryka Północna");
            viewQuiz.setBackgroundColor(Color.argb(255, 255, 153, 194));
        }
        else if(tabela.equals("AmerykaS")){
            question_country1 = mDBHelper.queryQuestionAmerykaS(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countAmerykaS("AmerykaS", null,null,null,null,null,null);
            getSupportActionBar().setTitle("Flagi - Ameryka Południowa");
            viewQuiz.setBackgroundColor(Color.argb(255, 255, 128, 128));
        }
        else if(tabela.equals("AustraliaOceania")){
            question_country1 = mDBHelper.queryQuestionAustraliaOceania(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countAustraliaOceania("AustraliaOceania", null,null,null,null,null,null);
            getSupportActionBar().setTitle("Flagi - Australia i Oceania");
            viewQuiz.setBackgroundColor(Color.argb(255, 173, 235, 173));
        }
        else{
            question_country1 = mDBHelper.queryQuestionEuropa(Integer.toString(quest_nr));
            liczba_pytan = mDBHelper.countEuropa("Europa", null,null,null,null,null,null);
        }


        question_country = question_country1;
        String numerPytania = Integer.toString(nr_pytania);




        if (question_country.moveToFirst()){
            do{
                mCountry = question_country.getString(0);
                mFlaga = question_country.getBlob(1);
                mFlagaBitmap = BitmapFactory.decodeByteArray(mFlaga, 0 , mFlaga.length);
                mPodpowiedz1 = question_country.getString(3);
                mPodpowiedz2 = question_country.getString(4);


                //Toast.makeText(Quiz.this,"Country: "+question_country.getString(0),Toast.LENGTH_LONG).show();
            } while(question_country.moveToNext());
        }
        mFlagaObrazek.setImageBitmap(mFlagaBitmap);



        buttonPodpowiedz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Quiz.this,mPodpowiedz1,Toast.LENGTH_LONG).show();
            }
        });

        buttonPodpowiedz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Quiz.this,mPodpowiedz2,Toast.LENGTH_LONG).show();
            }
        });

        buttonSprawdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String odpowiedz = editPanstwo.getText().toString();
                if (odpowiedz.equals(mCountry)){
                    if(quest_nr<liczba_pytan) {
                        mPunkty = mPunkty + 1;
                        Toast.makeText(Quiz.this, "Dobrze, idziesz dalej!", Toast.LENGTH_SHORT).show();
                        check_odpowiedz = Boolean.TRUE;
                        Boolean upPoint = mDBHelper.updatePoint(tabela, mPunkty);
                        quest_nr = quest_nr + 1;
                        buttonNastepne.setEnabled(true);
                        buttonSprawdz.setEnabled(false);
                    }
                    else{
                        mPunkty = mPunkty + 1;
                        Toast.makeText(Quiz.this, "Gratulacje, ukończyłeś kontynent!", Toast.LENGTH_SHORT).show();
                        Boolean upPoint = mDBHelper.updatePoint(tabela, mPunkty);
                        buttonSprawdz.setEnabled(false);
                    }

                }

                else {
                    Toast.makeText(Quiz.this,"Spróbuj jeszcze raz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNastepne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check_odpowiedz==Boolean.TRUE){

                    Cursor q_country;
                    if(tabela.equals("Europa")) {
                        q_country = mDBHelper.queryQuestionEuropa(Integer.toString(quest_nr));
                    }
                    else if (tabela.equals("Afryka")){
                        q_country = mDBHelper.queryQuestionAfryka(Integer.toString(quest_nr));
                    }
                    else if(tabela.equals("Azja")){
                        q_country = mDBHelper.queryQuestionAzja(Integer.toString(quest_nr));
                    }
                    else if(tabela.equals("AmerykaN")){
                        q_country = mDBHelper.queryQuestionAmerykaN(Integer.toString(quest_nr));
                    }
                    else if(tabela.equals("AmerykaS")){
                        q_country = mDBHelper.queryQuestionAmerykaS(Integer.toString(quest_nr));
                    }
                    else if (tabela.equals("AustraliaOceania")){
                        q_country = mDBHelper.queryQuestionAustraliaOceania(Integer.toString(quest_nr));
                    }
                    else {
                        q_country = mDBHelper.queryQuestionEuropa(Integer.toString(quest_nr));
                    }
                    if (q_country.moveToFirst()){
                        do{
                            mCountry = q_country.getString(0);
                            mFlaga = q_country.getBlob(1);
                            mFlagaBitmap = BitmapFactory.decodeByteArray(mFlaga, 0 , mFlaga.length);
                            mPodpowiedz1 = q_country.getString(3);
                            mPodpowiedz2 = q_country.getString(4);


                            //Toast.makeText(Quiz.this,"Country: "+q_country.getString(0),Toast.LENGTH_LONG).show();
                        } while(q_country.moveToNext());
                    }
                    mFlagaObrazek.setImageBitmap(mFlagaBitmap);
                    editPanstwo.setText("");
                    editPanstwo.setHint("PAŃSTWO");
                    check_odpowiedz = Boolean.FALSE;
                    buttonSprawdz.setEnabled(true);

                }
                else {
                    buttonNastepne.setEnabled(false);
                }
            }
        });

        buttonPowrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBHelper.close();
                openMain();

            }
        });


    }

    public void openMain() {
        onBackPressed();
        finish();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
