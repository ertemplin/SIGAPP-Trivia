package com.purdueacm.sigapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class QuestionEditor implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// Gets a RootPanel for the <div id="editorBody" />
		RootPanel root = RootPanel.get("editorBody");
		TextBox t = new TextBox();
		t.setText("Test!");
		root.addStyleName("testAlign");
		root.add(t);
		//TODO Implement a UI. This is currently just an example of what GWT can do.

	}

}
