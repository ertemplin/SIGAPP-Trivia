package com.sigapp.buzztime;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BuzzTimeActivity extends Activity {
	
	 String userName = "" ;
	 
	 TextView text_uname;
	 TextView text_score;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text_uname = (TextView) findViewById(R.id.t_username);
        text_score = (TextView) findViewById(R.id.t_score);
        GridView quesGrid = (GridView) findViewById(R.id.ques_grid);
        quesGrid.setAdapter(new BaseAdapter(){

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 4;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				Button b = new Button(BuzzTimeActivity.this);
				b.setHeight(300);
				b.setText((char)('A' + position) + "");
				
				return b;
			}
        	
        });
        
        final Dialog d = new Dialog(this);
        LinearLayout l = new LinearLayout(this);
		TextView uname = new TextView(BuzzTimeActivity.this);
		TextView code = new TextView(this);
		final EditText e_uname = new EditText(this);
		EditText e_code = new EditText(this);
		Button submitDialog = new Button(this);
		l.addView(uname);
		l.addView(e_uname);
		l.addView(code);
		l.addView(e_code);
		l.addView(submitDialog);
		l.setOrientation(LinearLayout.VERTICAL);
		uname.setText("User Name: ");
		code.setText("Code");
		submitDialog.setText("Submit");
	    d.setCancelable(false);
		
		submitDialog.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = e_uname.getText().toString();
				d.dismiss();
				text_uname.setText("User Name:" + " " + userName);
		        text_score.setText("Score: " + "0");
				
				
			}
			
		
		}
		
		
				);
		
		
		
        
		d.setContentView(l);
		
		d.show();
		
        
    }
}