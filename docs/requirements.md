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

## Overall Description
### 2.1 Product Perspective
This is a project for our Software Engineering course- spring 2020, created to practice and demonstrate our OOP solution to a SWE problem. Bank System is a distributed system with a large DB of customers' and accounts' informations.
### 2.2 Product Functions
A user can perform all the well-known operations of a real life bank. Account owners can interact with their accounts through an ATM or at a bank branch with the help of a teller to modify their account. New customers can become account holders by visiting a bank branch. 
### 2.3 User Classes and Characteristics
Users can be either customers or to-be-customers. Customers will be given a virtual card, a card without a strip number but with a chip number, upon opening a new account. The card allows interactions with the account through an ATM. Without the card, customers can get access to their account through bank branches. 
### 2.4 Operating Environment
The software is a distributed software that will operate over a network.


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