package com.sigapp.buzztime;

import java.util.Observable;
import java.util.Observer;

import edu.purdue.cs.Message;
import edu.purdue.cs.RoomConnection;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BuzzTimeActivity extends Activity {
	
	 String userName = "" ;
	 
	 TextView text_uname;
	 TextView text_score;
	 
	 RoomConnection room;
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
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				Button b = new Button(BuzzTimeActivity.this);
				b.setHeight(300);
				b.setText((char)('A' + position) + "");
				b.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Log.d("Click debugging", ((char)('A' + position))+"");
						
					}
				});
				return b;
			}
        	
        });
        
        createJoinDialog();
    }
    
    public void createJoinDialog() {
    	final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	alertDialogBuilder.setMessage("New Game");
    	alertDialogBuilder.setCancelable(false);
    	LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
    	final View layout = inflater.inflate(R.layout.join_game_dialog,(ViewGroup) findViewById(R.id.dialog_root));
    	alertDialogBuilder.setView(layout);
    	final EditText codeBox = (EditText) layout.findViewById(R.id.codeInput);
    	
    	alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//EditText userNameBox = (EditText) layout.findViewById(R.id.unameInput);
        		String userName = codeBox.getText().toString();
        		text_uname.setText("User Name:" + " " + userName);
        		text_score.setText("Score: " + "0");
        		
        		room = new RoomConnection(userName, new Observer() {
					@Override
					public void update(Observable arg0, Object arg1) {
						Message m = (Message) arg1;
						Log.d("New Message", m.getText());
						
					}
        		});
			}
    		
    	});

    	
    	
    	AlertDialog joinGameDialog = alertDialogBuilder.create();

		joinGameDialog.show();
    }
    
}