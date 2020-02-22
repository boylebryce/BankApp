### Customer:
Can interact with ATM and a Teller
Can access the information in their accounts
Knows their Card information (chip #, card #, pin #)
Can make transactions
Deposit cash and checks
Withdraw cash
Transfer money between checking and savings
Can request a paper or email receipts for their ATM transactions
Can go to a branch to open a new account but 3 is the limit
Can add authorized users to their accounts but 1 is the limit
Can go to the branch to close their account
Can report their card stolen

### Bank:
Contains all the customer's personal information
Contains all the bank acounts 
Can authenticate a user based on given information
Can report the contents of an account upon request from an Interface

### Interface:
Can accept transactions from customers with valid card and pin number
Can take in customer card information and ask the bank to authorize access to account details
Can allow customers to do transactions of withdrawals, deposits of cash and check
Can generate receipts - summary of transactions
Can sommunicate to the bank to immediately reflect changes to the customer's account
Can report account balance
Can generate a random transaction number
Knows the number of checks it has recieved 
Knows the total amount of deposited cash by customers

### ATM:
Can accept transactions 
Transfers between checking and saving
Can display account balance
Can update the account info as soon as a transaction is accepted
Can alert the bank about maintenance needs
Ink, paper, cash balance
Can alert the authorities and bank about any break ins
Can alert the fraud department about suspicious activities 
When a wrong pin is entered once
Can take photos of the customers with every transaction
Knows its status, idle, with customer, or in transaction 


### Bank Branch:
Has an adddress
Has a name
Knows the amount of available cash in inventory
Knows the affiliated ATMs to itself - ATMs that are in its premise
Can accept requests from customers for transactions or closing or opening accounts or adding authorized users or unlocking accounts
Can decide when to shut down ATMs 
Can close an account when fraud department flags the account more than a number of times
Define ATM threshold for max daily amount taken out
Can alert authorities about bank(branch) robbery attempt
Collects the deposited cash in the ATMs at the end of each day

### Fraud department:
Can decide if a check from ATM or branch is fake and decline the updates to the account
Can flag accounts for fraud or stolen
Decides whether or not to lock an account 

### Card
A data obj that holds its card # and chip # and name of its owner

### Accounts
Knows its type of account checking, saving, or credit
The authorized users and primary owner
Stores the deposited checks information
Knows the # of attempts with wrong pin number made on it
Information of the account owners
Current available balance and processing balance
Contains a list of the transactions the owners did
Knows whether account is locked or stolen

### Maintenance department:
Can accept alerts for an ATM to be fixed
Responds whether a person is on the way

### Security department:
Can accept alerts from a bank about an ATM or branch 
Responds whether a person is on the way

### Alerts:
Receives transactions from ATM
Receives alerts from bank branch 
Determine priority level of alerts
Distribute info to correct department woh can appropriately handle

### Transaction:
Constains info about 
* Randomly generated number for the transaction
* Card number used to make the transaction
* Type of transaction it is(deposit cash/check, withdraw cash, check account balance)
* Location of transaction
* Amount of money deposited
* Photo of the check
* Photo of the person 




