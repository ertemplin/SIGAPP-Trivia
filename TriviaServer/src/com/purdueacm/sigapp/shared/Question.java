package com.purdueacm.sigapp.shared;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Question implements Serializable {

	// Used to identify the question.
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String key;
	
	@Persistent
	private String question;
	
	@Persistent
	private String correctAnswer;
	
	@Persistent
	private String[] incorrectAnswers;
	
	// YES, there is a reason that all of those are private, and accessed through getter and setter methods. 
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public String[] getIncorrectAnswers() {
		return incorrectAnswers;
	}
	
	public void setIncorrectAnswers(String[] incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
}
