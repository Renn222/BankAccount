import java.awt.Color;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class BankAccount 
{
	public int accountNum;
	private double balance;
	private String firstName;
	private String middleName;
	private String lastName;
	private String password;
	String message;
	private NumberFormat monies = NumberFormat.getCurrencyInstance();
	
	//This is first account
	BankAccount(int accNum, double amt, String pswd, String fName, String mName, String lName, String message)
	{	
		accountNum = accNum;
		balance = amt;
		password = pswd;
		firstName = fName;
		middleName = mName;
		lastName = lName;
				
		message = "";
		
	}
	
	int getAccountNum()
	{
		return accountNum;
	}
	
	static int getNewAccountNum()
	{
		Random stuff = new Random();
		return stuff.nextInt(90000) + 10000;
	}
	
	static void changeAccNum(BankAccount b, int accNum)
	{
		b.accountNum = accNum;
	}
	
	//Deposits money into account and returns True or False
	boolean deposit(double amount)
	{
		if(amount > 0)
		{
			balance += amount;
			message += "\n" + timestamp() + ": Deposit of amount " + monies.format(amount) + " made.";
			return true;
		}
		message += "\n" + timestamp() + ": Deposit Unsuccessful: Invalid Amount.";
		return false;
	}
	
	//Withdraws money and returns True or False
	boolean withdraw(double amount)
	{

		if(balance - amount >= 0)
		{
			balance -= amount;
			message += "\n" + timestamp() + ": Withdrawal of amount " + monies.format(amount) + " made.";
			return true;
		}
		
		//Overdraft fees
		message += "\n" + timestamp() +": A bank overdraft fee of 25 will be charged to your account. We thank you for your business.";
		return penalty();
	}
	
	//Overdraft Fees
	private boolean penalty()
	{
		balance -= 25;
		return false;
	}
	
	//Gets balance of acc
	double getBalance()
	{
		return balance;
	}
	
	//Displays accountNum, firstName, middleName, lastName and balance
	void display()
	{
		String profile = "Acount Number:" + accountNum + "\nName:" 
				+ firstName + " " + middleName + " " + lastName 
				+ "\nBalance: " + monies.format(balance);
		//System.out.println(profile);
	}
	
	String profile()
	{
		String myProfile = "Acount Number: " + accountNum + "\n\nName: " 
				+ firstName + " " + middleName + " " + lastName 
				+ "\n\nBalance: " + balance;
		
		return myProfile; 
	}
	
	//Transfers money to another account
	boolean transferTo(double amount, BankAccount otherAcct)
	{		
		if(!withdraw(amount))
		{
			message += "\n" + timestamp() + ": Overdraft during transfer. Transaction not processed. Please contant us for further assistance. "
					+ "A fee of $25 was applied.";
			return false;
		}
		if(amount < 0)
		{
			amount = amount - amount * 2;
		}
		otherAcct.deposit(amount);
		message += "\n" + timestamp() + ": Transfer of " + monies.format(amount) + " dollars to account " + otherAcct.accountNum + " complete.";
		return true;
		
	}
	
	//Takes money from another account
	int transferFrom(double amount, BankAccount otherAcct, String pswd)
	{
		if(!otherAcct.checkPassword(pswd))
		{
			message += "\n" + timestamp() + ": Transfer of " + monies.format(amount) + " dollars from account " + otherAcct.accountNum
					+ " unsuccessful. Please check password and try again.";
			return 0;
		}
		
		if(!otherAcct.withdraw(amount))
		{
			message += "\n" + timestamp() + ": Transfer of " + monies.format(amount) + " dollars from account " + otherAcct.accountNum
					+ " unsuccessful due to insufficient funds. A fee of $25 was applied.";
			return 1;
		}
		
		if(!deposit(amount))
		{
			message += "\n" + timestamp() + ": Transfer of " + monies.format(amount) + " dollars from account " + otherAcct.accountNum + " incomplete due to invalid amount.";
			return 2;
		}
		
		message += "\n" + timestamp() + ": Transfer of " + monies.format(amount) + " dollars from account " + otherAcct.accountNum + " complete.";
		return 3;
	}
	
	//Resets password
	boolean resetPassword(String currentPassword, String newPassword)
	{
		if(currentPassword.equals(password))
		{
			password = newPassword;
			message += "\n" + timestamp() + ": Password successfully changed.";
			return true;
		}
		message += "\n" + timestamp() + ": Password Reset Unsuccessful. Please try again.";
		return false;
	}

	//Displays password
	String getPassword()
	{
		return password;
	}
	
	//Check to see if they entered the right password
	boolean checkPassword(String pswd)
	{
		if(pswd.equals(password))
		{
			return true;
		}
		return false;
	}
	
	//Check to see if they entered the right Account Number
	boolean checkAccountNum(int num)
	{
		if(accountNum == num)
		{
			return true;
		}
		return false;
	}
	
	//Message is history of account transactions and stuff
	String getMessage()
	{
		return message;
	}
	
	void updateMessage(String msg)
	{
		message = msg;
	}
	//Takes out all money in account
	void emptyAccount()
	{
		balance = 0;
		message += "\n" + timestamp() + ": So much for \"Leaving something for a rainy day!\"";
	}
	
	String getFirstName()
	{
		return firstName;
	}
	
	String getMiddleName()
	{
		return middleName;
	}
	
	String getLastName()
	{
		return lastName;
	}
	
	static String timestamp()
	{
		Date date= new Date();
	
		long time = date.getTime();
	 
		Timestamp ts = new Timestamp(time);
		return ts.toString();
	}
	
	public static void main (String [] args)
	{	
		
	}
	
}
