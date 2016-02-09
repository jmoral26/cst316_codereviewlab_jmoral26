/*
  File:	Savings.java
  Author: Julian Morales	
  Date:	 2/7/2016
  
  Description: The Savings class is in charge of managing all savings accounts
*/
/**
  Class:	Savings.java
  
  Description: This class manges the savings accounts.
*/
package banking.primitive.core;

public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	private int numWithdraws = 0;

	/**
	  Method: Savings
	  Inputs: String name
	  Returns: none

	  Description: A method for making a Savings account
	*/

	private Savings(String name) {
		super(name);
	}

	/**
	  Method: Savings
	  Inputs: String name, float balance
	  Returns:

	  Description:
	*/

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	/**
	  Method: deposit
	  Inputs: float amount
	  Returns: boolean

	  Description: A method for depositing money in a Savings account
	*/

	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount - 0.50F;
			if (balance >= 0.0f) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	/**
	  Method: withdraw
	  Inputs: float amount
	  Returns: boolean

	  Description: A method for withdrawing money from a savings account.
	*/

	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > 0.0f) {
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > 3){
				balance = balance - 1.0f;
			// KG BVA: should be < 0
			}
			if (balance <= 0.0f) {
				setState(State.OVERDRAWN);
			}
			//woops previously fixed this
			return true;
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

    Description: This method is used to convert the information into a readable printable state.
  */
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}