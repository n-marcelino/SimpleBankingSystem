package com.ciit.cs208

import java.text.BreakIterator

class Main {
	
	static void main(args) {
		
		//CONSTRUCTS A PERSON OBJECT
		boolean personMade = false;
		def x, y, z
		Person p
		while (personMade == false) {
			println "First of all, let's get some basic information about you: "
			print "What is your first name?: "
			x = System.in.newReader().readLine()
			
			print "What is your surname?: "
			y = System.in.newReader().readLine()
			
			print "Enter the amount of money you have in your wallet (in numbers): "
			
			try {
				z = System.in.newReader().readLine() as Double
			} catch (Exception e) {
				println "\nPlease input a valid amount."
			}
			
			if (z != null && z >= 0) {
				p = new Person(x,y,z)
				break
			} else {
				println "\nError: not allowed to have a negative amount of money."
				println "--------------------------\n"
			}
		}
		//LAUNCHES Introduction of Program
		println "--------------------------"
		launchIntro()
		println "Press enter to continue..."
		System.in.newReader().readLine()
		println "--------------------------"
		
		//OUTPUTS WHO THE USER OF THE PROGRAM IS, BASED ON THE PERSON OBJECT CONSTRUCTED ABOVE
		thisIsYou(p)
		
		//------------------------------------
		//----START OF BANKING SYSTEM CODE----
		//------------------------------------
		
		//THE BANK ACCOUNT ARRAYS
		def bankAccounts = []
		
		for (int i = 0; i < 10; i++) {
			bankAccounts.add(i, -1)
			//adds 10 items to bankAccounts[] containing a value of '-1',
			//this will later be replaced with BankAccount Objects
		}
		
		/*
		 * the following is an infinite while loop containing the log-in menu
		 * this will lead to the inner loop which accesses the actual bank transactions
		 * until user "logs out", which will then return them to the infinite log-in menu 
		 * 
		 */
		while(true) {
			int choice 					//used to choose from the switch case
			Boolean loggedIn = false 	//the condition of the inner loop which holds the actual bank transactions
			int whichAccount = -1 		//set to -1, because of the bankAccounts array; our way to check which account is logged in
			def un, pw					//username and password input
			
			println "-----------------------------------------"
			println "-----CIIT BANKING SYSTEM Log-in Menu-----"
			println "-----------------------------------------"
			println "[1] Sign-up"
			println "[2] Log-in"
			
			print "INPUT: "
			
			try {
				choice = System.in.newReader().readLine() as Integer
			} catch (Exception e) {
				println "Invalid input. Please try again."
			}
			
			println "--------------------------"
			
			switch (choice) {
				case 1: //THE FOR LOOP which contains the signing up menu
					for(int i = 0; i < bankAccounts.size(); i++) { 
						if (bankAccounts[i] != -1) {
							// if bankAccounts[i] is no longer equal to -1, that means it contains a BankAccount object already
							continue
						} else {
							println "\nSIGNING UP\n"
							print "Username: "
							un = System.in.newReader().readLine()
							print "Password: "
							pw = System.in.newReader().readLine()
							
							bankAccounts[i] = new BankAccount(un,pw,p)
							// replaces bankAccounts[i] value from -1 to a BankAccount object with a corresponding un and pw
							
							println "\nSuccessfully signed up. You can now log-in using this account!\n"
							println "--------------------------"
							break
						}
					} 
					break
				case 2: 
					println "\nLOGGING IN\n"
					print "Username: "
					un = System.in.newReader().readLine()
					print "Password: "
					pw = System.in.newReader().readLine()
					
					//for loop cycling through the array to check for an account with correct un and pw
					for(int i = 0; i < bankAccounts.size(); i++) {
						//the if-else statement checking for username and password
						if (!(bankAccounts[i] == -1)) { //checks if index is not empty
							if (bankAccounts[i].getUsername() == un) {
								if (bankAccounts[i].getPassword() == pw) {
									loggedIn = true
									whichAccount = i //notes down which bankAccount index is currently logged in; going to be used in the inner while loop
									println "\nSuccessfully logged in!\n"
									break
								} else {
									println "Please check if your password is correct.\n"
									break
								}
							}
						} else { //if for loop hits an empty index (index containing a non-BankAccount object), then it'll throw this
							println "\nNo accounts with that username was found in our system"
							break //breaks immediately, bc if we hit an empty index, then the following indeces are also empty
						}
					}
					break
			}
			
			//THIS IS THE INNER WHILE LOOP THAT CONTAINS THE ACTUAL WHILE TRANSACTIONS
			bankSystem:
			while(loggedIn == true) {
				def owner = bankAccounts[whichAccount].getOwner() //uses the "whichAccount" variable declared earlier to check which account is logged in
				def currentBank = bankAccounts[whichAccount]
				println "-----------------------------------------"
				println "USER ${currentBank.getUsername()} is currently logged in!"
				println "--------------------------"
				println "${owner.getFirstName()} ${owner.getLastName()}'s BANK ACCOUNT ${whichAccount + 1}"
				println "--------------------------"
				//ENTER BANKING SYSTEM HERE
				
				
				
				while(true) {
					println "\nWelcome to CIIT Banking System! How may we help you today?\n"
					
					println "[1] Balance Inquiry"
					println "[2] Deposit  Cash"
					println "[3] Withdraw Cash"
					println "[4] Get a loan"
					println "[5] Pay your bills"
					println "[0] Log-out"
					
					print "INPUT: "
					try {
						choice = System.in.newReader().readLine() as Integer
					} catch (Exception e) {
						println "Invalid input. Please try again."
					}
					
					switch(choice) {
						case 1: //balance inquiry
							println("\nYou have chosen to inquire your balance. \n");
							println("Your current running balance is: " + currentBank.getBalance())
							
							println "\nPress enter to continue..."
							System.in.newReader().readLine()
							println "--------------------------"
							break;
						case 2: //deposit
							println("\nYou have chosen to deposit cash.");
							println("Your current running balance is: " + currentBank.getBalance())
							
							Deposit_Trans(currentBank, p);
							
							println "\nPress enter to continue..."
							System.in.newReader().readLine()
							println "--------------------------"
							break;
						case 3: //withdraw cash
							println"\nYou have chosen to withdraw cash."
							print "Current Running balance is: " + currentBank.getBalance()
							
							Withdraw_Trans(currentBank, p);
							
							println "\nPress enter to continue..."
							System.in.newReader().readLine()
							println "--------------------------"
							break;
						case 4: //get a loan
							println"\nYou have chosen to get a loan."
							print "Current Running balance is: " + currentBank.getBalance()
							
							GetLoan_Trans(currentBank)
							
							println "\nPress enter to continue..."
							System.in.newReader().readLine()
							println "--------------------------"
							break;
						case 5: //pay bills
							println"\nYou have chosen to pay your bills: "
							PayBills_Trans(currentBank)
							println "\nPress enter to continue..."
							System.in.newReader().readLine()
							println "--------------------------"
							break;
						case 0: //log-out
							println "\nThank you for choosing CIIT Banking System."
							println "We await your next transaction :)"
							
							println "\nPress enter to continue..."
							System.in.newReader().readLine()
							println "--------------------------"
							
							thisIsYou(p)
							loggedIn = false;
							break bankSystem;
							break;
						default:
							println "Invalid input, please try again."
							break;
					}
				} 
			}
		}
	}
	
