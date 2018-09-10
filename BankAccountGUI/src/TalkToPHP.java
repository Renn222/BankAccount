import java.net.URLEncoder;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

public class TalkToPHP 
{
	private static String url = "https://test5665441.000webhostapp.com";
	
	static boolean uploadAcc(BankAccount ob, JFrame frmAtm)
	{
		
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{
			http.sendPost(url+"/php/uploadAcc.php?", 
					   "accNum=" + ob.getAccountNum() +
					   "&balance=" + ob.getBalance() +
					   "&password=" + ob.getPassword() + 
					   "&first_name=" + ob.getFirstName() +
					   "&middle_name=" + ob.getMiddleName() +
					   "&last_name=" + ob.getLastName() +
					   "&history=" + ob.getMessage());
				
			if (http.response != null)
			{
				if(http.response.toString().trim().equals("Account Number not Unique"))
				{
					int accNumUnique = BankAccount.getNewAccountNum();
					BankAccount.changeAccNum(ob, accNumUnique);
					TalkToPHP.uploadAcc(ob, frmAtm);
				}
				else if(http.response.toString().trim().equals("Account Created"))
				{
					return true;
				}
				else
				{
					// handle php error
					System.out.println("Php Error!\n"+http.response.toString());
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace(); 
			JOptionPane.showMessageDialog(frmAtm, "Server Unreachable.\nPlease try again after checking your Internet connection.", "Network Error", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	static BankAccount login(JTextField accountNumberText, JPasswordField passwordField, JFrame frmAtm)
	{
		BankAccount c = null;
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{
			http.sendPost(url+"/php/login.php?", 
					   "accNum=" + Integer.parseInt(accountNumberText.getText()) + 
					   "&password=" + String.valueOf(passwordField.getPassword()));
			
			if (http.response != null)
			{
				if (http.response.toString().trim().equals("Login successful"))
				{
					// handle success!
					c = getUserInfo(frmAtm, Integer.parseInt(accountNumberText.getText()));
					return c;
				}
				else if (http.response.toString().trim().equals("Login Failed"))
				{
					// handle login failed!
					JOptionPane.showMessageDialog (frmAtm, "Incorrect username/password.", "Please try again.", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					// handle php error
					System.out.println("Php Error!\n"+http.response.toString());
				}
			}
		}
		catch(java.lang.NumberFormatException e1)
		{
			JOptionPane.showMessageDialog(frmAtm, "Account Number must be an Integer", "Login Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) 
		{ 
			//e.printStackTrace();
			JOptionPane.showMessageDialog(frmAtm, "Server Unreachable.\nPlease try again after checking your Internet connection.", "Network Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
		
	static BankAccount getUserInfo(JFrame frmAtm, int accNum)
	{
		BankAccount d = null;
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{
			http.sendPost(url+"/php/getUserInfo.php?", 
						   "accNum=" + accNum);			
			if(http.response.toString().trim().equals("No account number submitted"))
			{
				JOptionPane.showMessageDialog(frmAtm, "Account number not submitted", "Error", JOptionPane.ERROR_MESSAGE);
			}
				
			else if(http.response.toString().trim().equals("Please submit an integer"))
			{
				JOptionPane.showMessageDialog(frmAtm, "Account Number is not an Integer", "Error", JOptionPane.ERROR_MESSAGE);
			}
				
			else if(http.response.toString().trim().equals("Match not found. Query returned null"))
			{
				JOptionPane.showMessageDialog(frmAtm, "No match found", "Error", JOptionPane.ERROR_MESSAGE);
			}
				
			else
			{
				// retrieves entire row
				d = parseResult(http.response.toString());
			}
					
		} 
		
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
		}
		return d;
	}	

	static BankAccount parseResult(String result)
	{	
		try 
		{
			System.out.println(result);
			if (result!=null && !result.equals("[]") && !result.equals("") && result.startsWith("{"))
			{
				JSONObject obj = new JSONObject(result);
				BankAccount ob = new BankAccount(obj.getInt("account_number"), obj.getDouble("balance"),obj.getString("password"), 
						obj.getString("first_name"), obj.getString("middle_name"), obj.getString("last_name"), obj.getString("history"));
				return ob;
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	static void updateBalance(BankAccount ob, JFrame frmAtm)
	{
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{			
			http.sendPost(url+"/php/updateBalance.php?", 
					   "accNum=" + ob.getAccountNum() +
					   "&balance=" + ob.getBalance());
			
			if (http.response != null)
			{
				if(http.response.toString().trim().equals("Balance Updated"))
				{
				}
				else if(http.response.toString().trim().equals("Account number not found"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Wrong Account Number used", "System Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(http.response.toString().trim().equals("Balance must == double && accNum must == int"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Balance must be a double and account number must be an integer", "System Error", JOptionPane.ERROR_MESSAGE);				
				}
				else
				{
					System.out.println("Php Error!\n"+http.response.toString());
				}
			}
		}
		catch (Exception e) 
		{ 
			//e.printStackTrace();
			JOptionPane.showMessageDialog(frmAtm, "Server Unreachable.\nPlease try again after checking your Internet connection.", "Network Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	static void updatePassword(BankAccount ob, JFrame frmAtm)
	{
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{			
			http.sendPost(url+"/php/updatePassword.php?", 
					   "accNum=" + ob.getAccountNum() +
					   "&password=" + ob.getPassword());
			
			if (http.response != null)
			{
				if(http.response.toString().trim().equals("Password Updated"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Password Change Successful", "Success", JOptionPane.PLAIN_MESSAGE);
				}
				else if(http.response.toString().trim().equals("Account number not found"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Wrong Account Number used", "System Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					System.out.println("Php Error!\n"+http.response.toString());
				}
			}
		}
		catch (Exception e) 
		{ 
			//e.printStackTrace();
			JOptionPane.showMessageDialog(frmAtm, "Server Unreachable.\nPlease try again after checking your Internet connection.", "Network Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	static void updateHistory(BankAccount ob, JFrame frmAtm, String message)
	{
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{			
			http.sendPost(url+"/php/updateHistory.php?", 
					   "accNum=" + ob.getAccountNum() +
					   "&history=" + message);
			
			if (http.response != null)
			{
				if(http.response.toString().trim().equals("History Updated"))
				{
					JOptionPane.showMessageDialog(frmAtm, "History Change Successful", "Success", JOptionPane.PLAIN_MESSAGE);
				}
				else if(http.response.toString().trim().equals("Account number not found"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Wrong Account Number used", "System Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					System.out.println("Php Error!\n"+http.response.toString());
				}
			}
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
			JOptionPane.showMessageDialog(frmAtm, "Server Unreachable.\nPlease try again after checking your Internet connection.", "Network Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	
	static int getAccountsCreated(JFrame frmAtm)
	{
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{			

			http.sendPost(url+"/php/getNumberAccounts.php?", "");
			if (http.response != null)
			{				
				if(http.response.toString().trim().equals("Match not found. Query returned null"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Match not found. Query returned null", "System Error", JOptionPane.ERROR_MESSAGE);				
				}
				
				else
				{
					int count = parseCount(http.response.toString());

					if(count != -1)
					{
						return count;
					}
					else
					{
						JOptionPane.showMessageDialog(frmAtm, "Number of Accounts not recieved", "System Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
		return 0;
	}
	
	static int parseCount(String result)
	{	
		try 
		{
			if (result!=null && !result.equals("[]") && !result.equals(""))
			{
				JSONObject obj = new JSONObject(result);
				int count = obj.getInt("COUNT(count)");
				return count;
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	static String getHistory(JFrame frmAtm, BankAccount ob)
	{
		BAHttpURLConnection http = new BAHttpURLConnection();
		try 
		{			
			http.sendPost(url+"/php/getHistory.php?", 
					"accNum=" + ob.getAccountNum());
			if (http.response != null)
			{
				if(http.response.toString().trim().equals("Match Not Found. Query returned null"))
				{
					JOptionPane.showMessageDialog(frmAtm, "Match Not Found. Query returned null", "System Error", JOptionPane.ERROR_MESSAGE);				
				}
			
				else
				{
					String history = parseHistory(http.response.toString());
					
					if(!history.equals(null))
					{
						return history;
					}
					else
					{
						JOptionPane.showMessageDialog(frmAtm, "History not recieved", "System Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
		return null;
	}
	
	static String parseHistory(String result)
	{	
		try 
		{
			if (result!=null && !result.equals("[]") && !result.equals("") && result.startsWith("{"))
			{
				JSONObject obj = new JSONObject(result);
				String history = obj.getString("history");
				return history;
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

}
