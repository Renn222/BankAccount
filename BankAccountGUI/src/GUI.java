import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.CardLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

/*PROBLEMS NEEDED TO FIX. 
 * CURRENT:
 * IDK maybe i'm done?
 */
public class GUI {
	BankAccount ob;	
	ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
	
	static int numBankAccount = -1;
	private JFrame frmAtm;
	CardLayout cards = new CardLayout(0, 0);
	private JPasswordField passwordField;
	private JTextField accountNumberText;
	private JTextField amountNumber;
	private JTextField withdrawAmount;
	private JTextField accountNumberText_1;
	private JTextField amountText;
	private JTextField accountNumberText_2;
	private JTextField amountText_1;
	private JPasswordField passwordTransferFrom;
	private JPasswordField passwordFieldNew;
	private JPasswordField passwordFieldCurrent;
	private JTextPane txtpnProfile;
	private JTextPane txtpnHistory;
	private NumberFormat monies = NumberFormat.getCurrencyInstance();
	private JTextField firstNameText;
	private JTextField middleNameText;
	private JTextField lastNameText;	
	private JTextField initialDepositText;
	private JPasswordField passwordText;

		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI window = new GUI();
					window.frmAtm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAtm = new JFrame();
		frmAtm.setTitle("ATM");
		frmAtm.setBounds(100, 100, 450, 300);
		frmAtm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAtm.getContentPane().setLayout(cards);
		
		numBankAccount = TalkToPHP.getAccountsCreated(frmAtm);
		
		//LOGIN PANEL
		JPanel loginJPanel = new JPanel();
		frmAtm.getContentPane().add(loginJPanel, "LOGIN");
		loginJPanel.setLayout(null);
		
		JButton loginJButton = new JButton("Login");
		loginJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				ob = TalkToPHP.login(accountNumberText, passwordField, frmAtm);
			
