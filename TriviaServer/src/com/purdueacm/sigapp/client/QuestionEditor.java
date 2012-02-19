package com.purdueacm.sigapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.purdueacm.sigapp.shared.Question;

public class QuestionEditor implements EntryPoint {
	private QuestionServiceAsync questionService = (QuestionServiceAsync) GWT.create(QuestionService.class);
	
	@Override
	public void onModuleLoad() {
		// Gets a RootPanel for the <div id="editorBody" />
		RootPanel root = RootPanel.get("editorBody");
		root.addStyleName("testAlign");
		
		Button newQuestionButton = new Button("Add New Question");
		newQuestionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Get the (x,y) position of the bottom left corner of the button that was clicked.
				Point dialogCorner = new Point(event.getRelativeElement().getAbsoluteLeft(), event.getRelativeElement().getAbsoluteBottom());
				// Make the dialog and set its top left corner to the point created before this line.
				makeNewQuestionDialog(true).setPopupPosition((int)dialogCorner.getX(), (int)dialogCorner.getY());
			}
		});

		root.add(newQuestionButton);
		//TODO Implement a table or something to allow admins to edit or remove questions.

	}
	
	/**
	 * Creates a new DialogBox that will be used to create a new question.
	 * 
	 * @param showOnCreate  If true, DialogBox shown when this method is called.
	 * @return The newly created DialogBox.
	 */
	public DialogBox makeNewQuestionDialog(boolean showOnCreate) {
		final DialogBox box = new DialogBox(true, false);
		box.setText("Add a new question!");
		
		VerticalPanel formPanel = new VerticalPanel();
		
		Label questionTextLabel = new Label("Question Text:");
		final TextArea questionText = new TextArea();
		
		Label correctAnswerLabel = new Label("Correct Answer:");
		final TextBox correctAnswerText = new TextBox();
		
		Label incorrectAnswersLabel = new Label("Incorrect Answers:");
		final TextBox incorrectAnswerText1 = new TextBox();
		final TextBox incorrectAnswerText2 = new TextBox();
		final TextBox incorrectAnswerText3 = new TextBox();
		

		Button createQuestionButton = new Button("Add Question");
		createQuestionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Question toSend = new Question();
				toSend.setQuestion(questionText.getText());
				toSend.setCorrectAnswer(correctAnswerText.getText());
				String[] incorrectAnswers = {incorrectAnswerText1.getText(), incorrectAnswerText2.getText(), incorrectAnswerText3.getText()};
				toSend.setIncorrectAnswers(incorrectAnswers);
				
				questionService.addQuestion(toSend, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Failed to add question because: "+ caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						box.hide();
					}
				});
			}
		});
		
		formPanel.add(questionTextLabel);
		formPanel.add(questionText);
		formPanel.add(correctAnswerLabel);
		formPanel.add(correctAnswerText);
		formPanel.add(incorrectAnswersLabel);
		formPanel.add(incorrectAnswerText1);
		formPanel.add(incorrectAnswerText2);
		formPanel.add(incorrectAnswerText3);
		formPanel.add(createQuestionButton);
		
		box.add(formPanel);
		
		box.setWidth("20em");
		
		if(showOnCreate)
			box.show();
		return box;
	}

}
