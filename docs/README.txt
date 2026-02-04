How To Run(For Windows):
=======================

1. Install PostgreSQL + pgAdmin (recommended)


Database Setup
=======================
2. Create a database named: fitness_club
3. Open pgAdmin -> Query Tool -> run: 
	- sql/DDL.sql
	- sql/DML.sql

Environment
=======================
4. Create app/.env:
	DB_URL=jdbc:postgresql://localhost:5432/fitness_club
	DB_USER=postgres
	DB_PASS=your_password
	

Run Java App
=======================
5. In terminal, cd into app folder:
	cd app
6.Compile:
	mvn clean compile
7. Run:
	mvn exec:java


Optional (Run through vscode)
=======================
You can run the SQL scripts from VSCode by using a PostgreSQL/SQLtools extension and connecting to your local PostgreSQL server, then running the sql files

