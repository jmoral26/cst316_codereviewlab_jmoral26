/*
  File:	ServerSolution.java
  Author:	Julian Morales
  Date:	2/7/2016
  
  Description: The ServerSolution is class is used to manage accounts.
*/


/**
  Class:	ServerSolution.java
  
  Description: This account checks for errors when manipulating files.
*/
package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.State;

class ServerSolution implements AccountServer {

	private static String fileName = "accounts.ser";

	private Map<String,Account> accountMap = null;

    /**
    Method: ServerSolution()
    Inputs:  none
    Returns: none

    Description: A method for starting up the ServerSolution
  */
	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					if (acc != null){
						accountMap.put(acc.getName(), acc);
					}
				}
			}
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		finally {
			if (in != null) {
				try {
					in.close();
				} 
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	 /**
    Method: newAccountFactory
    Inputs: String type, String name, float balance
    Returns: boolean

    Description: A method for creating a new account factory
  */
	private boolean newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {
		
		if (accountMap.get(name) != null){
			return false;
		}
		
		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} 
		else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} 
		else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			accountMap.put(acc.getName(), acc);
		} 
		catch (Exception exc) {
			return false;
		}
		return true;
	}
	 /**
    Method: newAccount
    Inputs: String type, String name, float balance
    Returns: boolean

    Description: A method for creating a new account
  */
	public boolean newAccount(String type, String name, float balance) 
		throws IllegalArgumentException {
		
		if (balance < 0.0f){
			throw new IllegalArgumentException("New account may not be started with a negative balance");
		}
		List<Account> accounts = getAllAccounts();
		for(int i = 0; i < accounts.size(); i++)
		{
			if(accounts.get(i).getName() == name){return false;}
		}
		return newAccountFactory(type, name, balance);
	}
	 /**
    Method: closeAccount
    Inputs: String name
    Returns: boolean

    Description: A method for closing an account
  */
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(State.CLOSED);
		return true;
	}

	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc.getState() != State.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}
	 /**
    Method: saveAccounts
    Inputs: none
    Returns: none

    Description: A method for saving accounts it checks for possible errors.
  */
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));

			out.writeObject(Integer.valueOf(accountMap.size()));
			for (int i=0; i < accountMap.size(); i++) {
				out.writeObject(accountMap.get(i));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + fileName);
		} 
		finally {
			if (out != null) {
				try {
					out.close();
				} 
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

}