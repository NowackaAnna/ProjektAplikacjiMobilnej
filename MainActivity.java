package pl.edu.uwr.pum.flagi;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonEuropa;
    private Button buttonAfryka;
    //private DatabaseHelper mDBHelper;
    //private SQLiteDatabase mDb;
    public String table_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonEuropa = (Button) findViewById(R.id.Europa);
        buttonEuropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "Europa";
                openQuiz();
                //mDb.execSQL("SELECT * FROM EUROPA");

            }
        });
        buttonAfryka = (Button) findViewById(R.id.Afryka);
        buttonAfryka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_name = "Afryka";
                openQuiz();

            }
        });

    }

    public void openQuiz() {
        Intent intent = new Intent(this,Quiz.class);
        //startActivityForResult();
        intent.putExtra("Tabela",table_name);
        startActivity(intent);

    }
}
