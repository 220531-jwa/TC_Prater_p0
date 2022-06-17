package dev.prater.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.prater.models.*;
import dev.prater.utils.ConnectionUtil;

public class UserAccountDAO {//CRUD: Create Read Update Destroy for each DAO handler class
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	//Create
	public UserAccount createUserAcc(UserAccount uA)
	{
		String sql = "insert into useraccounts values (default, ?,?,?,?) returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uA.getPersName());
			ps.setString(2, uA.getFamName());
			ps.setString(3, uA.getUsername());
			ps.setString(4, uA.getPasskey());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {return new UserAccount(rs.getInt("id"),rs.getString("pers_name"),rs.getString("fam_name"),
					rs.getString("username"),rs.getString("passkey"));}
		}
		catch(SQLException e) {e.printStackTrace();}
		return null;
	}

	//Read
	public List<UserAccount> getAllUserAccounts()
	{
		List<UserAccount> users = new ArrayList<>();
		String sql = "select * from useraccounts";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				String persName = rs.getString("pers_name");
				String famName = rs.getString("fam_name");
				String username = rs.getString("username");
				String passkey = rs.getString("passkey");
				
				UserAccount uA = new UserAccount(id,persName,famName,username,passkey);
				users.add(uA);
			}
			
			return users;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public UserAccount getUserAccount(int uID)
	{
		UserAccount uA = null;
		String sql = "select * from useraccounts where id = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				String persName = rs.getString("pers_name");
				String famName = rs.getString("fam_name");
				String username = rs.getString("username");
				String passkey = rs.getString("passkey");
				
				uA = new UserAccount(id,persName,famName,username,passkey);
			}
			
			return uA;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public UserAccount getUserAccount(String username)
	{
		UserAccount uA = null;
		String sql = "select * from useraccounts where username = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				String persName = rs.getString("pers_name");
				String famName = rs.getString("fam_name");
				//String username = rs.getString("username"); //already have this one in inputs
				String passkey = rs.getString("passkey");
				
				uA = new UserAccount(id,persName,famName,username,passkey);
			}
			
			return uA;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	//Update
	public UserAccount updateUserAccount (UserAccount uInput)
	{
		String sql = "update useraccounts set pers_name = ?, fam_name = ?, username = ?, passkey = ? where id = ? returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uInput.getPersName());
			ps.setString(2, uInput.getFamName());
			ps.setString(3, uInput.getUsername());
			ps.setString(4, uInput.getPasskey());
			ps.setInt(5, uInput.getUserID());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {return new UserAccount(rs.getInt("id"),rs.getString("pers_name"),rs.getString("fam_name"),
					rs.getString("username"),rs.getString("passkey"));}
		}
		catch(SQLException e) {e.printStackTrace();}
		return null;
	}
	
	//Destroy
	public boolean deleteUserAccount(int uID) 
	{
		if (getUserAccount(uID)==null) {return false;}
		else {
			String sql = "delete from useraccounts where id = ? returning *";
			try(Connection conn = cu.getConnection();)
			{
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, uID);
				ps.executeQuery();
			    return true;
			}
			catch(SQLException e) {e.printStackTrace();}
		return false;
		}
	}
}
