# Fetch-Reward-Backend-Challenge

Initial Steps:
This program is built using spring boot framework and Java version 11. To run this application you will need to download and install IDE for Spring Boot. I used Spring Tool Suite and 
JDK to run java applications.

If you are using STS first download and install the IDE, then follow the steps:
First download the project.
In STS, Click on File-> Import-> Maven -> Existing Maven Project -> Next -> Browse -> Choose the directory where the downloaded project is and select the project -> click finish.

Once all the files are loaded.
Expand Fetch-Rewards-Program, expand src/main/java, expand the package com.FetchRewards.FetchRewardsProgram then right click on FetchRewardsProgramApplication.java then run as java
application.

Once you followed the above mention steps you can use following routes.


To add a transaction, go to the following route: 
http://localhost:8080/transaction/add

It will display input fields for Name, Points and Timestamp. Follow the timestamp format described. If it is a new user and tries to enter negative balance it will throw exception.

To spend points, go to the following route: 
http://localhost:8080/transaction/spend

Enter the points you like to spend and submit with the button. It will direct ypu to http://localhost:8080/transaction/spendResult, which will display the payer name, spent points and balance left.
That page has a button which can be used to add more transaction.

