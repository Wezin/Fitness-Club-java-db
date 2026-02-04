INSERT INTO Member (name, email, date_of_birth, gender, phone, target_weight, target_body_fat)
VALUES
('Max Payne', 'max@hotmail.com', '2002-02-24', 'Male', '123-456-7890', 190, 24.4),
('Padme Amidala', 'Padme@hotmail.com', '1999-10-01', 'Female', '123-555-6789', 140, 15.8),
('Albert Robertson', 'Albert@hotmail.com', '2004-04-16', 'Male', '883-865-3455', 350, 50);

INSERT INTO Trainer (name, email, specialization) VALUES
('Ben Kenobi', 'ben.trainer@hotmail.com', 'Strength Training'),
('Charlie Brown', 'charlie.trainer@hotmail.com', 'Yoga'),
('Jack Sparrow', 'jack.trainer@hotmail.com', 'Weight Loss');

INSERT INTO AdminStaff (name, email) VALUES
('Bruce Wanye', 'bruce.admin@hotmail.com'),
('Albus Dumbledore', 'henry.admin@hotmail.com');

INSERT INTO HealthMetric (member_id, metric_datetime, weight, heart_rate, body_fat_percentage, notes) VALUES
--Max
(1, '2025-11-20 12:25', 165, 72, 22.8, 'Baseline Movement'),
(1, '2025-11-27 14:41', 160, 70, 22.1, 'Slight Improvement'),
--Padme
(2, '2025-11-22 20:22', 130, 76, 19.2, 'Post-workout'),
--Albert
(3, '2025-11-25 06:33', 250, 80, 44.5, 'Check up');

INSERT INTO Room (name, capacity, location) VALUES
('Studio A', 20, 'First Floor'),
('Studio B', 15, 'First Floor'),
('PT Room', 3, 'Second Floor');

INSERT INTO ClassType (name, description, default_capacity, status) VALUES
('Strength Building Basics', 'Entry-level Strength building class focused on the development of core muscles', 20, 'scheduled'),
('Yoga basics', 'Beginner Friendly yoga class aimed at honing ones flexibility and breathing', 15, 'scheduled'),
('Weight Loss', 'Physically intense and scientifically proven fat burning training/exercise', 15, 'scheduled');

INSERT INTO ClassSession (class_type_id, trainer_id, room_id, day_of_week, start_time, end_time, capacity) VALUES
-- Strength training with Ben Kenobi Studio A
(1, 1, 1, 2, '16:00', '18:00', 20), --CHANGE TO JUST TIME NOT DATE
-- Yoga with Charlie Brown in Studio A
(2,2,1,1, '07:00', '08:00', 15),
-- Weight Loss class with Jack Sparrow in Studio B
(3,3,2,3, '20:00', '22:00', 15);

INSERT INTO TrainerAvailability (trainer_id, day_of_week, start_time, end_time, status) VALUES
-- Ben Kenobi: Monday and Wednesdays Afternoon before strength training
(1, 1, '13:00', '15:45', 'unoccupied'), -- Monday
(1, 3, '13:00', '15:45', 'unoccupied'), -- Wednesday
-- Charlie Brown: Tuesdays and Thursdays After Yoga class
(2, 2, '08:15', '10:00', 'unoccupied'), -- Tusday
(2, 4, '13:00', '15:45', 'unoccupied'), -- Thussday
-- Jack Sparrow Friday Mornings
(3, 5, '10:00', '12:00', 'unoccupied');

INSERT INTO ClassRegistration (member_id, session_id, registration_time, status) VALUES
--Max Payne for strength training 
(1,1, '2025-11-27 16:00', 'registered'),
-- Padme Amidala for Yoga and Strength training
(2,2, '2025-11-14 10:30', 'registered'),
(2,1, '2025-11-16 12:22', 'registered'),
--Albert Robertson for weight loss
(3,3, '2025-12-01 23:56', 'registered');




