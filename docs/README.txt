Name: Wezi Nyasulu
Student Number: 101260335


How To Run(For Windows):
=======================
== Data Base (SQL) ==

1. In pgadmin, run DDL to create the tables
2. In pgadmin, run DML to insert instances of each table

== Running Java code ==

3. In terminal, navigate to the submission folder
4. run "mvn clean compile" to compile the code
5. run "mvn exec:java" to run the code

=======================

Video Link:
=======================
https://youtu.be/agOWCVtYCW8 
=======================


Extra Explanations:
=======================
1. 
	View: Stores a saved query that shows session_id, trainer_id, start_time, and end_time from ClassSession. This is accessed later by the viewTrainerSchedule() function for simple access to a trainers sessions
	Tool: Forces members emails to be lowercase before being updated or inserted. This is used by registerMember() and updateMemberProfile() to keep emails consistent and normalized.
	Index: Creates an index on ClassSession(trainer_id) to allow the database to quickly find the sessions for a trainer. This allows functions like viewTrainerSchedule() to run faster and more efficiently
=======================

