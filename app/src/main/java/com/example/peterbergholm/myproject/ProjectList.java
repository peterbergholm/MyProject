package com.example.peterbergholm.myproject;

import com.example.peterbergholm.myproject.DBHelper;
import com.example.peterbergholm.myproject.Project;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for providing list of the created projects that are saved in the db.
 * <p>
 */
public class ProjectList extends AppCompatActivity {

    /**
     * An array of project items.
     */
    public static List<Project> ITEMS;
    /**
     * A map of sample (dummy) items, by projectID.
     */
    public static Map<Long, Project> ITEM_MAP;


    // Lue projektitiedot tauluihin
    public ProjectList(List<Project> mITEMS, Map<Long, Project> mITEM_MAP){
        ITEMS = mITEMS;
        ITEM_MAP = mITEM_MAP;
    }



    public List<Project> getItems(){
        return ITEMS;
    }

    public Map<Long, Project> getItemMap(){
        return ITEM_MAP;
    }

    private void createStaticList(){

    }

}
