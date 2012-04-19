package com.sigapp.buzztime;

import java.util.Observable;
import java.util.Observer;

import edu.purdue.cs.Message;
import edu.purdue.cs.RoomConnection;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
	 
	 ApplicationController controller;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Typeface type = Typeface.createFromAsset(getAssets(),"COOLVETI.TTF");
        
        TextView name = (TextView)findViewById(R.id.trivial);
        //name.setTypeface(type);
        
        /*text_uname = (TextView) findViewById(R.id.t_username);
        text_score = (TextView) findViewById(R.id.t_score);
        
        Button choiceA = (Button) findViewById(R.id.choiceA);
        Button choiceB = (Button) findViewById(R.id.choiceB);
        Button choiceC = (Button) findViewById(R.id.choiceC);
        Button choiceD = (Button) findViewById(R.id.choiceD);
        
        choiceA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				controller.sendAnswer('A');
			}
        });
        choiceB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				controller.sendAnswer('B');
			}
        });
        choiceC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				controller.sendAnswer('C');
			}
        });
        choiceD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				controller.sendAnswer('D');
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
    	final EditText nameBox = (EditText) layout.findViewById(R.id.unameInput);
    	final EditText codeBox = (EditText) layout.findViewById(R.id.codeInput);
    	final Context context = this.getApplicationContext();
    	alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//EditText userNameBox = (EditText) layout.findViewById(R.id.unameInput);
				String userName = nameBox.getText().toString();
        		String roomName = codeBox.getText().toString();
        		text_uname.setText("User Name:" + " " + userName);
        		text_score.setText("Score: " + "0");
        		
        		controller = new ApplicationController(userName, roomName, context);
			}
    		
    	});

    	
    	
    	AlertDialog joinGameDialog = alertDialogBuilder.create();

		joinGameDialog.show();*/
    }
    
}