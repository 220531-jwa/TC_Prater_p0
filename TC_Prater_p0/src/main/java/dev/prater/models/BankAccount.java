package dev.prater.models;

public class BankAccount {
	int accountNumber;
	int amount; //measured in US cents
	int userID;
	double interest;
	
	BankAccount(int uID) {this(uID,0);}
	
	BankAccount(int uID, int amount)
	{
		this.accountNumber = 0;
		this.interest = 0;
		this.userID = uID;
		this.amount = amount;
	}
	
	int getAccountNumber() {return this.accountNumber;}
	int getAmount() {return this.amount;}
	int getUserID() {return this.userID;}
	double getInterest() {return this.interest;}
	
	void setAccountNumber(int aN) {this.accountNumber=aN;}
	void setAmount(int a) {this.amount=a;}
	void setInterest(double i) {this.interest=i;}
	
	String transferTo(int a, int aN) {return transferTo(a,aN,this.userID);}
	String transferTo(int a, int aN, int uID) 
	{
		String output = "";
		return output;
	}
}
