package com.purdueacm.sigapp.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class Question {

	// Used to identify the question.
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	String key;
	
	@Persistent
	String question;
	
	@Persistent
	String correctAnswer;
	
	@Persistent
	String[] incorrectAnswers;
}
