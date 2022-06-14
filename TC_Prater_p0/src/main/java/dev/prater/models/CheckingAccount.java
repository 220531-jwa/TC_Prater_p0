package dev.prater.models;

public class CheckingAccount extends BankAccount {
	int debitCardNumber;
	
	CheckingAccount(int uID){super(uID);}
	CheckingAccount(int uID,int a){super(uID,a);}
	
	//doesn't look like it's useful in this project
}
