use queasy;

-- add users
INSERT INTO `queasy`.`app_user` (`email`, `password`, `role`, `username`) VALUES ('test@some.com', '$2y$10$SYFyoCF5n6GkZ.ChtJp9/.mDLpJfs2cn0e7wsOfNnmV3QqHYz47hq', 'USER', 'test');
INSERT INTO `queasy`.`app_user` (`email`, `password`, `role`, `username`) VALUES ('test2@some.com', '$2a$10$rsZVQ9Bc32UN/EsVHfCcEupKyCT8XiSNBLMofA68DAYs9NyYVFYnO', 'USER', 'test2');

-- add roles
INSERT INTO `queasy`.`role` (`id`, `name`) VALUES (1, 'USER');
INSERT INTO `queasy`.`role` (`id`, `name`) VALUES (2, 'ADMIN');
INSERT INTO `queasy`.`role` (`id`, `name`) VALUES (3, 'OPERATOR');

INSERT INTO `queasy`.`user_roles` (`role_id`, `user_id`) VALUES (1, 2);
INSERT INTO `queasy`.`user_roles` (`role_id`, `user_id`) VALUES (2, 2);

-- add some quizzes and questions
INSERT INTO `queasy`.`quizzes` (`id`, `level`,  `valid`, `title`) VALUES (1, 1, true, 'Mon premier quiz');
INSERT INTO `queasy`.`quizzes` (`id`, `level`,  `valid`, `title`) VALUES (2, 2, true, 'Les noms de capitales');

INSERT INTO `queasy`.`questions` (id, text, quiz_id) VALUES (1, 'Quelle est la capitale de la France ?', 2);
INSERT INTO `queasy`.`questions` (id, text, quiz_id) VALUES (2, 'Quelle est la capitale du Maroc ?', 2);
INSERT INTO `queasy`.`questions` (id, text, quiz_id) VALUES (3, 'Quelle est la capitale de la Belgique ?', 2);

INSERT INTO `queasy`.`answers` (id, text, correct, question_id) VALUES
  (0, 'Paris', true, 1),
  (0, 'Lyon', false, 1);

INSERT INTO `queasy`.`answers` (id, text, correct, question_id) VALUES
  (0, 'Casablanca', false, 2),
  (0, 'Marrakech', false, 2),
  (0, 'Rabat', true, 2);