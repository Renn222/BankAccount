import java.util.Random;

public class BankAccount 
{
	public int accountNum;
	private double balance;
	private String firstName;
	private String middleName;
	private String lastName;
	private String password;
	private String message;
	
	//This is first account
	BankAccount(double amt, String fName, String mName, String lName)
	{	
		Random stuff = new Random();
		accountNum = stuff.nextInt(99999) + 10000;
		
		balance = amt;
		firstName = fName;
		middleName = mName;
		lastName = lName;
		password = firstName.substring(0, 3) + lastName.substring(lastName.length() - 2);
		
		message = "";
		
	}
	
	//This is second account
	BankAccount(String fName, String lName)
	{
		Random stuff = new Random();
		accountNum = stuff.nextInt(99999) + 10000;
		
		firstName = fName;		
		middleName = null;
		lastName = lName;
		
		message = "";
	}
	
	//Deposits money into account and returns True or False
	boolean deposit(double amount)
	{
		if(amount > 0)
		{
			balance += amount;
			message += "\nDeposit of amount $" + amount + " made.";
			return true;
		}
		message += "\nDeposit Unsuccessful: invalid amount.";
		return false;
	}
	
	//Withdraws money and returns True or False
	boolean withdraw(double amount)
	{

		if(balance - amount >= 0)
		{
			balance -= amount;
			message += "\nWithdrawal of amount $" + amount + " made.";
			return true;
		}
		
		//Overdraft fees
		penalty();
		message += "\nA bank overdraft fee of $25 will be charged to your account. We thank you for your business.";
		return false;
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
		System.out.println("\nBalance: $" + balance);
		return balance;
	}
	
	//Displays accountNum, firstName, middleName, lastName and balance
	void display()
	{
		System.out.println("The account number is " + accountNum + ". The account holder's full name is " 
	+ firstName + " " + middleName + " " + lastName 
	+ ". The balance is $ " + balance);
	}
	
	//Transfers money to another account
	boolean transferTo(double amount, BankAccount otherAcct)
	{		
		if(withdraw(amount) == false)
		{
			message += "\n		Overdraft during transfer. Transaction not processed. Please contant us for further assistance. "
					+ "A fee of $25 was applied.";
			return false;
		}
		otherAcct.deposit(amount);
		message += "\n		Transfer of " + amount + " dollars to account " + otherAcct.accountNum + " complete.";
		return true;
		
	}
	
	//Takes money from another account
	boolean transferFrom(double amount, BankAccount otherAcct, String pswd)
	{
		if(otherAcct.checkPassword(pswd) == false)
		{
			message += "\nTransfer of " + amount + " dollars from account " + otherAcct.accountNum
					+ " unsuccessful. Please check password and try again.";
			return false;
		}
		
		if(otherAcct.withdraw(amount) == false)
		{
			message += "\nTransfer of " + amount + " dollars from account " + otherAcct.accountNum
					+ " unsuccessful due to insufficient funds. A fee of $25 was applied.";
			return false;
		}
		
		if(deposit(amount) == false)
		{
			message += "\n		Transfer of " + amount + " dollars from account " + otherAcct.accountNum + " incomplete due to invalid amount.";
			return false;
		}
		
		message += "\n		Transfer of " + amount + " dollars from account " + otherAcct.accountNum + " complete.";
		return true;
	}
	
	//Resets password
	boolean resetPassword(String currentPassword, String newPassword)
	{
		if(currentPassword.equals(password))
		{
			password = newPassword;
			message += "\nPassword successfully changed.";
			return true;
		}
		message += "\nPassword Reset Unsuccessful. Please try again.";
		return false;
	}
	
	//Displays password (not rly)
	void getPassword()
	{
		message += "\nHA! If only it were that easy! ECB will be notified of your attempt to hack us, criminal!";
	}
	
	//Check to see if they entered the right password
	private boolean checkPassword(String pswd)
	{
		if(pswd.equals(password))
		{
			return true;
		}
		return false;
	}
	
	//Message is history of account transactions and stuff
	String getMessage()
	{
		System.out.println(message);
		return message;
	}
	
	//Takes out all money in account
	void emptyAccount()
	{
		balance = 0;
		message += "\nSo much for \"Leaving something for a rainy day!\"";
	}
	
	public static void main (String [] args)
	{
		BankAccount a  = new BankAccount(20, "Jaimin", "ddd", "Nimavat");
		BankAccount b  = new BankAccount(300, "Jaimin", "ddd", "ddd");
		
		a.transferFrom(250.1111111111, b, "Jaidd");
		a.getMessage();
		a.getBalance();
		
	}
	
}
