package dev.prater.services;

import java.util.ArrayList;
import java.util.List;

import dev.prater.models.UserAccount;
import dev.prater.repository.UserAccountDAO;

public class UserAccService {
	private static UserAccountDAO uDAO = new UserAccountDAO();
	
	public UserAccount login(String username, String passkey) 
	{	
		UserAccount uA = uDAO.getUserAccount(username);
		
		if (uA.getPasskey().equals(passkey)) {return uA;}
		else {return null;}
	}
	
	// register / create user
	public UserAccount createUserAcount(UserAccount uA) {
		UserAccount createdUser = uDAO.createUserAcc(uA);
		return createdUser;
	}

	public List<UserAccount> getAllUsers() 
	{
		List<UserAccount> userList = uDAO.getAllUserAccounts();
		List<UserAccount> output = new ArrayList<>();
		UserAccount temp = new UserAccount();
		
		if (userList == null) {return null;}
		else {
			for (UserAccount ua: userList) 
			{
				temp = ua;
				temp.setPassword("omitted for security reasons");
				output.add(temp);
				temp = null;
			}
			return output;
		}
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

	public boolean updateUserAccount(UserAccount uChanged) {
		boolean victory = false;
		UserAccount uBack = uDAO.updateUserAccount(uChanged);
		if (uBack.getUserID() == uChanged.getUserID()) {victory = true;}
		return victory;
	}

	public boolean deleteUserAccount(int id) {return uDAO.deleteUserAccount(id);}
	
}
