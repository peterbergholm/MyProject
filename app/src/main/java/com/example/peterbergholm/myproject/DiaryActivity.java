package com.example.peterbergholm.myproject;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiaryActivity extends AppCompatActivity {

    static long projectId = 0;

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        android.view.MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
                case android.R.id.home:
                    navigateUpTo(new Intent(this, ProjectListActivity.class));
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Get the projectId from the call parameters
        projectId=getIntent().getLongExtra("projectId",0);

        final DBHelper dbHelper = new DBHelper(this);


        // Reference to the project comment field
        final EditText etComment= findViewById(R.id.diaryComment);

        Button newProjectComment = (Button) findViewById(R.id.diaryButton);
        newProjectComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read project data values to the variables
                String projectComment = etComment.getText().toString();

                // USerid and projectname are mandatory parameters
                if( projectId != 0) {
                    dbHelper.saveDiary(projectId, projectComment, false);
                    if(dbHelper != null)
                        dbHelper.close();

                    // After adding comment, start the detail view
                    Intent intent = new Intent(DiaryActivity.this, ProjectDetailActivity.class);
                    intent.putExtra(ProjectDetailFragment.ARG_ITEM_ID, projectId);
                    startActivity(intent);

                }

            }
        });


    }

    public void showToast(String teksti){
        int aika = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, teksti, aika);
        toast.show();
    }

}