	static void launchIntro() {		
		println "\nWELCOME TO Simple Banking System"
		println "BROUGHT TO YOU BY CS208 Group 9\n"
	}

	static void thisIsYou(Person p) {
		println "--------------------------"
		println "\nTHIS IS YOU: \n"
		println "FIRST NAME: ${p.getFirstName()}"
		println "SURNAME: ${p.getLastName()}"
		println "\nYou currently have an amount of PHP${p.getMoneyInWallet()} in your wallet.\n"
		
		println "Press enter to continue..."
		System.in.newReader().readLine()
		println "--------------------------"
	}
	
	static void Deposit_Trans(BankAccount b, Person p) {
		double input = 0
		try {
			print("\nEnter amount to deposit: ");
			input = System.in.newReader().readLine() as Double
			}
		catch (Exception e){
			println("Please enter numbers only. Try again.");
		}
		
		//checking for if input>=0, if input > walletMoney, if input >= 500000
		if (input > 500000)
			println "Deposit amount must not than 500,000"
		else if (input <= 0)
			println "Deposit Amount must be greater than zero"
		
		else {
			b.setBalance(b.getBalance() + input) //adds money into your bank
			p.setMoneyInWallet(p.getMoneyInWallet() - input)
			
			println "\nPHP" + b.getBalance() + " has been added into your account!"
			println "Deposit Transaction is successfully completed\n"
			
			print "Current Running balance is: " + b.getBalance() + "\n"
		}
	}
	
