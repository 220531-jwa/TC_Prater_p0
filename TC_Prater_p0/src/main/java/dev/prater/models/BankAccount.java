package dev.prater.models;

public class BankAccount {
	int accountNumber;
	int userID; //measured in US cents
	int amount;
	String type;
	
	public BankAccount() {;}
	
	public BankAccount(int aN, int uID, int amount, String type)
	{
		this.accountNumber = aN;
		this.userID = uID;
		this.amount = amount;
		this.type = type;
	}
	
	public int getAccountNumber() {return this.accountNumber;}
	public int getAmount() {return this.amount;}
	public int getUserID() {return this.userID;}
	public String getType() {return this.type;}
	
	public void setAccountNumber(int aN) {this.accountNumber=aN;}
	public void setAmount(int a) {this.amount=a;}
	public void setType(String t) {this.type=t;}
	
	public String transferTo(int a, int aN) {return transferTo(a,aN,this.userID);}
	public String transferTo(int a, int aN, int uID) 
	{
		String output = "";
		return output;
	}
}
