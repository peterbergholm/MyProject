package com.example.peterbergholm.myproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An activity representing a single Project detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ProjectListActivity}.
 */
public class ProjectDetailActivity extends AppCompatActivity {

    static Long projectId;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath = null;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.addcomment:
                // SIIRRYTÄÄN SYÖTTÄMÄÄN PROJETILLE PÄIVÄKIRJAA
                Intent newProject = new Intent( ProjectDetailActivity.this, DiaryActivity.class);
                newProject.putExtra("projectId", projectId);
                startActivity(newProject);
                return true;
            case R.id.addpic :
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photo = null;
                    try{
                        photo = createImageFile();

                    }catch (IOException ex){
                        Snackbar.make(findViewById(R.id.projectDetailCoordinator), R.string.pic_error, Snackbar.LENGTH_SHORT).show();
                        return false;
                    }
                    if(photo != null){
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.example.android.fileprovider",
                                photo);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
                return true;

            case R.id.instructions:
                // SIIRRYTÄÄN INTSRUCTIONS NÄYTTÖÖN
                Intent instructions = new Intent(ProjectDetailActivity.this, Instructions.class);
                instructions.putExtra("caller", "detail");
                startActivity(instructions);
                return true;

            case android.R.id.home:
                navigateUpTo(new Intent(this, ProjectListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
/*
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView= new ImageView(this);
            mImageView.setImageBitmap(imageBitmap);
            */
            if(mCurrentPhotoPath != null){
                final DBHelper dbHelper = new DBHelper(this);
                dbHelper.saveDiary(projectId, mCurrentPhotoPath, true);
                Snackbar.make(findViewById(R.id.projectDetailCoordinator), R.string.pic_taken, Snackbar.LENGTH_SHORT).show();

                // After adding comment, start the detail view to re-fresh the view with the pic information
                Intent intent = new Intent(ProjectDetailActivity.this, ProjectDetailActivity.class);
                intent.putExtra(ProjectDetailFragment.ARG_ITEM_ID, projectId);
                startActivity(intent);

            }
            else
                Snackbar.make(findViewById(R.id.projectDetailCoordinator), R.string.pic_error, Snackbar.LENGTH_SHORT).show();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            DBHelper dbHelper = new DBHelper(this);
            projectId = getIntent().getLongExtra(ProjectDetailFragment.ARG_ITEM_ID, 0);
            Project mItem = dbHelper.getProject( String.valueOf(projectId));
            ArrayList<String> diary = dbHelper.getDiary(projectId);


            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ProjectDetailFragment.PROJECT_NAME,
                    String.valueOf(mItem.projectName));
            arguments.putString(ProjectDetailFragment.PROJECT_OWNER,
                    String.valueOf(mItem.projectOwner));
            arguments.putString(ProjectDetailFragment.PROJECT_DESCRIPTION,
                    String.valueOf(mItem.projectDescription));
            arguments.putStringArrayList(ProjectDetailFragment.DIARY_COMMENT,diary);

            ProjectDetailFragment fragment = new ProjectDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.project_detail_container, fragment)
                    .commit();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
