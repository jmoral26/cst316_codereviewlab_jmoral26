/*
  File:	AccountServerFactory.java
  Author: Julian Morales	
  Date:	 2/7/2016
  
  Description: This account is responsible for getting the singleton
*/
/**
  Class:	AccountServerFactory.java
  
  Description: This account is responsible for getting the singleton.
*/
package banking.primitive.core;


public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}
	/**
	  Method: lookup()
	  Inputs: none
	  Returns: none

	  Description: gets a new server solution.
	*/

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
