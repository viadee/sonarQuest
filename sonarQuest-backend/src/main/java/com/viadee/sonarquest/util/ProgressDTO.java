package com.viadee.sonarquest.util;

public class ProgressDTO {
	private double totalAmount;
	private double numberOfVariable;
	private double calculatedProgress;

	public ProgressDTO(double totalAmount, double numberOfVariable) {
		super();
		this.totalAmount = totalAmount;
		this.numberOfVariable = numberOfVariable;
	}
	
	public ProgressDTO(double totalAmount, double numberOfVariable, double calculatedProgress) {
		super();
		this.totalAmount = totalAmount;
		this.numberOfVariable = numberOfVariable;
		this.calculatedProgress = calculatedProgress;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getNumberOfVariable() {
		return numberOfVariable;
	}
	public void setNumberOfVariable(double numberOfVariable) {
		this.numberOfVariable = numberOfVariable;
	}
	public double getCalculatedProgress() {
		return calculatedProgress;
	}
	public void setCalculatedProgress(double calculatedProgress) {
		this.calculatedProgress = calculatedProgress;
	}
}
