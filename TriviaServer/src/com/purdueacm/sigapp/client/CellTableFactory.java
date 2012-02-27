package com.purdueacm.sigapp.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.purdueacm.sigapp.shared.Question;

public class CellTableFactory {

	/**
	 * Creates an editable CellTable with all the questions in it.
	 * 
	 * @param questionService QuestionServiceAsync that can be used to get the content of the table.
	 * @return A new CellTable filled with every question that is in the datastore.
	 */
	public static CellTable<Question> createEditableCellTable(final QuestionServiceAsync questionService) {
		// Create a CellTable
		final CellTable<Question> toReturn = new CellTable<Question>();
		
		// Create a question column
		TextCell questionCell = new TextCell();
		Column<Question, String> questionColumn = new Column<Question, String>(questionCell) {

			@Override
			public String getValue(Question object) {
				return object.getQuestion();
			}
			
		};
		
		// Create a correct answer column
		TextCell correctAnswerCell = new TextCell();
		Column<Question, String> correctAnswerColumn = new Column<Question, String>(correctAnswerCell) {

			@Override
			public String getValue(Question object) {
				return object.getCorrectAnswer();
			}
			
		};
		
		// Create incorrect answer column #1
		TextCell incorrectAnswerCell1 = new TextCell();
		Column<Question, String> incorrectAnswerColumn1 = new Column<Question, String>(incorrectAnswerCell1) {

			@Override
			public String getValue(Question object) {
				return object.getIncorrectAnswers()[0];
			}

		};

		// Create incorrect answer column #2
		TextCell incorrectAnswerCell2 = new TextCell();
		Column<Question, String> incorrectAnswerColumn2 = new Column<Question, String>(incorrectAnswerCell2) {

			@Override
			public String getValue(Question object) {
				return object.getIncorrectAnswers()[1];
			}

		};

		// Create incorrect answer column #3
		TextCell incorrectAnswerCell3 = new TextCell();
		Column<Question, String> incorrectAnswerColumn3 = new Column<Question, String>(incorrectAnswerCell3) {

			@Override
			public String getValue(Question object) {
				return object.getIncorrectAnswers()[2];
			}

		};
		
		// Add all the columns to the table
		toReturn.addColumn(questionColumn, "Question");
		toReturn.addColumn(correctAnswerColumn, "Correct Answer");
		toReturn.addColumn(incorrectAnswerColumn1, "Incorrect Answer 1");
		toReturn.addColumn(incorrectAnswerColumn2, "Incorrect Answer 2");
		toReturn.addColumn(incorrectAnswerColumn3, "Incorrect Answer 3");
		
		
		// Create a data provider.
		AsyncDataProvider<Question> dataProvider = new AsyncDataProvider<Question>() {

			@Override
			protected void onRangeChanged(HasData<Question> display) {
				// Get the visible range of rows in the table.
				final Range range = display.getVisibleRange();
				final int start = range.getStart();
				final int end = range.getStart() + range.getLength();
				
				// Get a list of the columns that can be sorted
				final ColumnSortList sortList = toReturn.getColumnSortList();
				
				/** Use this to implement sorting. Boolean value will have to be used in the getAllQuestions method, and data will have to be sorted on server.
				 * 
				 * boolean sortAscending = false;
				 * if(sortList.size() != 0)
				 *     sortAscending = !sortList.get(0).isAscending();
				 */
				
				
				questionService.getAllQuestions(new AsyncCallback<Question[]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Data call failed. Message is: " + caught.getMessage());
					}

					@Override
					public void onSuccess(Question[] result) {
						List<Question> dataInRange = Arrays.asList(result);
						//TODO add size limitation
						toReturn.setRowData(dataInRange);
					}
					
				});
				
			}
			
		};
		
		dataProvider.addDataDisplay(toReturn);
		
		/**
		 * Add a ColumnSortEvent.AsyncHandler to connect sorting to the AsyncDataProvider
		 * 
		 * AsyncHandler columnSortHandler = new AsyncHandler(toReturn);
		 * toReturn.addColumnSortHandler(columnSortHandler);
		 * 
		 * toReturn.getColumnSortList().push(questionColumn);
		 */
		
		return toReturn;
	}
}
