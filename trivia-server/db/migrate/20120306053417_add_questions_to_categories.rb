class AddQuestionsToCategories < ActiveRecord::Migration
  def change
	  change_table :questions do |q|
		  q.references :category
	  end
  end
end
