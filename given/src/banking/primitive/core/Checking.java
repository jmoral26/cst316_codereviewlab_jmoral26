/*
  File:	Checking.java
  Author: Julian Morales	
  Date:	 2/7/2016
  
  Description: The checkings class manges all checking accounts.
*/
/**
  Class:	Checking.java
  
  Description: This class manages the checkings account.
*/
package banking.primitive.core;

import banking.primitive.core.Account.State;

public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	private int numWithdraws = 0;
	
	private Checking(String name) {
		super(name);
	}
	/**
	  Method: createChecking()
	  Inputs: String name
	  Returns: none

	  Description: Method for creating checkings account
	*/

    public static Checking createChecking(String name) {
        return new Checking(name);
    }
    /**
    Method: Checking()
    Inputs: string name, float balance
    Returns: none

    Description: It creates a checking account with a name and balance
  */

	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	/**
	  Method: deposit
	  Inputs: float amount
	  Returns: boolean

	  Description: The deposit method is used to add money to a checkings account.
	*/

	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount;
			if (balance >= 0.0f) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	/**
	  Method: withdraw()
	  Inputs: float amount
	  Returns: boolean

	  Description: withdraw is used to get money from an account
	*/

	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == State.OPEN || (getState() == State.OVERDRAWN && balance > -100.0f)) {
				balance = balance - amount;
				numWithdraws++;
				if (numWithdraws > 10){
					balance = balance - 2.0f;
				}
				if (balance < 0.0f) {
					setState(State.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}
	
	
	

	public String getType() { 
		return "Checking"; 
	}
	/**
	  Method: toString()
	  Inputs: none
	  Returns: String

	  Description: This method is used to get a string that is printable.
	*/

	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}