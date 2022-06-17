package dev.prater.controller;

import java.util.List;

import dev.prater.models.UserAccount;
import dev.prater.services.UserAccService;

import io.javalin.http.Context;

public class UserAccController {
	private static UserAccService us = new UserAccService();

	public static void createNewUserAcc(Context ctx) {
		ctx.status(201);
		UserAccount userFromRequestBody = ctx.bodyAsClass(UserAccount.class);
		UserAccount u = us.createUserAcount(userFromRequestBody); // unmarshalling
		ctx.json(u);
	}

	public static void getAllUserAccounts(Context ctx) {
		ctx.status(200);
		List<UserAccount> users = us.getAllUsers();
		ctx.json(users);
	}
	
	public static void getUserAccount(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id0"));
		UserAccount u = null;
		try {
			u = us.getUserAccount(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.status(200);
		ctx.json(u);
	}
	
	public static void updateUserAccount(Context ctx) {
		boolean accountFound = false;
		UserAccount uChanged = ctx.bodyAsClass(UserAccount.class); //unmarshalling
		accountFound = us.updateUserAccount(uChanged);
		if (accountFound) {ctx.status(200);}
		else {ctx.status(404); ctx.result("This user might not exist. Double-check the user ID and try again.");}
	}
	
	public static void deleteUserAccount(Context ctx) {
		boolean victory = false;
		int id = Integer.parseInt(ctx.pathParam("id0"));
		victory = us.deleteUserAccount(id);
		if (victory) {ctx.status(205); ctx.result("Guillotine");}
		else {ctx.status(404); ctx.result("You can't kill what is already dead.");}
	}
}
