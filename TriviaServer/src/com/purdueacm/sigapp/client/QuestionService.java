package com.purdueacm.sigapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.purdueacm.sigapp.shared.Question;

public interface QuestionService extends RemoteService {

	public Question[] getAllQuestions();
	
}
