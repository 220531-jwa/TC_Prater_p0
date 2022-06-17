package dev.prater.services;

import java.util.List;

import dev.prater.models.BankAccount;
import dev.prater.repository.BankAccDAO;
import dev.prater.repository.UserAccountDAO;

public class BankAccService {
	private static BankAccDAO bDAO = new BankAccDAO();

	public BankAccount createBankAcount(BankAccount ledgerFromRequestBody, int uID) {
		BankAccount bA = new BankAccount(0, uID, ledgerFromRequestBody.getAmount(), ledgerFromRequestBody.getType());
		UserAccountDAO uDAO = new UserAccountDAO();
		if (uDAO.getUserAccount(bA.getUserID()) != null) {return bDAO.createBankAcc(bA);}
		else {return null;}
	}
	
	public List<BankAccount> getUsersLedgers(int uID, int lowerLimit, int upperLimit) {return bDAO.getBankAccountsByUID(uID, lowerLimit, upperLimit);}
	
	public BankAccount getBankAccount(int bID) {return bDAO.getBankAccountByBID(bID);}

	public BankAccount updateLedger(BankAccount bUpdate) {return bDAO.updateBankAccount(bUpdate);}
	
	public BankAccount updateBalance(int bID, int input)  
	{
		if (bDAO.getBankAccountByBID(bID) == null) {return null;}
		else 
		{
			BankAccount oldLedger = bDAO.getBankAccountByBID(bID);
			if (input > 0 || oldLedger.getAmount()+input > 0) 
			{
				BankAccount tempLedger = new BankAccount(oldLedger.getAccountNumber(),oldLedger.getUserID(),oldLedger.getAmount()+input,oldLedger.getType());
				return bDAO.updateBankAccount(tempLedger);
			}
			else return null;
		}
	}


	public boolean attemptDeleteBankAccount(BankAccount guilty) {
		BankAccount suspect = bDAO.getBankAccountByBID(guilty.getAccountNumber());
		if (suspect == null) {return false;}
		else if (suspect.getAccountNumber() == guilty.getAccountNumber()) {bDAO.deleteBankAccount(guilty.getAccountNumber()); return true;}
		else {return false;}
	}
}
