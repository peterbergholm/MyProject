package com.example.peterbergholm.myproject;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * Created by peter on 16/6/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final Context context;
    private final static int    DB_VERSION = 10;

    public DBHelper(Context context) {
        super(context, "myApp.db", null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = null;
        try {
            query = "create table logins (userId Integer primary key autoincrement, " +
                    " username text, password text)";
            sqLiteDatabase.execSQL(query);
            query = "create table projects (projectId Integer primary key autoincrement, userId Integer, " +
                    " projectname text, projectowner text, projectDescription text)";
            sqLiteDatabase.execSQL(query);

            query = "create table projectdiary(diaryId Integer primary key autoincrement, projectId Integer, " +
                    " diaryDate CHAR DEFAULT(datetime('now','localtime')) , diaryComment text, diaryUri text)";
            sqLiteDatabase.execSQL(query);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try{
            System.out.println("UPGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
            onCreate(sqLiteDatabase);
            if (oldVersion<10){
                String query = "create table logins (userId Integer primary key autoincrement, " +
                        " username text, password text)";
                sqLiteDatabase.execSQL(query);
                query = "create table projectdb (projectId Integer primary key autoincrement, userId Integer, " +
                        " projectname text, projectowner text, projectDescription text)";
                sqLiteDatabase.execSQL(query);

                query = "create table projectdiary(diaryId Integer primary key autoincrement, projectId Integer, " +
                        " diaryDate CHAR DEFAULT(datetime('now','localtime')) , diaryComment text, diaryUri text)";
                sqLiteDatabase.execSQL(query);
            }
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        System.out.println("DOWNGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
    }

    public User insertUser (User queryValues){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", queryValues.username);
        values.put("password", queryValues.password);
        queryValues.userId=database.insert("logins", null, values);
        if(database != null)
            database.close();
        return queryValues;
    }

    public int updateUserPassword (User queryValues){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", queryValues.username);
        values.put("password", queryValues.password);
        queryValues.userId=database.insert("logins", null, values);
        database.close();
        return database.update("logins", values, "userId = ?", new String[] {String.valueOf(queryValues.userId)});
    }

    public User getUser (String username){
        String query = "Select userId, password from logins where username ='"+username+"'";
        User myUser = new User(0,username,"");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                myUser.userId=cursor.getLong(0);
                myUser.password=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        database.close();
        return myUser;
    }

    public void saveProject (Project queryValues){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("projectName", queryValues.projectName);
        values.put("projectOwner", queryValues.projectOwner);
        values.put("projectDescription", queryValues.projectDescription);
        values.put("userId", queryValues.userId);

        queryValues.setProjectId(database.insert("projects", null, values));
        database.close();
    }

    public Project getProject (String projectId){
        String query = "Select projectId, userId, projectName, projectOwner, projectDescription from projects where projectId = " +projectId;
        SQLiteDatabase database = this.getReadableDatabase();
        Project myProject = null;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                if(!cursor.isNull(0))
                    myProject= new Project(cursor.getLong(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return myProject;
    }

    public void getProjects (Long userId, List projectList, Map projectMap){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "Select projectId, projectName, projectOwner, projectDescription from projects where userId = "+userId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Long projectId = cursor.getLong(0);
                Project myProject = new Project(projectId, userId, cursor.getString(1), cursor.getString(2), cursor.getString(3));
                projectList.add(myProject);
                projectMap.put(projectId,myProject);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void saveDiary (long projectId, String comment, boolean image){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("projectId", projectId);
        values.put("diaryComment", comment);
        if (image)
           values.put("diaryUri", comment);
        else
            values.put("diaryUri", "");
        database.insert("projectdiary", null, values);
        database.close();
    }

    public ArrayList<String> getDiary(long projectId){
        ArrayList<String> theDiary = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "Select diaryDate, diaryComment, diaryUri from projectdiary where projectId = "+projectId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                // If the diary comment consists the image path, we will add a prefix "pic:" to it
                if(cursor.getString(2).equals(""))
                    theDiary.add(cursor.getString(0)+ ": "+ cursor.getString(1));
                else
                    theDiary.add("pic:"+ cursor.getString(2));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return theDiary;
    }

}
