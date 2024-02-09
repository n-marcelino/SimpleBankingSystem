package com.ciit.cs208

class BankAccount {
	String username
	String password
	Person owner
	
	Double balance = 0
	Double loanAmount = 0
	
	
	BankAccount(String username, String password, Person owner) {
		this.username = username
		this.password = password
		this.owner = owner
	}
}
