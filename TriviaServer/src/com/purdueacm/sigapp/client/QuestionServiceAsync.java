package com.purdueacm.sigapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.purdueacm.sigapp.shared.Question;

public interface QuestionServiceAsync {

	void getAllQuestions(AsyncCallback<Question[]> callback);

}
