package com.example.peterbergholm.myproject;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static boolean firstTime = true;
    public static ArrayList<String> shoppings = new ArrayList<String>();

    public static long userId = 0;
    ArrayAdapter<String> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.addProject:
                // SIIRRYTÄÄN SYÖTTÄMÄÄN UUTTA  PROJEKTIA
                Intent newProject = new Intent( MainActivity.this, NewProject.class);
                newProject.putExtra("userId", userId);
                startActivity(newProject);
                return true;
            case R.id.viewProjects:
                // SIIRRYTÄÄN PROJEKTILISTAUKSEEN
                Intent newProjectList = new Intent( MainActivity.this, ProjectListActivity.class);
                newProjectList.putExtra("userId", userId);
                startActivity(newProjectList);
                return true;
            case R.id.instructions:
                // SIIRRYTÄÄN INTSRUCTIONS NÄYTTÖÖN
                Intent instructions = new Intent(MainActivity.this, Instructions.class);
                instructions.putExtra("caller", "main");
                startActivity(instructions);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //NOTIFIKAATIO TOAST, TOAST ON POPUP IKKKUNA JOKA NÄKYY NOIN SEKUNNIN
    // APUMETODI
    public void showToast(String teksti){
        int aika = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, teksti, aika);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (firstTime) {
            userId = getIntent().getLongExtra("userId", 0);
            firstTime = false;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
