package dev.prater.services;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.prater.models.UserAccount;
import dev.prater.repository.UserAccountDAO;

@ExtendWith(MockitoExtension.class)
public class UserServiceTester {
	@InjectMocks
	private static UserAccService us;
	@Mock
	private static UserAccountDAO mockUserDAO;
	
	@BeforeAll
	public static void setUp() 
	{
		us = new UserAccService();
		mockUserDAO = mock(UserAccountDAO.class);
	}
	
	@Test
	public void shouldReturnAllUsers()
	{
		List<UserAccount> uL = new ArrayList<>();
		uL.add(new UserAccount(1,"a","z","abc","xyz"));
		uL.add(new UserAccount(2,"b","z","abc","xyz"));
		uL.add(new UserAccount(3,"c","z","abc","xyz"));
		uL.add(new UserAccount(4,"d","z","abc","xyz"));
		uL.add(new UserAccount(5,"e","z","abc","xyz"));
		
		when(mockUserDAO.getAllUserAccounts()).thenReturn(uL);
		
		assertEquals(uL, us.getAllUsers()); 
	}
}
