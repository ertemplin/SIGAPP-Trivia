package com.purdueacm.sigapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.purdueacm.sigapp.shared.Question;

@SuppressWarnings("serial")
public class QuestionServiceImpl extends RemoteServiceServlet {

	/**
	 * Gets all of the questions from the datastore.
	 * @return
	 */
	public Question[] getAllQuestions() {
		//TODO implement this.
		return null;
	}
}
