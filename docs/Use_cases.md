GOALS:
	Business model is to get more customers and get more money
		- get more customers onboard

	Customer Service
		- allow modification of personal info
		- allow customers to close their accounts
		- allow customers to freeze (lock) their accounts 
		- have functional ATMs that are well maintained

	Provide safety to info and assets
		- authenticate the users before every transaction - pin number card number or personal info
		- reflect changes to funds ASAP 
			- keep track of the available funds in checking/saving accounts as transactions happen
			- keep track of the credit card payements
		- a fraud department that handles (misuse of cards) unauthorized users to access card or wrong pin numbers

MOST COMMON Use Cases:
	ATM:
		CUSTOMER INITIALIZES:
			deposit cash or check 
			withdraw money
			view account balance

		BANK INITIALIZES:
			locks account with 1 wrong pin number entered
			updates its database with the account balance with the transaction customer did-does

		ATM INITIALIZES:
			take photo of person at the ATM
			keeps track of its maintainance needs to generates alerts about it
			generates alerts: 
				generates an alert to its branch and calls 911 at the time of rubbery
				generates an alert to its branch janitors for refills 
				generates an alert to branch when available cash is low

	Branch:
		deposit cash or check
		withdraw money
		view account balance
		summarize and print reciept


Use Case ID: {This should be coded to identify the level of the use case}
Use Case Name: Customers goes to the ATM to deposit cash 
Relevant Requirements: Customer must have their card number and correct pin number
Primary Actor: customer initiates use
Pre-conditions: Customer must have a valid account and valid card to be able to perform this action at the ATM. ATM has
to be functional and in idle state 
Post-conditions: The added cash amount gets relflected to their account balance. ATM stores the cash and increases the
total value of deposits it has recieved. 
Basic Flow or Main Scenario: 
	flow of events: 
	1 The user initiates an action by giving card number and pin nunmber to an ATM 
	1a ATM's status switches to "in-use"
	2 The ATM captures a photo of the person at the ATM
	3 The ATM authenticates the information of user and account
	4 If authentication successfull, user choses to deposit cash in the desired account and enters the amount of cash
	4a ATM accepts the cash and stores it
	4b ATM sends account balance updates to the bank in order to update cutomer's account details
	4c Customer chooses whether to do another transaction or get recipt 
	4d ATM checks if the ink and paper levels are sufficient for printing a recipt 
	4e If resourcecs are available, the customers gets a recipt and ATM updates the value of available resources
	4f If resources are not available, ATM sends an alert about needing refills and displays a descriptive message about lack of resources to let the customer know. 
	5 ATM generates a transaction object that summarizes the events and includes the captures photo.
	6 New transaction gets stored in the bank dataset.
	7 ATM updates its status to "idle"
Extensions or Alternate Flows: A variation on this use case is: 
	 If authentication fails, ATM generates an alert about account being accessed by unauthorized user
	 Alerts recieves the ATM generated alert and takes the approperiate actions to lock the account
	 ATM displays a descriptive message to let the cutomer know access is denied
Exceptions: Error could happen if customer's card number is not linked to any accounts or if person is not a cutomer of the bank.
Related Use Cases: NA

Use Case ID: {This should be coded to identify the level of the use case}
Use Case Name: Customers goes to the ATM to deposit check 
Relevant Requirements: Customer must have their card number and correct pin number
Primary Actor: customer initiates use
Pre-conditions: Customer must have a valid account and valid card to be able to perform this action at the ATM. ATM has
to be functional and in idle state 
Post-conditions: The cash amount of check gets relflected to their account balance after check is validated in 2 busines
days. ATM stores the check and increases the total number of check deposits it has recieved. 
Basic Flow or Main Scenario: 
	flow of events: 
	1 The user initiates an action by giving card number and pin nunmber to an ATM 
	1a ATM's status switches to "in-use"
	2 The ATM captures a photo of the person at the ATM
	3 The ATM authenticates the information of user and account
	4 If authentication successfull, user choses to deposit check in the desired account and enters the check amount
	4a ATM accepts the check and stores it
	4b ATM generates an alert about the recieved check and the card number affiliated with the check
	4c Alert recieves the ATM generated alert and forwards to fraud department to authenticate the validity of check 
	4c Customer chooses whether to do another transaction or get recipt 
	4d ATM checks if the ink and paper levels are sufficient for printing a recipt 
	4e If resourcecs are available, the customers gets a recipt and ATM updates the value of available resources
	4f If resources are not available, ATM sends an alert about needing refills and displays a descriptive message about lack of resources to let the customer know. 
	5 ATM generates a transaction object that summarizes the events and includes the captures photo and check details.
	6 New transaction gets stored in the bank dataset.
	7 ATM updates its status to "idle"
Extensions or Alternate Flows: A variation on this use case is: 
	 If authentication fails, ATM generates an alert about account being accessed by unauthorized user
	 Alerts recieves the ATM generated alert and takes the approperiate actions to lock the account
	 ATM displays a descriptive message to let the cutomer know access is denied
Exceptions: Error could happen if customer's card number is not linked to any accounts or if person is not a cutomer of the bank.
Related Use Cases: Fraud recieves an alert about a check and takes up to 2 days to notofy the bank about validity.
	If check is valid, the fraud department generated an alert to let the bank know to relfect the changes on  customer account
	If check is not valid, the faurd department generates an alert to flag the customer account and lock it 


Use Case ID: {This should be coded to identify the level of the use case}
Use Case Name: Customers goes to the ATM to check account balance 
Relevant Requirements: Customer must have their card number and correct pin number
Primary Actor: customer initiates use
Pre-conditions: Customer must have a valid account and valid card to be able to perform this action at the ATM. ATM has
to be functional and in idle state 
Post-conditions: No changes to the system 
Basic Flow or Main Scenario: 
	flow of events: 
	1 The user initiates an action by giving card number and pin nunmber to an ATM 
	1a ATM's status switches to "in-use"
	2 The ATM captures a photo of the person at the ATM
	3 The ATM authenticates the information of user and account
	4 If authentication successfull, user choses to see their account balance
	5 ATM generates a transaction object that summarizes the events and includes the captures photo of cutomer.
	6 New transaction gets stored in the bank dataset.
	7 ATM updates its status to "idle"
Extensions or Alternate Flows: If authentication fails, ATM generates an alert about account being accessed by
unauthorized user
	 Alerts recieves the ATM generated alert and takes the approperiate actions to lock the account
	 ATM displays a descriptive message to let the cutomer know access is denied
Exceptions: Error could happen if customer's card number is not linked to any accounts or if person is not a cutomer of
the bank.
Related Use Cases: NA
