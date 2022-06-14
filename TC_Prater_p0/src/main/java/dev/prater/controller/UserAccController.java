package dev.prater.controller;

import java.util.List;

import dev.prater.models.UserAccount;
import dev.prater.services.UserAccService;

import io.javalin.http.Context;

public class UserAccController {
	private static UserAccService us = new UserAccService();

	public static void getAllUserAccounts(Context ctx) {
		ctx.status(200);
		List<UserAccount> users = us.getAllUsers();
		ctx.json(users);
	}
	
	public static void createNewUserAcc(Context ctx) {
		ctx.status(201);
		UserAccount userFromRequestBody = ctx.bodyAsClass(UserAccount.class);
		UserAccount u = us.createUserAcount(userFromRequestBody); // unmarshalling
		ctx.json(u);
	}
	
	public static void getUserAccount(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		UserAccount u = null;
		try {
			u = us.getUserAccount(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.status(200);
		ctx.json(u);
	}
	
//	public static void deleteUser(Context ctx) {
//		int id = Integer.parseInt(ctx.pathParam("id"));
//		us.deleteUser(id);
//	}
	
//	public static void updateUser(Context ctx) {
//		UserAccount uChanged = ctx.bodyAsClass(User.class); //unmarshalling
//		System.out.println("updateUser -= " + uChanged);
//		us.updateUser(uChanged);
//	}
	
//	public static void updatePassword(Context ctx) {
//		int id = Integer.parseInt(ctx.pathParam("id"));
//		UserAccount u = ctx.bodyAsClass(User.class); // {"password": "newPassword"}
//		System.out.println(u.getPassword());
//		us.updatePassword(id, u.getPassword());
//	}
}
