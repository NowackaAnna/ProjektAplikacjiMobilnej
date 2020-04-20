package pl.edu.uwr.pum.flagi;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "flagibaza.db";
    private static File DB_PATH = null;
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        //File path = context.getDatabasePath("flagibaza.db");

        DB_PATH = context.getAbsolutePath();
        Log.e("Path1", String.valueOf(DB_PATH));
        Log.i("dziala","tu dziala");
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
            checkDB = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.OPEN_READONLY);
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
        String outFileName = DB_PATH+DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while((length=mInput.read(buffer))>0){
            mOutput.write(buffer, 0 ,length);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    public void openDataBase() throws SQLException{
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.OPEN_READONLY);
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
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return mDataBase.query("flagibaza",null,null,null,null,null,null);
    }


}

