# Requirements Specification
## For Software Engineering Project- Bank App

Table of Contents
  * [Revision History](#revision-history)
  * [Introduction](#1-introduction)
    * 1.1 [Purpose](#11-purpose)
    * 1.2 [Product Scope](#12-product-scope)    
    * 1.3 [Definitions acronyms](#13-definitions-acronyms)
    * 1.4 [References](#14-references)
    * 1.5 [Overview](#15-overview)
  * [Overall description](#2-overall-description)
    * 2.1 [Product perspective](#21-product-perspective)
    * 2.2 [Product Architecture](#22-product-architecture)
    * 2.3 [Product Features](#23-product-features)
    * 2.4 [Constraints](#24-constraints)
    * 2.5 [Assumptions and Dependancies](#25-assumptions)
  * [Specific requirements](#3-specific-requirements) 
    * 3.1 [Functional Requirements](#31-functional-requirements)
    * 3.2 [External Interface Requirement](#32-external-interface-requirement)
    * 3.3 [Internal Interface Requirement](#33-internal-interface-requirements)
  * [Legal, Copyright and Other Notices](#4-legal-copyright-and-other-notices)
    * 4.1 [Secity and Privacy Requirements](#41-Security-and-privacy-requirements)
    * 4.2 [Environmental Requirements](#42-environmental-requirements)
    * 4.3 [Performance Requirements](#43-performance-requirements)

## Revision History
| Name | Date    | Reason For Changes  | Version   |
| ---- | ------- | ------------------- | --------- |
|      |         |                     |           |
|      |         |                     |           |
|      |         |                     |           |

## 1. Introduction
### 1.1 Purpose 
This document outlines the requirements for the Bank System, a functional interface for managing personal finances by using the offered products of Spotlight Financial Holdings.   
### 1.2 Product Scope
This document will catalog the user, system, and hardware requirements for the Bank system. It will not, however, document how these requirements will be implemented specifically. 
### 1.3 Definitions acronyms
SFH stands for the name of the Bank, Spotlight Financial Holdings.
ATM describes a machine that is located on the premise of a branch.
Branch describes a physical location of SFH bank.
ATM- Automated Teller Machine
### 1.4 References
Use Case Specification Document [docs/use_cases.md](https://github.com/Ing-Ram/BankApp/blob/master/docs/Use_cases.md).
UML Use Case Diagrams Document [docs/BankApp UML.jpg](https://github.com/Ing-Ram/BankApp/blob/master/docs/BankApp%20UML.jpg)
### 1.5 Overview
The Bank system is designed to create an accurate stimulation of a real world bank with its rules of operations being close to today's well known banks that offer checking, savings and credit card accounts.

## 2. Overall Description
### 2.1 Product Perspective
Bank System is a distributed system with a large database of
customers and accounts' informations. The system is a collection of integrated objects.
### 2.2 Product Architecture
System is designed based on object oriented paradigms. 
The system will be organized into 4 major modules: the Interface module, which is parent module of ATM and Branch moduls, the Bank module.
### 2.3 Product Features
The high-level features of the system are as follows (see section 3 of this document for more detailed requirements that address these features):
### 2.4 Constraints
NA
### 2.5 Assumptions and Dependancies
It is assumed that cutomer lives in USA and their addresses and phone numbers are in USA format.
There are no stand alone ATMs. 
It is assumed that the person using the ATM is the actual authorized user of the account.
It is assumed that when the primary account holder adds an authorized user to their account, the person is a trusted person.
It is assumed no customers have middle names or middle initials.

## 3. Specific Requirements 
### 3.1 Functional Requirements
#### 3.1.1 Common Requirements
Customers should be able to access their account informations using their card number and pin number. 
Pins are numeric values with legth 4. 
Card numbers are numeric values with length 16.
Card chip number are the last 4 digits of card number.  
Account numbers are numeric values with length 10.
Account types are strings containing one of the options {checking, saving, credit card}
#### 3.1.2 Module Requirement
Customers who don't have their card with them can authenticate their identity with their address, full name, and their phone number.
#### 3.2 External Interface Requirement
NA
#### 3.3 Internal Interface Requirement
The system must process a Transaction object that gets generated at the end of each customer interaction with an ATM or Branch. The Transaction object includes the following fields: type of the transaction, location of transaction, amount deposited or withdrawn, photo of check, photo of the person, transaction number, and card number. 
## 4. Legal, Copyright and Other Notices
Each bank entity will supply their own legal and insurance policies. This software will not reflect the 
rights and views of the companies that purchase this banking software.
All sales are final, and the transfer of legal reprecussions are transfer with the completion the sale of this
software to an entity. Also, while testing the software- if you break it, you buy it.
Each entity is required to insure bbank accounts to a predetermined amount, but the creaters of this software
will not be held accountable for that. 
### 4.1 Security and privacy requirements:
The bank entities are entitled to use this software as a bank system managment tool. 
We reserve the right to occasionally back door into a bank system and sell of user data
at market values. 
### 4.2 Environmental requirements
Our commitment to the environment requies that for every ATM that is created, a tree must be planted 
within a one mile proximity to the bank branch attached to the corporate bank entity. 
### 4.3 Performance requirements
Network required, but not included. 
The must have Java 8 or later on server machines. 
For optimal usesage accquire state of the art Mac Pro machines from apple, inc. A raspberry pi would suffice for
accessing date- not for managing it. 