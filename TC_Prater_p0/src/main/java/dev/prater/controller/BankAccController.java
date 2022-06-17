package dev.prater.controller;

import java.util.List;

import dev.prater.models.BankAccount;
import dev.prater.services.BankAccService;

import io.javalin.http.Context;

public class BankAccController {
	private static BankAccService bass = new BankAccService(); //the fish, not the clef 
	
	public static void createNewBankAccount(Context ctx)
	{
		int uID = Integer.parseInt(ctx.pathParam("id0"));
		BankAccount ledgerFromRequestBody = ctx.bodyAsClass(BankAccount.class);
		BankAccount b = bass.createBankAcount(ledgerFromRequestBody, uID); // unmarshalling
		if (b != null) {ctx.status(201); ctx.json(b);}
		else {ctx.status(404); ctx.result("User not found, bank account creation aborted.");}
	}
	
	public static void getBankAccount(Context ctx)
	{
		int bID = Integer.parseInt(ctx.pathParam("id1"));
		BankAccount retrievedLedger = bass.getBankAccount(bID);
		if (retrievedLedger.getAccountNumber() != 0) {ctx.status(200); ctx.json(retrievedLedger);}
		else {ctx.status(404);}
	}
	
	public static void getUsersWallets(Context ctx)
	{
		int uID = Integer.parseInt(ctx.pathParam("id0"));
		int lowerLimit = -1;
		int upperLimit = -1; 
		String floor = ctx.queryParam("amountGreaterThan");
		String cieling = ctx.queryParam("amountLessThan");
		if (floor != null) {lowerLimit = Integer.parseInt(floor);}
		if (cieling != null) {upperLimit = Integer.parseInt(cieling);}
		List<BankAccount> wL = bass.getUsersLedgers(uID, lowerLimit, upperLimit);
		//String queryParam = ctx.queryParam("someKey"); //can have multiple queryParams, as website.url?qp1=val1?qp2=val2
		ctx.json(wL);
		if (wL.size() == 0) {ctx.status(404);} else {ctx.status(200);}
	}
	
	public static void updateBankAccount (Context ctx)
	{
		BankAccount bUpdate = ctx.bodyAsClass(BankAccount.class);
		BankAccount bReturned = bass.updateLedger(bUpdate);
		ctx.json(bReturned);
		if (bReturned.getAccountNumber() > 0) {ctx.status(200);} else {ctx.status(404);}
	}
	
	public static void transactBankAccount (Context ctx) 
	{
		boolean isDeposit = false;
		int bID = Integer.parseInt(ctx.pathParam("id1"));
		int adjustment = 0; 
		String input = ctx.body();
		String[] command = new String[2];
		BankAccount output = null;
		
		if (bass.getBankAccount(bID).getUserID() == Integer.parseInt(ctx.pathParam("id0"))) 
		{
			input = input.toLowerCase();
			input = input.replaceAll("[^a-z0-9:]", "");
			command = input.split(":");
			if (command[0] == "deposit") {isDeposit = true;}
			else if (command[0] == "withdraw") {isDeposit = false;}
			adjustment = Integer.parseInt(command[1]);
			if (!isDeposit) {adjustment = adjustment*(-1);}
			output = bass.updateBalance(bID, adjustment);
			if (output != null) {ctx.status(200); ctx.json(output);}
			else {ctx.status(422); ctx.result("Insufficient funds");}
		}
		else {ctx.status(404);}
	}
	
	public static void transferFunds(Context ctx)
	{
		int adjustment = 0;
		int transFrom = Integer.parseInt(ctx.pathParam("id1"));
		int transTo = Integer.parseInt(ctx.pathParam("id2"));
		String input = ctx.body();
		String[] command = new String[2];
		BankAccount[] wallets = new BankAccount[2];
		input = input.toLowerCase();
		input = input.replaceAll("[^a-z0-9:]", "");
		command = input.split(":");
		adjustment = Integer.parseInt(command[1]);
		if (bass.getBankAccount(transFrom) == null || bass.getBankAccount(transTo) == null) {ctx.status(404);} //do the ledgers exist?
		else if (bass.getBankAccount(transFrom).getUserID() != Integer.parseInt(ctx.pathParam("id0"))) {ctx.status(404);} //does id0 own id1?
		else 
		{
			wallets[0] = bass.updateBalance(bass.getBankAccount(transFrom).getAccountNumber(), adjustment*-1);
			wallets[1] = bass.updateBalance(bass.getBankAccount(transTo).getAccountNumber(), adjustment);
			if (wallets[0] == null|| wallets[1] == null) {ctx.status(422);} //did the transfer fail?
			else {ctx.status(200);}
		}
	}
	
	public static void deleteBankAccount (Context ctx)
	{
		BankAccount guilty = ctx.bodyAsClass(BankAccount.class);
		boolean guillotine = bass.attemptDeleteBankAccount(guilty);
		if (guillotine == true) {ctx.status(205);} else {ctx.status(404);}
	}

}
