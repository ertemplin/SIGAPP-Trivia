package com.purdueacm.sigapp.server;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.purdueacm.sigapp.client.QuestionService;
import com.purdueacm.sigapp.shared.Question;

@SuppressWarnings("serial")
public class QuestionServiceImpl extends RemoteServiceServlet implements QuestionService {

	/**
	 * Gets all of the questions from the datastore.
	 * @return
	 */
	public Question[] getAllQuestions() {
		//TODO implement this.
		return null;
	}
	
	/**
	 * Adds the provided question to the App Engine datastore.
	 * @param q
	 */
	public void addQuestion(Question q) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Question toAdd = new Question();
		toAdd.setQuestion(q.getQuestion());
		toAdd.setCorrectAnswer(q.getCorrectAnswer());
		toAdd.setIncorrectAnswers(q.getIncorrectAnswers());
		
		pm.makePersistent(toAdd);
		
		pm.close();
	}
}
