# CustomersFX MVC Application Demo Project

Technology stack:
--
- JavaFX 8
- ControlsFX 8.40.13
- JPA 2.1
- Hibernate ORM 5.2.11.Final
- JasperReports 6.4.1
- Apache Commons 3.4
- Logback 1.2.3
- Flyway 4.2.0
- MySQL ver: 5.1.44

Run the CustomersFX Project:
--
1. Clone the CustomersFX project:

    git clone https://devsniper@bitbucket.org/devsniper/customersfx.git
    
2. Maven install all dependencies:

    mvn clean install
    
3. Create **MySQL Database** in terminal or in your favorite MySQL tool: 
    
    CREATE DATABASE customersfxdb CHARACTER SET utf8 COLLATE utf8_general_ci;
    
4. Change your **MySQL credentials** in pom.xml:
    
     database.user
     
     database.password
            
5. Run CustomersFX
    
    mvn jfx:run

CustomersFx Demo Accounts:
--
Username: admin

Password: 1

Username: editor

Password: 1