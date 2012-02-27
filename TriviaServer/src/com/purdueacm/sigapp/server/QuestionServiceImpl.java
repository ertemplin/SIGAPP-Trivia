package com.purdueacm.sigapp.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.purdueacm.sigapp.client.QuestionService;
import com.purdueacm.sigapp.shared.Question;

@SuppressWarnings("serial")
public class QuestionServiceImpl extends RemoteServiceServlet implements QuestionService {

	/**
	 * Gets all of the questions from the datastore. Only use this method on the admin 
	 * console, otherwise you will use a TON of datastore reads.
	 * @return
	 */
	public Question[] getAllQuestions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(Question.class);
		
		List<Question> questions;
		Question[] toReturn = null;
		
		questions = (List<Question>)query.execute(Question.class);
		
		toReturn = new Question[questions.size()];
		
		for(int i = 0; i<questions.size(); i++) {
			toReturn[i] = questions.get(i);
		}
		
		
		query.closeAll();
		pm.close();
		return toReturn;
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
