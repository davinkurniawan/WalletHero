package au.edu.unsw.comp4920.objects;

import java.math.BigDecimal;

public class Goal {
	private int goalID;
	private int personID;
	private String goalPeriod;
	private String detail;

	private BigDecimal goalAmount;
	private BigDecimal currentAmount;

	private int type;
	private int category;
	private String categoryString;
	private String statusString;

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

	public BigDecimal getGoalAmount() {
		return goalAmount;
	}

	public void setGoalAmount(BigDecimal amount) {
		this.goalAmount = amount;
		this.goalAmount = this.goalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal amount) {
		this.currentAmount = amount;
		this.currentAmount = this.currentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
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

	public int getUserID() {
		return userID;
	}

	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	@Override
	public String toString() {
		return "Goal [goalID=" + goalID + ", personID=" + personID + ", goalPeriod=" + goalPeriod + ", detail=" + detail
				+ ", goalAmount=" + goalAmount + ", currentAmount=" + currentAmount + ", type=" + type + ", category="
				+ category + ", categoryString=" + categoryString + ", statusString=" + statusString + "]";
	}

}
