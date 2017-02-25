# ClJobs

This is a simple application scraping data from Craiglist lets you search in the Jobs and send report to the email

####Example of html email report:

![Alt text](/example_email_report.PNG?raw=true "Example of email report")

####Example of code:
    
```java
ArrayList markingTags = new ArrayList();
markingTags.add("Software");
markingTags.add("Developer");
markingTags.add("Academy");

SearchParams searchParams = new SearchParams("developer", "seoul", "co.kr", markingTags);
JobSeeker jobSeeker = new JobSeeker(searchParams);

EmailCredentials mailCredentials = new EmailCredentials("login", "password", "smtp.email.com", "recipient");
EmailSubscriber subscriber = new EmailSubscriber(mailCredentials);

jobSeeker.addSubscriber(subscriber);
jobSeeker.searchAndNotifyIfNecessary();
```
####How to use via command line:
 1. type: "-add-seeker"
 2. type: "vacancy location domain tags"
 3. type: "login password host recipient"
 2. type "-search"
 
