package dev.prater.services;

import java.util.List;

import dev.prater.models.UserAccount;
import dev.prater.repository.UserAccountDAO;

public class UserAccService {
	private static UserAccountDAO uDAO = new UserAccountDAO();
	
	public UserAccount login(String username, String passkey) 
	{	
		UserAccount uA = uDAO.getUserAccount(username);
		
		if (uA.getPasskey().equals(passkey)) {
			return uA;
		}
		return null;
	}
	
//	public UserAccount updatePasskey(int id, String passkey) {
//		// check if that user exists
//		return uDAO.updateUserPassword(id, passkey);
//	}
		
	// register / create user
	public UserAccount createUserAcount(UserAccount uA) {
		UserAccount createdUser = uDAO.createUserAcc(uA);
		return createdUser;
	}

	public List<UserAccount> getAllUsers() {
		return uDAO.getAllUserAccounts();
	}

	public UserAccount getUserAccount(int id) throws Exception {
		// this is where you could put some business logic 
		// for example checking if the User returned by userDao.getUserById(id) is null 
		UserAccount uA = uDAO.getUserAccount(id);
		
		if (uA == null) {
			throw new Exception("User not found");
		}
		
		return uA;
	}

//	public void deleteUserAccount(int id) {
//		uDAO.deleteUserAccount(id);
//	}
	
//	public void updateUserAccount(UserAccount uChanged) {
//		uDAO.updateUserAccount(uChanged);
//	}

}
