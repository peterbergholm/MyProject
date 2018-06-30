package com.example.peterbergholm.myproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;


/**
 * A fragment representing a single Project detail screen.
 * This fragment is either contained in a {@link ProjectListActivity}
 * in two-pane mode (on tablets) or a {@link ProjectDetailActivity}
 * on handsets.
 */
public class ProjectDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String PROJECT_NAME = "projectName";
    public static final String PROJECT_OWNER = "projectOwner";
    public static final String PROJECT_DESCRIPTION = "projectDescription";
    public static final String DIARY_COMMENT = "diary";

    String projectName = null;
    String projectOwner = null;
    String projectDescription = null;
    ArrayList<String> diaryComment = null;
    String diaryUri = null;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProjectDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(PROJECT_NAME)) {
            projectName = getArguments().getString(PROJECT_NAME);
            projectOwner = getArguments().getString(PROJECT_OWNER);
            projectDescription = getArguments().getString(PROJECT_DESCRIPTION);
            diaryComment = getArguments().getStringArrayList(DIARY_COMMENT);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(projectName);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.project_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (projectName != null) {
            String text = projectDescription+"\n____________________________\n\n\n";
            for(int i=0; i<diaryComment.size();i++){
                    text+=diaryComment.get(i)+"\n---------------------------\n\n\n";
                    //TODO: Add photo to the list
            }
            ((TextView) rootView.findViewById(R.id.project_detail)).setText(text);
        }
        return rootView;
    }
}