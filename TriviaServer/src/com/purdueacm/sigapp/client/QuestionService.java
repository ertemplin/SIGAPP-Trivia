package com.purdueacm.sigapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.purdueacm.sigapp.shared.Question;

@RemoteServiceRelativePath("question-rpc")
public interface QuestionService extends RemoteService {

	public Question[] getAllQuestions();
	
	public void addQuestion(Question q);
	
}
