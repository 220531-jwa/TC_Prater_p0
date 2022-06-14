package dev.prater.utils;

import java.util.*;

import dev.prater.controller.UserAccController;
import dev.prater.models.*;
//import dev.prater.service.*;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;


/*for those who don't get the joke: an amentum increases javelin speed much like a sling increases rock speed*/
public class Amentum { 
	public void serverRequestHandler() {
		Javalin app = Javalin.create();
		app.start(8081);
		//app.[request type here]("[url extension here, ex: /games/]", ctx -> {[lambda function here]});
		
		app.routes(() -> {
			get((ctx) ->ctx.result("This is a bank!"));
			path("/clients", () -> {
				get(UserAccController::getAllUserAccounts);
				post(UserAccController::createNewUserAcc);
				path("/{id}", () -> {
					get(UserAccController::getUserAccount);
					path("/accounts", () -> {
						//GET all accounts of user with id1
						path("/{id}", () -> {
							path("/transfer", () -> {
								path("/{id}", () -> {
									//transfer funds from id2 to id3
								});
							});
						});
					});
				});
			});
		});
		
		app.exception(Exception.class, (e, ctx) -> {
		    ctx.status(404);
		    ctx.result("<h1>User not found</h1>");
		});
		
		//app.close();
	}
}
