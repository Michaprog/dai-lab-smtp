DAI lab: SMTP
=============

Description
----------

This repositry contains a Java implementation of a **TCP client** application that uses Socket API to communicate with a **SMTP Client***. 
The application has been designed to send email pranks to a list of victims by sending prank mails with randomly selected messages. The project demonstrate the implementation of a SMTP protocol and provide a simple oriented model for sending mails.

Repository Contents : 
----------

This project is composed by 2 directories:
- ***files*** : contains 2 files in .txt.

    -> victims.txt : contains the list of emails.
    -> messages.txt : contains the list of prank messages composed by a header and a body.
    
    *The user is free to add as much emails adress or prank messages as he wants and change them.*

- ***main*** : contains 4 java files that handle the SMTP client
    -> Main : execute the main program
    -> LoadFiles : will load the .txt files and form groups
    -> runSMTP : run the SMTP client
    -> Message : Message class for the structure

Instructions for setting up the Mock SMTP Server:
----------

To experiment with this tool without sending reels emails, we use a preconceived mock SMTP Server called MailDev that provides a web interface and a server for testing purposes.

**Setting up** :

 * 1st step : Install Docker on your machine if it's not done yet<br>

 * 2nd step : Run the following command to start the MaildevServer 
        `docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev`<br>

 * 3rd step : check if MailDev is running properly:
        `docker ps -a | grep maildev`<br>

 * 4th step : open a web browser and navigate to http://localhost:1080 to access the MailDev web interface.<br>

 * 5th step : stop the Maildev container:
        `docker stop maildev`<br>

Example Conversation using SMTP Client :
----------

`S: EHLO localhost`

`R: 250-maildev Hello localhost [127.0.0.1]`

`S: MAIL FROM: < sender@example.com >`

`R: 250 OK`

`S: RCPT TO: < victim1@example.com >`

`R: 250 OK`

`S: RCPT TO: < victim2@example.com >`

`R: 250 OK`

`S: DATA`

`R: 354 End data with <CR><LF>.<CR><LF>`

`S: Subject: Test Email`

`S: From: sender@example.com`

`S: To: victim1@example.com, victim2@example.com`  

`S: This is a test email.`

`S: . `

`R: 250 OK`

`S: QUIT`

`R: 221 Bye`
