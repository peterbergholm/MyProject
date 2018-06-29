package com.example.peterbergholm.myproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class NewProject extends AppCompatActivity {

    Project newProject = null;
    private long userId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get the userId from the call parameters
        userId=getIntent().getLongExtra("userId",0);

        final DBHelper dbHelper = new DBHelper(this);


        // HAE VIITAUS XML TIEDOSTON EDITTEXT KENTTÄÄN R:n KAUTTA
        final EditText etProjectName = findViewById(R.id.projectName);
        final EditText etProjectOwner = findViewById(R.id.projectOwner);
        final EditText etProjectDescription = findViewById(R.id.projectDescription);

        Button newProjectSaveBt = (Button) findViewById(R.id.newProjectButton);
        newProjectSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read project data values to the variables
                String projectName = etProjectName.getText().toString();
                String projectOwner = etProjectOwner.getText().toString();
                String projectDescription = etProjectDescription.getText().toString();
                long projectId = 0; // This is just to give some value. Not used when saving new project because auto-increment in DB for this value.


                // USerid and projectname are mandatory parameters
                if( userId != 0 && projectName != null) {
                    newProject = new Project(projectId, userId, projectName, projectOwner, projectDescription);
                    dbHelper.saveProject(newProject);
                    showToast("NewprojectId:" + newProject.projectId+ "-->UserId: "+ userId + "--> ProjectName" + newProject.projectName +"->Owner"+newProject.projectOwner);
                    if(dbHelper != null)
                        dbHelper.close();

                    // SIIRRYTÄÄN PROJEKTILISTAUKSEEN
                    Intent newProjectList = new Intent( NewProject.this, ProjectListActivity.class);
                    newProjectList.putExtra("userId", userId);
                    startActivity(newProjectList);
                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }


    public void showToast(String teksti){
        int aika = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, teksti, aika);
        toast.show();
    }

}
