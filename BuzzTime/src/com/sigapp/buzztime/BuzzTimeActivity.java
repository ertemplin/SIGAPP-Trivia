package com.sigapp.buzztime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class BuzzTimeActivity extends Activity {
	
	final int selected = Color.parseColor("#FF6633");
	final int unselected = Color.parseColor("#33CCFF");
	final int disabled = Color.parseColor("#A9A9A9");
	
	String userName = "";
	Button choiceA;
	Button choiceB;
	Button choiceC;
	Button choiceD;

	ApplicationController controller;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		choiceA = (Button) findViewById(R.id.choiceA);
		choiceB = (Button) findViewById(R.id.choiceB);
		choiceC = (Button) findViewById(R.id.choiceC);
		choiceD = (Button) findViewById(R.id.choiceD);

		choiceA.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				controller.sendAnswer('A');
				enableButtons(false);
				choiceA.setBackgroundColor(selected);
			}
		});
		choiceB.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				controller.sendAnswer('B');
				enableButtons(false);
				choiceB.setBackgroundColor(selected);
			}
		});
		choiceC.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				controller.sendAnswer('C');
				enableButtons(false);
				choiceC.setBackgroundColor(selected);
			}
		});
		choiceD.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				controller.sendAnswer('D');
				enableButtons(false);
				choiceD.setBackgroundColor(selected);
			}
		});

		createJoinDialog();
	}
	
	public void enableButtons(boolean state) {
		if(!state) {
			choiceA.setBackgroundColor(disabled);
			choiceB.setBackgroundColor(disabled);
			choiceC.setBackgroundColor(disabled);
			choiceD.setBackgroundColor(disabled);
		}
		else {
			choiceA.setBackgroundColor(unselected);
			choiceB.setBackgroundColor(unselected);
			choiceC.setBackgroundColor(unselected);
			choiceD.setBackgroundColor(unselected);
		}
		choiceA.setEnabled(state);
		choiceB.setEnabled(state);
		choiceC.setEnabled(state);
		choiceD.setEnabled(state);
	}

	public void createJoinDialog() {
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("New Game");
		alertDialogBuilder.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.join_game_dialog, (ViewGroup) findViewById(R.id.dialog_root));
		alertDialogBuilder.setView(layout);
		final EditText nameBox = (EditText) layout.findViewById(R.id.unameInput);
		final EditText codeBox = (EditText) layout.findViewById(R.id.codeInput);
		final BuzzTimeActivity context = this;
		alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String userName = nameBox.getText().toString();
						String roomName = codeBox.getText().toString();

						controller = new ApplicationController(userName, roomName, context);
					}
				});

		AlertDialog joinGameDialog = alertDialogBuilder.create();

		joinGameDialog.show();
	}
}