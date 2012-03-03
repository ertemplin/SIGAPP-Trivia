class RandomquestionController < ApplicationController
	# GET /random
	def index
		@questions = Question.all

		index = (Random.rand(@questions.size()))
		@chosenQuestion = @questions[index]

		render json: @chosenQuestion
	end
end
