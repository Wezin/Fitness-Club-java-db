CREATE TABLE Member (
	member_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	date_of_birth DATE,
	gender VARCHAR(20),
	phone VARCHAR(20),
	join_date DATE DEFAULT CURRENT_DATE,
	target_weight NUMERIC(5,2),
	target_body_fat NUMERIC(5,2)
);

CREATE TABLE Trainer (
	trainer_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	specialization VARCHAR(100)
);

CREATE TABLE AdminStaff(
	admin_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE HealthMetric(
	metric_id SERIAL PRIMARY KEY,
	member_id INT NOT NULL REFERENCES Member(member_id),
	metric_datetime TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
	weight NUMERIC(5,2),
	heart_rate INT,
	body_fat_percentage NUMERIC(5,2),
	notes TEXT
);

CREATE TABLE Room(
	room_id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	capacity INT NOT NULL,
	location VARCHAR(100)
);

CREATE TABLE ClassType(
	class_type_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description TEXT,
	default_capacity INT NOT NULL,
	status VARCHAR(20) DEFAULT 'unscheduled'
);

CREATE TABLE ClassSession(
	session_id SERIAL PRIMARY KEY,
	class_type_id INT NOT NULL REFERENCES ClassType(class_type_id),
	trainer_id INT NOT NULL REFERENCES Trainer(trainer_id),
	room_id INT REFERENCES Room(room_id),
	day_of_week INT,
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	capacity INT NOT NULL
);

CREATE TABLE TrainerAvailability(
	availability_id SERIAL PRIMARY KEY,
	trainer_id INT NOT NULL REFERENCES Trainer(trainer_id),
	day_of_week INT,
	start_time TIME,
	end_time TIME,
	status VARCHAR(20) DEFAULT 'unoccupied'
);

CREATE TABLE ClassRegistration(
	registration_id SERIAL PRIMARY KEY,
	member_id INT NOT NULL REFERENCES Member(member_id),
	session_id INT NOT NULL REFERENCES ClassSession(session_id),
	registration_time TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
	status VARCHAR(20) DEFAULT 'registered',
	UNIQUE (member_id, session_id) 
);



--View
CREATE OR REPLACE VIEW TrainerScheduleView AS
SELECT
	--Colums taken from Classsession 
	session.session_id,
	session.trainer_id,
	session.start_time,
	session.end_time
FROM ClassSession session;

--Trigger
CREATE OR REPLACE FUNCTION normalize_member_email()
RETURNS TRIGGER AS $$
BEGIN
	NEW.email := lower(NEW.email); --Convert to lowercase
	RETURN NEW; --return updated row
END;
$$ LANGUAGE plpgsql; --write in PL/pgSQL
--For every new or upadted memebr row, run mormalize_member_email() to fix email befoer saving 
CREATE TRIGGER trigger_normalize_member_email
BEFORE INSERT OR UPDATE ON Member
FOR EACH ROW
EXECUTE FUNCTION normalize_member_email();

--Index
CREATE INDEX idx_classsession_trainer
	ON ClassSession(trainer_id); --Build index on trainer_id
