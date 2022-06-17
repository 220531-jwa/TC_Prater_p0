package dev.prater.utils;

import dev.prater.controller.UserAccController;
import dev.prater.controller.BankAccController;

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
		app.start(8082);
		//app.[request type here]("[url extension here, ex: /games/]", ctx -> {[lambda function here]});
		
		app.routes(() -> {
			get((ctx) ->ctx.result("This is a bank!"));
			path("/clients", () -> {
				get(UserAccController::getAllUserAccounts);
				post(UserAccController::createNewUserAcc);
				path("/{id0}", () -> {
					get(UserAccController::getUserAccount);
					put(UserAccController::updateUserAccount);
					delete(UserAccController::deleteUserAccount);
					path("/accounts", () -> {
						post(BankAccController::createNewBankAccount);
						get(BankAccController::getUsersWallets);
						path("/{id1}", () -> {
							get(BankAccController::getBankAccount);
							put(BankAccController::updateBankAccount);
							delete(BankAccController::deleteBankAccount);
							patch(BankAccController::transactBankAccount);
							path("/transfer", () -> {
								path("/{id2}", () -> {
									patch(BankAccController::transferFunds);
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
/*	POST /clients => Creates a new client return a 201 status code COMPLETE
GET /clients => gets all clients return 200 COMPLETE
GET /clients/10 => get client with id of 10 return 404 if no such client exist COMPLETE 
PUT /clients/12 => updates client with id of 12 return 404 if no such client exist COMPLETE
DELETE /clients/15 => deletes client with the id of15 return 404 if no such client exist return 205 if success COMPLETE
POST /clients/5/accounts =>creates a new account for client with the id of 5 return a 201 status code COMPLETE
GET /clients/7/accounts => get all accounts for client 7 return 404 if no client exists COMPLETE
GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and 2000 return 404 if no client exists COMPLETE
GET /clients/9/accounts/4 => get account 4 for client 9 return 404 if no account or client exists COMPLETE
PUT /clients/10/accounts/3 => update account with the id 3 for client 10 return 404 if no account or client exists COMPLETE
DELETE /clients/15/accounts/6 => delete account 6 for client 15 return 404 if no account or client exists COMPLETE
PATCH /clients/17/accounts/12 => Withdraw/deposit given amount (Body: {"deposit":500} or {"withdraw":250} return 404 if no account or client exists return 422 if insufficient funds COMPLETE
PATCH /clients/12/accounts/7/transfer/8 => transfer funds from account 7 to account 8 (Body: {"amount":500}) return 404 if no client or either account exists return 422 if insufficient funds COMPLETE*/