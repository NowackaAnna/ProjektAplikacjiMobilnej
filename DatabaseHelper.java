package pl.edu.uwr.pum.flagi;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "flagibaza6";
    private static String DB_PATH = null;
    private static final int DB_VERSION = 1;
    public static final String TABLE_Europa = "Europa";


    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        //File path = context.getDatabasePath("flagibaza6.db");

        //DB_PATH = path.getAbsolutePath();
        this.DB_PATH = "/data/data/"+context.getPackageName()+"/"+"databases";

        //Log.e("Path1", String.valueOf(DB_PATH));
        //Log.i("dziala","tu dziala");
    }
    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();
        if (dbExist){

        }
        else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            }
            catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String mPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLException e){

        }
        if (checkDB!=null){
            checkDB.close();
        }
        return checkDB != null ? true:false;

    }
    private void copyDataBase() throws IOException{
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while((length=mInput.read(buffer))>0){
            mOutput.write(buffer, 0 ,length);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
        Log.i("dziala","baza skopiowana");
    }
    public void openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.OPEN_READWRITE);
        Log.i("dziala","baza otwarta");
    }

    @Override
    public synchronized void close(){
        if (mDataBase != null){
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (newVersion>oldVersion){
            try{
                copyDataBase();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public Cursor queryEuropa(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return mDataBase.query("Europa",null,null,null,null,null,null);
    }
    public Cursor queryAfryka(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return mDataBase.query("Afryka",null,null,null,null,null,null);
    }
    public Cursor queryPunkty(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return mDataBase.query("Punkty",null,null,null,null,null,null);
    }
    public Cursor queryQuestionEuropa(String nr_question){
        String query="SELECT * FROM Europa WHERE Indeks = ?";
        String[] selectionArgs = {nr_question};
        return mDataBase.rawQuery(query, selectionArgs);
    }
    public Cursor queryQuestionAfryka(String nr_question){
        String query="SELECT * FROM Afryka WHERE Indeks = ?";
        String[] selectionArgs = {nr_question};
        return mDataBase.rawQuery(query, selectionArgs);
    }
    public Cursor queryQuestionAzja(String nr_question){
        String query="SELECT * FROM Azja WHERE Indeks = ?";
        String[] selectionArgs = {nr_question};
        return mDataBase.rawQuery(query, selectionArgs);
    }
    public Cursor queryQuestionAmerykaN(String nr_question){
        String query="SELECT * FROM AmerykaN WHERE Indeks = ?";
        String[] selectionArgs = {nr_question};
        return mDataBase.rawQuery(query, selectionArgs);
    }
    public Cursor queryQuestionAmerykaS(String nr_question){
        String query="SELECT * FROM AmerykaS WHERE Indeks = ?";
        String[] selectionArgs = {nr_question};
        return mDataBase.rawQuery(query, selectionArgs);
    }
    public Cursor queryQuestionAustraliaOceania(String nr_question){
        String query="SELECT * FROM AustraliaOceania WHERE Indeks = ?";
        String[] selectionArgs = {nr_question};
        return mDataBase.rawQuery(query, selectionArgs);
    }
    public boolean updatePoint(String kontynent, Integer mmpunkty){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(kontynent,mmpunkty);
        mDataBase.update("Punkty",contentValues,null,null);
        return true;

    }

    public int getQuestionCount(String kontynent) {
        String countQuery = "SELECT  * FROM " + kontynent;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //public Cursor getAllData(){
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    Cursor res = db.rawQuery("SELECT * FROM Europa",null);
    //    return res;
    //}
    //public Cursor pokazWszystkie(){
    //    String[] kolumny = {"Country","Flag","Indeks","Podpowiedz1","Podpowiedz2"};
    //    SQLiteDatabase db = getReadableDatabase();
    //    Cursor kursor = db.query("Europa",kolumny,null,null,null,null,null);
     //   return kursor;
    //}
    //public List<Rekord> pokazWybraneEuropa(Integer Indeks){
      //  List<Rekord> wybrane = new LinkedList<Rekord>();
       // SQLiteDatabase db = getReadableDatabase();
       // String[] kolumny = {"Country","Flag","Indeks","Podpowiedz1","Podpowiedz2"};
       // String args[] = {Indeks+""};
        //Cursor kursor = db.query("Europa",kolumny," Indeks=?",args,null,null,null, null);
       // if(kursor != null){
        //    while (kursor.moveToNext()){
         //       Rekord rekord = new Rekord();
          //      rekord.setCountry(kursor.getString(0));
           //     //rekord.setFlag(kursor.getBlob(1));
             //   rekord.setIndeks(kursor.getInt(2));
              //  rekord.setPodpowiedz1(kursor.getString(3));
               // rekord.setPodpowiedz2(kursor.getString(4));
                //wybrane.add(rekord);}

        //}
        //return wybrane;
    //}
    public Cursor dajWszystkie(){
        String[] kolumny = {"Country","Flag","Indeks","Podpowiedz1","Podpowiedz2"};
        //SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = mDataBase.query(TABLE_Europa,kolumny,null,null,null,null,null);
        Log.i("dziala","dane odczytane");
        return kursor;
    }
    public Integer countEuropa(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor c = mDataBase.query("Europa",null,null,null,null,null,null);
        int liczba_pytan = c.getCount();

        return liczba_pytan;
    }
    public Integer countAfryka(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor c = mDataBase.query("Afryka",null,null,null,null,null,null);
        int liczba_pytan = c.getCount();

        return liczba_pytan;
    }
    public Integer countAzja(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor c = mDataBase.query("Azja",null,null,null,null,null,null);
        int liczba_pytan = c.getCount();

        return liczba_pytan;
    }
    public Integer countAmerykaN(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor c = mDataBase.query("AmerykaN",null,null,null,null,null,null);
        int liczba_pytan = c.getCount();

        return liczba_pytan;
    }
    public Integer countAmerykaS(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor c = mDataBase.query("AmerykaS",null,null,null,null,null,null);
        int liczba_pytan = c.getCount();

        return liczba_pytan;
    }
    public Integer countAustraliaOceania(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor c = mDataBase.query("AustraliaOceania",null,null,null,null,null,null);
        int liczba_pytan = c.getCount();

        return liczba_pytan;
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.setVersion(oldVersion);
    }



}

