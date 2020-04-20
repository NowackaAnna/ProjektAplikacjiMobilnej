package pl.edu.uwr.pum.flagi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

public class Quiz extends AppCompatActivity {
    //private DatabaseHelper mDBHelper;
    //private SQLiteDatabase mDb;
    String tabela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null)
        {
            tabela =(String) b.get("Tabela");

        }
        Toast.makeText(getApplicationContext(),tabela,Toast.LENGTH_SHORT).show();
        DatabaseHelper mDBHelper = new DatabaseHelper(Quiz.this);
        try{
            mDBHelper.createDataBase();
        }
        catch (IOException ioe){
            throw new Error("Unable to create database");
        }
        //try{
        //    mDBHelper.openDataBase();
        //}
        //catch (SQLException sqle){
         //   throw sqle;
       // }
        //Toast.makeText(getApplicationContext(),"Baza danych",Toast.LENGTH_SHORT).show();
        //c = mDBHelper.query("flagibaza",null,null,null,null,null,null);
    }
}
