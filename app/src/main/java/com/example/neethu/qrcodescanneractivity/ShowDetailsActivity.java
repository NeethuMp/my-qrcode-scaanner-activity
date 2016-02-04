package com.example.neethu.qrcodescanneractivity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by neethu on 3/2/16.
 */
public class ShowDetailsActivity extends Activity {
    ListView showView;
    TextView qData,qDate;
    MyCursorAdapter myCursorAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_activity);
        showView= (ListView) findViewById(R.id.listView);
        loadListviewData();
    }
    public  void  loadListviewData()
    {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Cursor dataCursor = dbHelper.getAllData();
        myCursorAdapter=new MyCursorAdapter(this,dataCursor,0);

//        String[] from = new String[]{ DBHelper.SCAN_URL, DBHelper.SCAN_DATE };
//        int[] to = new int[]{ R.id.textView2, R.id.textView3 };
//        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.text,
//                dataCursor, from, to, SimpleCursorAdapter.NO_SELECTION);
//        showView.setAdapter(simpleCursorAdapter);
        showView.setAdapter(myCursorAdapter);
    }

}