				if(ob != null)
				{
					cards.show(frmAtm.getContentPane(), "MAIN");
					passwordField.setText("");
					accountNumberText.setText("");
					String msg = TalkToPHP.getHistory(frmAtm, ob);
					ob.updateMessage(msg);
				}
			}
				
		});
		loginJButton.setBounds(141, 140, 131, 23);
		loginJPanel.add(loginJButton);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(10, 108, 106, 28);
		loginJPanel.add(lblPassword);
		
		JLabel lblAccountNumber = new JLabel("Account Number");
		lblAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAccountNumber.setBounds(10, 77, 169, 23);
		loginJPanel.add(lblAccountNumber);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 110, 131, 24);
		loginJPanel.add(passwordField);
		
		accountNumberText = new JTextField();
		accountNumberText.setBounds(141, 76, 131, 24);
		loginJPanel.add(accountNumberText);
		accountNumberText.setColumns(10);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				cards.show(frmAtm.getContentPane(), "REGISTER");
			}
		});
		btnNewButton.setBounds(141, 174, 131, 23);
		loginJPanel.add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnExit.setBounds(141, 209, 131, 25);
		loginJPanel.add(btnExit);
		
		
		
		//MAIN PANEL
		JPanel mainJPanel = new JPanel();
		frmAtm.getContentPane().add(mainJPanel, "MAIN");
		mainJPanel.setLayout(null);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				cards.show(frmAtm.getContentPane(), "DEPOSIT");
			}
		});
		btnDeposit.setBounds(29, 48, 162, 23);
		mainJPanel.add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "WITHDRAW");
			}
		});
		btnWithdraw.setBounds(236, 48, 162, 23);
		mainJPanel.add(btnWithdraw);
		
		JButton btnProfile = new JButton("Profile");
		btnProfile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtpnProfile.setText(ob.profile());
				cards.show(frmAtm.getContentPane(), "PROFILE");
				
			}
		});
		btnProfile.setBounds(29, 96, 162, 23);
		mainJPanel.add(btnProfile);
		
		JButton btnTranferTo = new JButton("Tranfer To");
		btnTranferTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "TRANSFER_TO");
			}
		});
		btnTranferTo.setBounds(29, 144, 162, 23);
		mainJPanel.add(btnTranferTo);
		
		JButton btnTransferFrom = new JButton("Transfer From");
		btnTransferFrom.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "TRANSFER_FROM");
			}
		});
		btnTransferFrom.setBounds(236, 144, 162, 23);
		mainJPanel.add(btnTransferFrom);
		
		JButton btnResetPassword = new JButton("Change Password");
		btnResetPassword.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "CHANGE_PASSWORD");
			}
		});
		btnResetPassword.setBounds(29, 194, 162, 23);
		mainJPanel.add(btnResetPassword);
		
		JButton btnEmptyAccount = new JButton("Empty Account");
		btnEmptyAccount.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "EMPTY_ACCOUNT");
				ob.withdraw(ob.getBalance());
				TalkToPHP.updateBalance(ob, frmAtm);
			}
		});
		btnEmptyAccount.setBounds(236, 96, 162, 23);
		mainJPanel.add(btnEmptyAccount);
		
		JButton btnHistory = new JButton("History");
		btnHistory.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "HISTORY");
				txtpnHistory.setText("History:\n" + ob.getMessage());
			}
		});
		btnHistory.setBounds(236, 194, 162, 23);
		mainJPanel.add(btnHistory);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				TalkToPHP.updateHistory(ob, frmAtm, ob.getMessage());
				cards.show(frmAtm.getContentPane(), "LOGIN");
			}
		});
		btnLogOut.setBounds(142, 228, 131, 23);
		mainJPanel.add(btnLogOut);
		
		
		
		//DEPOSIT PANEL
		JPanel depositJPanel = new JPanel();
		frmAtm.getContentPane().add(depositJPanel, "DEPOSIT");
		depositJPanel.setLayout(null);
				
		JLabel lblDepositAmount = new JLabel("Deposit Amount");
		lblDepositAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDepositAmount.setBounds(88, 97, 141, 25);
		depositJPanel.add(lblDepositAmount);
		
		amountNumber = new JTextField();
		amountNumber.setText("");
		amountNumber.setBounds(191, 100, 141, 20);
		depositJPanel.add(amountNumber);
		amountNumber.setColumns(10);
	
		
		JButton btnConfirmDeposit = new JButton("Confirm");
		btnConfirmDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try 
				{
					double amt = Double.parseDouble(amountNumber.getText());
					if(ob.deposit(amt))
					{
						JOptionPane.showMessageDialog(frmAtm, "Deposit Amount: " + monies.format(amt), "Success", JOptionPane.PLAIN_MESSAGE);
						TalkToPHP.updateBalance(ob, frmAtm);
					}
					else
					{
						JOptionPane.showMessageDialog(frmAtm, "Invalid Number", "Error", JOptionPane.ERROR_MESSAGE);

					}
				}
				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(frmAtm, "Amount must be a number", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnConfirmDeposit.setBounds(129, 160, 131, 23);
		depositJPanel.add(btnConfirmDeposit);
		
		JButton btnBackDeposit = new JButton("Back");
		btnBackDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
				amountNumber.setText("");
			}
		});
		btnBackDeposit.setBounds(129, 194, 131, 23);
		depositJPanel.add(btnBackDeposit);
		
		
		
		//WITHDRAW PANEL
		JPanel withdrawJPanel = new JPanel();
		frmAtm.getContentPane().add(withdrawJPanel, "WITHDRAW");
		withdrawJPanel.setLayout(null);
		
		JLabel lblWithdrawAmount = new JLabel("Withdraw Amount");
		lblWithdrawAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWithdrawAmount.setBounds(70, 97, 141, 25);
		withdrawJPanel.add(lblWithdrawAmount);
		
		withdrawAmount = new JTextField();
		withdrawAmount.setText("");
		withdrawAmount.setBounds(191, 100, 141, 20);
		withdrawJPanel.add(withdrawAmount);
		withdrawAmount.setColumns(10);
		
		JButton btnConfirmWithdraw = new JButton("Confirm");
		btnConfirmWithdraw.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{					
					double amt = Double.parseDouble(withdrawAmount.getText());
					if(ob.withdraw(amt))
					{						
						JOptionPane.showMessageDialog(frmAtm, "Withdraw Amount: " + monies.format(amt), "Success", JOptionPane.PLAIN_MESSAGE);
						TalkToPHP.updateBalance(ob, frmAtm);
					}
					else
					{
						JOptionPane.showMessageDialog(frmAtm, "Not Enough Funds. Overdraft fees have been applied", "Error", JOptionPane.ERROR_MESSAGE);
					}
				
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(frmAtm, "Amount must be a number", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnConfirmWithdraw.setBounds(129, 160, 131, 23);
		withdrawJPanel.add(btnConfirmWithdraw);
		
		JButton btnBackWithdraw = new JButton("Back");
		btnBackWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
				amountNumber.setText("");
				withdrawAmount.setText("");
			}
		});
		btnBackWithdraw.setBounds(129, 194, 131, 23);
		withdrawJPanel.add(btnBackWithdraw);
		
				
		
		//EMPTY ACCOUNT PANEL
		JPanel emptyAccountJPanel = new JPanel();
		frmAtm.getContentPane().add(emptyAccountJPanel, "EMPTY_ACCOUNT");
		emptyAccountJPanel.setLayout(null);
		
		JTextPane txtpnEmptyAccount = new JTextPane();
		txtpnEmptyAccount.setBackground(UIManager.getColor("Panel.background"));
		txtpnEmptyAccount.setBounds(141, 105, 366, 20);
		txtpnEmptyAccount.setText("Your Account is now empty");
		emptyAccountJPanel.add(txtpnEmptyAccount);
		
		JButton btnBackEmptyAccount = new JButton("Back");
		btnBackEmptyAccount.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
			}
		});
		btnBackEmptyAccount.setBounds(149, 198, 131, 23);
		emptyAccountJPanel.add(btnBackEmptyAccount);
		
		
		
		//TRANSFER TO PANEL
		JPanel transferToJPanel = new JPanel();
		frmAtm.getContentPane().add(transferToJPanel, "TRANSFER_TO");
		transferToJPanel.setLayout(null);
		
		JLabel lblAccountNumber_1 = new JLabel("Account Number");
		lblAccountNumber_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAccountNumber_1.setBounds(26, 75, 110, 22);
		transferToJPanel.add(lblAccountNumber_1);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAmount.setBounds(26, 108, 89, 27);
		transferToJPanel.add(lblAmount);
		
		accountNumberText_1 = new JTextField();
		accountNumberText_1.setText("");
		accountNumberText_1.setBounds(146, 77, 127, 20);
		transferToJPanel.add(accountNumberText_1);
		accountNumberText_1.setColumns(10);
		
		amountText = new JTextField();
		amountText.setText("");
		amountText.setBounds(146, 112, 127, 20);
		transferToJPanel.add(amountText);
		amountText.setColumns(10);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					double amt = Double.parseDouble(amountText.getText());
					int accNum = Integer.parseInt(accountNumberText_1.getText());
					BankAccount n = TalkToPHP.getUserInfo(frmAtm, accNum);
					
					if(n == null)
					{
						JOptionPane.showMessageDialog(frmAtm, "Invalid Account Number", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else if(ob.transferTo(amt, n))
					{
						JOptionPane.showMessageDialog(frmAtm, "Transfer Amount: "+ monies.format(amt), "Success", JOptionPane.PLAIN_MESSAGE);
						TalkToPHP.updateBalance(n, frmAtm);
						TalkToPHP.updateBalance(ob, frmAtm);
					}
					else
					{
						JOptionPane.showMessageDialog(frmAtm, "Insufficent funds, Overdraft fees have been applied", "Error", JOptionPane.ERROR_MESSAGE);
						TalkToPHP.updateBalance(ob, frmAtm);
					}
					
				}
				catch (NumberFormatException e1) 
				{ 
					//e.printStackTrace();
					JOptionPane.showMessageDialog(frmAtm, "Account Number and Amount must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirm.setBounds(146, 157, 131, 23);
		transferToJPanel.add(btnConfirm);
		
		JButton btnBackTransferTo = new JButton("Back");
		btnBackTransferTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
				amountNumber.setText("");
				accountNumberText_1.setText("");
				amountText.setText("");
			}
		});
		btnBackTransferTo.setBounds(146, 194, 131, 23);
		transferToJPanel.add(btnBackTransferTo);
		
		
		
		//TRANSFER FROM PANEL
		JPanel transferFromJPanel = new JPanel();
		frmAtm.getContentPane().add(transferFromJPanel, "TRANSFER_FROM");
		transferFromJPanel.setLayout(null);
		
		JLabel lblAccountNumber_2 = new JLabel("Account Number");
		lblAccountNumber_2.setBounds(26, 45, 110, 22);
		lblAccountNumber_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		transferFromJPanel.add(lblAccountNumber_2);
		
		JLabel lblAmount_1 = new JLabel("Amount");
		lblAmount_1.setBounds(26, 108, 89, 27);
		lblAmount_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		transferFromJPanel.add(lblAmount_1);
		
		accountNumberText_2 = new JTextField();
		accountNumberText_2.setBounds(146, 46, 127, 20);
		accountNumberText_2.setColumns(10);
		transferFromJPanel.add(accountNumberText_2);
		
		amountText_1 = new JTextField();
		amountText_1.setBounds(146, 112, 127, 20);
		amountText_1.setColumns(10);
		transferFromJPanel.add(amountText_1);
		
		passwordTransferFrom = new JPasswordField();
		passwordTransferFrom.setBounds(146, 77, 127, 20);
		transferFromJPanel.add(passwordTransferFrom);
		passwordTransferFrom.setColumns(10);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword_1.setBounds(26, 78, 89, 14);
		transferFromJPanel.add(lblPassword_1);
		
		JButton btnConfirm_1 = new JButton("Confirm");
		btnConfirm_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					double amt = Double.parseDouble(amountText_1.getText());
					int accNum = Integer.parseInt(accountNumberText_2.getText());
					BankAccount n = TalkToPHP.getUserInfo(frmAtm, accNum);
					int num = ob.transferFrom(amt, n, String.valueOf(passwordTransferFrom.getPassword()));
						
					if(n == null)
					{
						JOptionPane.showMessageDialog(frmAtm, "Invalid Account Number", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else if(num == 0)
					{
						JOptionPane.showMessageDialog(frmAtm, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else if(num == 1)
					{
						JOptionPane.showMessageDialog(frmAtm, "Insufficient Money for Withdrawal. Overdraft fee has been applied", "Error", JOptionPane.ERROR_MESSAGE);
						TalkToPHP.updateBalance(n, frmAtm);
					}
					
					else if(num == 2)
					{
						JOptionPane.showMessageDialog(frmAtm, "Account Number must be a positive number", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else if(num == 3)
					{
						JOptionPane.showMessageDialog(frmAtm, "Tranfer Amount: " + monies.format(amt), "Success", JOptionPane.ERROR_MESSAGE);
						
						TalkToPHP.updateBalance(n, frmAtm);
						TalkToPHP.updateBalance(ob, frmAtm);
					}
				}
				
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(frmAtm, "Amount and Account Number must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}	
		});
		btnConfirm_1.setBounds(146, 157, 131, 23);
		transferFromJPanel.add(btnConfirm_1);
		
		JButton btnBackTransferFrom = new JButton("Back");
		btnBackTransferFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
				amountText_1.setText("");
				accountNumberText_2.setText("");
				passwordTransferFrom.setText("");
			}
		});
		btnBackTransferFrom.setBounds(146, 191, 131, 23);
		transferFromJPanel.add(btnBackTransferFrom);
		
		
				
		//CHANGE PASSWORD PANEL
		JPanel changePasswordJPanel = new JPanel();
		frmAtm.getContentPane().add(changePasswordJPanel, "CHANGE_PASSWORD");
		changePasswordJPanel.setLayout(null);
		
		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCurrentPassword.setBounds(26, 75, 110, 22);
		changePasswordJPanel.add(lblCurrentPassword);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewPassword.setBounds(26, 116, 110, 22);
		changePasswordJPanel.add(lblNewPassword);
		
		passwordFieldNew = new JPasswordField();
		passwordFieldNew.setBounds(146, 118, 131, 20);
		changePasswordJPanel.add(passwordFieldNew);
		
		passwordFieldCurrent = new JPasswordField();
		passwordFieldCurrent.setBounds(146, 77, 131, 20);
		changePasswordJPanel.add(passwordFieldCurrent);
		
		JButton btnConfirmNewPassword = new JButton("Confirm");
		btnConfirmNewPassword.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(ob.resetPassword(String.valueOf(passwordFieldCurrent.getPassword()), String.valueOf(passwordFieldNew.getPassword())))
				{
					JOptionPane.showMessageDialog(frmAtm, "Password Changed", "Success", JOptionPane.PLAIN_MESSAGE);
					TalkToPHP.updatePassword(ob, frmAtm);					
				}
				else
				{
					JOptionPane.showMessageDialog(frmAtm, "Current Password is invalid", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnConfirmNewPassword.setBounds(146, 163, 131, 23);
		changePasswordJPanel.add(btnConfirmNewPassword);
		
		JButton btnBackChangePassword = new JButton("Back");
		btnBackChangePassword.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
				passwordFieldNew.setText("");
				passwordFieldCurrent.setText("");
			}
		});
		btnBackChangePassword.setBounds(146, 200, 131, 23);
		changePasswordJPanel.add(btnBackChangePassword);
		
		
		
		//HISTORY PANEL
		JPanel historyJPanel = new JPanel();
		frmAtm.getContentPane().add(historyJPanel, "HISTORY");
		historyJPanel.setLayout(null);
		
		txtpnHistory = new JTextPane();
		txtpnHistory.setBackground(UIManager.getColor("Panel.background"));
		txtpnHistory.setBounds(10, 53, 414, 135);
		historyJPanel.add(txtpnHistory);
		
		JButton btnBackHistory = new JButton("Back");
		btnBackHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
			}
		});
		btnBackHistory.setBounds(152, 199, 131, 23);
		historyJPanel.add(btnBackHistory);
		
		
		
		//PROFILE PANEL
		JPanel profileJPanel = new JPanel();
		frmAtm.getContentPane().add(profileJPanel, "PROFILE");
		profileJPanel.setLayout(null);
		
		txtpnProfile = new JTextPane();
		txtpnProfile.setBackground(UIManager.getColor("Panel.background"));
		txtpnProfile.setBounds(10, 11, 414, 176);
		profileJPanel.add(txtpnProfile);

		JButton btnBackProfile = new JButton("Back");
		btnBackProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "MAIN");
			}
		});
		btnBackProfile.setBounds(143, 198, 131, 23);
		profileJPanel.add(btnBackProfile);
		
		
		
		//REGISTER PANEL
		JPanel registerJPanel = new JPanel();
		frmAtm.getContentPane().add(registerJPanel, "REGISTER");
		registerJPanel.setLayout(null);
		
		JLabel lblInitialAmount = new JLabel("Initial Deposit      $");
		lblInitialAmount.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblInitialAmount.setBounds(23, 33, 120, 21);
		registerJPanel.add(lblInitialAmount);
		
		JLabel lblPasswordRegister = new JLabel("Password");
		lblPasswordRegister.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPasswordRegister.setBounds(23, 66, 89, 21);
		registerJPanel.add(lblPasswordRegister);
		
		JLabel lblFirstNameRegister = new JLabel("First Name");
		lblFirstNameRegister.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFirstNameRegister.setBounds(23, 99, 89, 21);
		registerJPanel.add(lblFirstNameRegister);
		
		JLabel lblMiddleNameRegister = new JLabel("Middle Name");
		lblMiddleNameRegister.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMiddleNameRegister.setBounds(23, 131, 89, 21);
		registerJPanel.add(lblMiddleNameRegister);
		
		JLabel lblLastNameRegister = new JLabel("Last Name");
		lblLastNameRegister.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLastNameRegister.setBounds(23, 162, 89, 21);
		registerJPanel.add(lblLastNameRegister);
		
		initialDepositText = new JTextField();
		initialDepositText.setFont(new Font("Dialog", Font.PLAIN, 12));
		initialDepositText.setColumns(10);
		initialDepositText.setBounds(144, 37, 131, 20);
		registerJPanel.add(initialDepositText);
		
		firstNameText = new JTextField();
		firstNameText.setFont(new Font("Dialog", Font.PLAIN, 12));
		firstNameText.setBounds(144, 101, 131, 20);
		registerJPanel.add(firstNameText);
		firstNameText.setColumns(10);
		
		middleNameText = new JTextField();
		middleNameText.setBounds(144, 132, 131, 20);
		registerJPanel.add(middleNameText);
		middleNameText.setColumns(10);
		
		lastNameText = new JTextField();
		lastNameText.setBounds(144, 163, 131, 20);
		registerJPanel.add(lastNameText);
		lastNameText.setColumns(10);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(144, 67, 131, 20);
		registerJPanel.add(passwordText);
		
		JButton btnConfirmRegister = new JButton("Confirm");
		btnConfirmRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					int accNumUnique = BankAccount.getNewAccountNum();
															
					//Next 5 if statements are checking if registration JTextFields are empty
					if(initialDepositText.getText().equals(""))
					{
						JOptionPane.showMessageDialog(frmAtm, "Please choose an amount to deposit", "Registration Error", JOptionPane.ERROR_MESSAGE);
					}
					
					if(String.valueOf(passwordText.getPassword()).equals(""))
					{
						JOptionPane.showMessageDialog(frmAtm, "Please write a password", "Registration Error", JOptionPane.ERROR_MESSAGE);
					}
					
					if(firstNameText.getText().equals(""))
					{
						JOptionPane.showMessageDialog(frmAtm, "Please write a first name", "Registration Error", JOptionPane.ERROR_MESSAGE);
					}
					
					if(middleNameText.getText().equals(""))
					{
						JOptionPane.showMessageDialog(frmAtm, "Please write a middle name", "Registration Error", JOptionPane.ERROR_MESSAGE);
					}
					
					if(lastNameText.getText().equals(""))
					{
						JOptionPane.showMessageDialog(frmAtm, "Please write a last name", "Registration Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						BankAccount b = new BankAccount(accNumUnique, Double.parseDouble(initialDepositText.getText()), String.valueOf(passwordText.getPassword()),
								firstNameText.getText(), middleNameText.getText(),lastNameText.getText(), BankAccount.timestamp() + ": Account Created");
						numBankAccount++;
						b.updateMessage(BankAccount.timestamp() + ": Account Created");
						if(TalkToPHP.uploadAcc(b, frmAtm))
						{
							//Creates new JTextField because I can only use login with JTextField not int
							JTextField accNum = new JTextField();
							accNum.setText(String.valueOf(accNumUnique));
							
							//Tells the use what their account number is
							JOptionPane.showMessageDialog(frmAtm, "Account Number: " + b.getAccountNum() + " Remember this, you will need it to login", "Message", JOptionPane.PLAIN_MESSAGE);
							
							//Logs them in
							ob = TalkToPHP.login(accNum, passwordText, frmAtm);
							cards.show(frmAtm.getContentPane(), "MAIN");
							String msg = TalkToPHP.getHistory(frmAtm, ob);
							ob.updateMessage(msg);
							
						}
						else
						{
							numBankAccount--;
						}
					}
					
				}
				catch(java.lang.NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(frmAtm, "Your Deposit must be a number", "Registration Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnConfirmRegister.setBounds(144, 194, 131, 23);
		registerJPanel.add(btnConfirmRegister);
		
		JButton btnBackRegister = new JButton("Back");
		btnBackRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cards.show(frmAtm.getContentPane(), "LOGIN");
				
				initialDepositText.setText("");
				passwordText.setText("");
				firstNameText.setText("");
				middleNameText.setText("");
				lastNameText.setText("");
				passwordField.setText("");
				accountNumberText.setText("");
			}
		});
		btnBackRegister.setBounds(144, 228, 131, 23);
		registerJPanel.add(btnBackRegister);
	}
}
