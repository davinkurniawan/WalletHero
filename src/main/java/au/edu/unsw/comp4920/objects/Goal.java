package au.edu.unsw.comp4920.objects;

import java.math.BigDecimal;

public class Goal {
	private int goalID;
	private int personID;
	private String goalPeriod;
	private String detail;
	private BigDecimal amount;
	private int type;
	private int category;

	public static int SAVING_GOAL = 1;
	public static int EXPENSE_RESTRICTION_GOAL = 2;

	public int getGoalID() {
		return goalID;
	}

	public void setGoalID(int goalID) {
		this.goalID = goalID;
	}

	public String getGoalPeriod() {
		return goalPeriod;
	}

	public void setGoalPeriod(String goalPeriod) {
		this.goalPeriod = goalPeriod;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
		this.amount = this.amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSavingGoal() {
		this.type = Goal.SAVING_GOAL;
	}

	public void setExpenseRestrictionGoal() {
		this.type = Goal.EXPENSE_RESTRICTION_GOAL;
	}

	public boolean isSavingGoal() {
		return this.type == Goal.SAVING_GOAL;
	}

	public boolean isExpenseRestrictionGoal() {
		return this.type == Goal.EXPENSE_RESTRICTION_GOAL;
	}
	
	public int getGoalType() {
		return this.type;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

}
