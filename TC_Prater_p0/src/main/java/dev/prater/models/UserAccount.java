package dev.prater.models;

public class UserAccount {
	int userID;
	String persName; //personal+family instead of first/last so Japanese users don't have to keep track of how many layers of flipped names they're on
	String famName;
	String username;
	String passkey;
	
	public UserAccount() {;}
	
	public UserAccount(int uID, String pN, String fN, String uN, String pK)
	{
		this.userID=uID;
		this.persName = pN;
		this.famName = fN;
		this.username=uN;
		this.passkey=pK;
	}
	
	public int getUserID() {return userID;}	
	public String getPersName() {return persName;}
	public String getFamName() {return famName;}
	public String getUsername() {return username;}
	public String getPasskey() {return passkey;}

	public void setPersName(String persName) {this.persName = persName;}	
	public void setFamName(String famName) {this.famName = famName;}
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String passkey) {this.passkey = passkey;}

	@Override
	public String toString() {return "User [id: "+userID+", personal name: "+persName+
			", family name: "+famName+", username: "+username+", passkey: "+passkey+"]";}
}
