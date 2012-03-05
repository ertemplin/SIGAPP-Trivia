class Question < ActiveRecord::Base
	validates :question, :presence => true
	validates :correctAnswer, :presence => true
	validates :incorrect1, :presence => true
	validates :incorrect2, :presence => true
	validates :incorrect3, :presence => true
end
