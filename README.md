# Daily News App 

#### The application allows you to create daily news, keep a record of news views, edit them, receive detailed statistics.

### Description

The application was developed in accordance with the terms of reference. Only the user with the ADMIN role has access to the administrator page; by default, when the application starts, a user is created with this role, admin login and admin password. All other users register through the registration form http: // localhost: 8080 / registration? with the role of USER. You can log in to the application on the page http: // localhost: 8080 / login. If the user is not authorized, when he gets to the main page http: // localhost: 8080 / he will be redirected to the authorization page, if the user is in the USER role, he will be redirected to the page with today's news http: // localhost: 8080 / daily_news, if user in the role of ADMIN will be directed to the admin page. 
1. The admin page, http: // localhost: 8080 / admin /, allows you to select either the message entered by the administrator for display for tomorrow or select a random topic from the repository. If the message for tomorrow is not selected, a bright red line at the top of the page will remind you of this. If the administrator has not made a decision before 0:00 Moscow time, a random topic from the store is selected for display. By default, the admin page displays news for the last week with statistics for all time.Statistics includes the total number of views of the news and the number of unique users who viewed this news. There is a statistics request form on the page where you need to enter a time period, news for which to take into account, and a time period for which to take into account statistics. Also on the page there is a form for creating / editing news, where you want to enter the date of the news, topic and text. If the news existed on this date, then it will be changed, otherwise created.
2. A user who visits his page can see today's message, can scroll through the previous ones by clicking on the relevant links. Also on the page there is a form for requesting news for a specific date. If a message exists and the user does not request a message for a date that has not yet arrived, it will be received.

### Used technologies
Java 11, Spring Boot, Spring Security, Spring Data, PostgresQL, REST Api, Git, Gradle, Lombok, Flyway, Mustache.

### How to start
* Annotation processor should be enabled in Intellij IDEA settings.
* Set up database
    * install Postresql on your PC
    * choose DB port 5432 or leave it as default
    * set up `spring.datasource.username` and `spring.datasource.password` as postgres and 0123456kKk OR change according fields in      properties file if you want to set other ones
create database news in postresql
