package dev.prater.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.prater.models.*;
import dev.prater.utils.ConnectionUtil;

public class BankAccDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	//Create
	public BankAccount createBankAcc(BankAccount bA)
	{
		String sql = "insert into bankaccounts values (default, ?,?,?) returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bA.getType());
			ps.setInt(2, bA.getAmount());
			ps.setInt(3, bA.getUserID());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {return new BankAccount(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("balance"), rs.getString("type"));}
		}
		catch(SQLException e) {e.printStackTrace();}
		return null;
	}

	//Read
	public List<BankAccount> getAllBankAccounts()
	{
		List<BankAccount> ledgers = new ArrayList<>();
		String sql = "select * from bankaccounts";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uid = rs.getInt("user_id");
				int amount = rs.getInt("balance");
				String type = rs.getString("type");
				
				BankAccount bA = new BankAccount(id, uid, amount, type);
				ledgers.add(bA);
			}
			
			return ledgers;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public BankAccount getBankAccountByBID(int bID)
	{
		BankAccount bA = null;
		String sql = "select * from bankaccounts where id = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uid = rs.getInt("user_id");
				int amount = rs.getInt("balance");
				String type = rs.getString("type");
				
				bA = new BankAccount(id, uid, amount, type);
			}
			
			return bA;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public List<BankAccount> getBankAccountsByUID(int uID, int lowerLimit, int upperLimit)
	{
		BankAccount bA = null;
		List<BankAccount> lA = new ArrayList<>();
		String sql = "select * from bankaccounts where user_id = ?";
		if (lowerLimit > -1) {sql += "and balance > ?";}
		if (upperLimit > -1) {sql += "and balance < ?";}
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uID);
			if (lowerLimit > -1 && upperLimit > -1) {ps.setInt(2, lowerLimit); ps.setInt(3, upperLimit);}
			else if (lowerLimit > -1) {ps.setInt(2, lowerLimit);}
			else if (upperLimit > -1) {ps.setInt(2, upperLimit);}
			else {;}
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uid = rs.getInt("user_id");
				int amount = rs.getInt("balance");
				String type = rs.getString("type");
				
				bA = new BankAccount(id, uid, amount, type);
				lA.add(bA);
			}
			
			return lA;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	//Update
	public BankAccount updateBankAccount (BankAccount bInput)
	{
		String sql = "update bankaccounts set type = ?, balance = ? where id = ? returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bInput.getType());
			ps.setInt(2, bInput.getAmount());
			ps.setInt(3, bInput.getAccountNumber());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {return new BankAccount(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("balance"), rs.getString("type"));}
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	//Destroy
	public void deleteBankAccount(int bID) 
	{
		String sql = "delete from bankaccounts where id = ? returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,bID);
			ps.executeQuery();
		}
		catch(SQLException e) {e.printStackTrace();}
	}
}