	static void Withdraw_Trans(BankAccount b, Person p) {
		double input = 0
		try {
			print"\nEnter amount to withdraw: "
			input = System.in.newReader().readLine() as Double
			}
		catch (Exception e){
			println "Please enter numbers only. Try again."
		}
		
		//checking for if input>=0, if input >= bankMoney, if input >= 500000
		if (input > 500000)
			println "Withdraw amount must not exceed 500,000"
		else if (input <= 0)
			println "Withdraw Amount must be greater than zero"
		else if (input > b.getBalance())
			println "You don't have enough money in your bank to withdraw the desired amount."
		else {
			b.setBalance(b.getBalance() - input) //subtracts value from bank
			p.setMoneyInWallet(p.getMoneyInWallet() + input) //adds money into your wallet
			
			println "\nPHP" + input + " has been withdrawn and put into your wallet!"
			println "Withdrawal Transaction is successfully completed\n"
			
			print "Current Running balance is: " + b.getBalance() + "\n"
		}
	}
	
	static void GetLoan_Trans(BankAccount b) {
		double input = 0
		try {
			print"\nEnter amount to borrow: "
			input = System.in.newReader().readLine() as Double
			}
		catch (Exception e){
			println "Please enter numbers only. Try again."
		}
		
		//checking for if input>=0, if input >= 500000
		if (input > 500000)
			println "Loan amount must not exceed 500,000"
		else if (input <= 0)
			println "Loan Amount must be greater than zero"
		else {
			b.setLoanAmount(b.getLoanAmount() + input) //notes down the current total loan amount
			b.setBalance(b.getBalance() + input) //adds money into your wallet
			
			println "\nPHP" + input + " has been added into your account!"
			println "Loan Transaction is successfully completed\n"

			print "Current Running balance is: " + b.getBalance() + "\n"
		}
	}
	
	static void PayBills_Trans(BankAccount b) {
		int choice
		double input
		
		println "[1] Pay your loans"
		println "[2] Other bills"
		println "[0] Go back"
		print "INPUT: "
		try {
			choice = System.in.newReader().readLine() as Integer
		} catch (Exception e) {
			println "Invalid input. Please try again."	
		}
		
		switch(choice) {
			case 1:
				//check first if u have loan
				println "\nYou have chosen to pay your loans. Please wait as we check our system...."
				if (b.getLoanAmount() == 0){
					println "You currently don't have any pending loans."
				} else {
					println "You currently have a pending loan."
					println "Loan Amount " + b.getLoanAmount()
					
					println "\nHow much do you wish to pay?"					
					try {
						print "INPUT: "
						input = System.in.newReader().readLine() as Double
						}
					catch (Exception e){
						println "Please enter numbers only. Try again."
					}
					
					//checking for if input is greater than money currently in account
					if (input > b.getBalance()) {
						println "You don't have enough money to pay your loans."
					} else {
						//checking whether input is greater than loan amount
						if (input > b.getLoanAmount()) {
							//set loan to 0
							//subtract from bank
							b.setLoanAmount(0)
							b.setBalance(b.getBalance() - b.getLoanAmount())
							println "The amount you wish to pay exceeds the value of your loan. Excess payment returned to your account."
							print "Current Loan balance is: " + b.getLoanAmount() + "\n"
							println "Loan Transaction is successfully completed \n"
							
							print "Current Running balance is: " + b.getBalance() + "\n"
						} else {
							b.setLoanAmount(b.getLoanAmount() - input)
							b.setBalance(b.getBalance() - input)
							println "Successfully paid your loan!"
							print "Current Loan balance is: " + b.getLoanAmount() + "\n"
							println "Loan Transaction is successfully completed \n"
							
							print "Current Running balance is: " + b.getBalance() + "\n"
						}
					}
				}
				break;
			case 2:
				println "Other bills are currenlty unsupported. Please try again soon!"
				break;
			case 0:
				break;
			default:
				"Invalid input. Please try again later."
				break;
		}
	}
}
