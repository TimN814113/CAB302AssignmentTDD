INSERT INTO Quiz (quiz_id, title, created_at) VALUES (1, 'Biology Basics', '2025-04-07 10:00:00');

-- Question 1
INSERT INTO Question (question_id, quiz_id, question_number, question_text) 
VALUES (1, 1, 1, 'What is the powerhouse of the cell?');

-- Question 2
INSERT INTO Question (question_id, quiz_id, question_number, question_text) 
VALUES (2, 1, 2, 'Which organelle is responsible for photosynthesis?');

-- Choices for Question 1
INSERT INTO Choice (choice_id, question_id, choice_number, choice_text, is_correct) VALUES
(1, 1, 1, 'Nucleus', 0),
(2, 1, 2, 'Mitochondria', 1),
(3, 1, 3, 'Ribosome', 0),
(4, 1, 4, 'Golgi Apparatus', 0);

-- Choices for Question 2
INSERT INTO Choice (choice_id, question_id, choice_number, choice_text, is_correct) VALUES
(5, 2, 1, 'Mitochondria', 0),
(6, 2, 2, 'Endoplasmic Reticulum', 0),
(7, 2, 3, 'Chloroplast', 1),
(8, 2, 4, 'Lysosome', 0);
