INSERT INTO patient
(public_id, name, birth_date, email, gender, blood_group)
VALUES
('pat_00000001', 'John Doe', '1990-05-15', 'john.doe@example.com', 'MALE', 'O_POSITIVE'),
('pat_00000002', 'Jane Smith', '1988-11-22', 'jane.smith@example.com', 'FEMALE', 'A_POSITIVE'),
('pat_00000003', 'Michael Brown', '1995-03-10', 'michael.brown@example.com', 'MALE', 'B_POSITIVE'),
('pat_00000004', 'Emily Davis', '1992-07-08', 'emily.davis@example.com', 'FEMALE', 'AB_NEGATIVE'),
('pat_00000005', 'David Wilson', '1985-12-30', 'david.wilson@example.com', 'MALE', 'O_NEGATIVE');

INSERT INTO doctor (name, specialisation, email)
VALUES
('Dr. Sarah Smith', 'Cardiology', 'sarah.smith@hospital.com'),
('Dr. John Doe', 'Pediatrics', 'john.doe@hospital.com'),
('Dr. Elena Rostova', 'Neurology', 'elena.rostova@hospital.com');


INSERT INTO appointment (appointment_time, reason, patient_id, doctor_id)
VALUES
('2026-06-30 09:00:00', 'General Health Checkup', 1, 1),
('2026-06-30 10:30:00', 'Fever and Cough', 2, 2),
('2026-06-30 11:15:00', 'Back Pain Consultation', 3, 3),
('2026-07-01 09:45:00', 'Routine Blood Pressure Check', 4, 1),
('2026-07-01 11:00:00', 'Follow-up Appointment', 5, 2);