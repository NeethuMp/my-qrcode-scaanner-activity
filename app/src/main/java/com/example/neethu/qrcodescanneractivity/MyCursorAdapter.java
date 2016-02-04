package com.example.neethu.qrcodescanneractivity;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by neethu on 3/2/16.
 */
public class MyCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    public MyCursorAdapter(Context context, Cursor c, int i) {
        super(context, c);
        cursorInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return cursorInflater.inflate(R.layout.text,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView qData= (TextView) view.findViewById(R.id.textView2);
        TextView qDate= (TextView) view.findViewById(R.id.textView3);
        String colum1 = cursor.getString( cursor.getColumnIndex(DBHelper.SCAN_URL) );
        qData.setText(colum1);
        long currentDateTime = cursor.getLong( cursor.getColumnIndex(DBHelper.SCAN_DATE) );
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy - kk:mm aa ");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentDateTime);
        String newDate=formatter.format(calendar.getTime());
        qDate.setText(newDate);




    }
}
