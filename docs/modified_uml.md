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
### ATM:
Can accept transactions 
Cash or check deposits
Withdrawals
Transfers between checking and saving
Can take in customer card information to authorize access to account details
Can display account balance
Can update the account info as soon as a transaction is accepted
Can alert the bank about maintenance needs
Ink, paper, cash balance
Can alert the authorities and bank about any break ins
Can alert the fraud department about suspicious activities 
When a wrong pin is entered more than 3 times
Can generate receipts 
Can take photos of the customers with every transaction
Knows its status, idle, with customer, or in transaction 

### Bank computer:
Can receive account updates from ATM and immediately reflect changes to the account
Can accept requests from customers for transactions or closing or opening accounts or adding authorized users or unlocking accounts
Can decide when to shut down ATMs 
Can close an account when fraud department flags the account more than a number of times
Define ATM threshold for max daily amount taken out
Can alert authorities about bank(branch) robbery attempt

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




