use queasy;

-- add users
INSERT INTO `queasy`.`app_user` (`email`, `password`, `role`, `username`) VALUES ('test@some.com', '$2a$10$rsZVQ9Bc32UN/EsVHfCcEupKyCT8XiSNBLMofA68DAYs9NyYVFYnO', 'USER', 'test');
INSERT INTO `queasy`.`app_user` (`email`, `password`, `role`, `username`) VALUES ('test2@some.com', '$2a$10$rsZVQ9Bc32UN/EsVHfCcEupKyCT8XiSNBLMofA68DAYs9NyYVFYnO', 'USER', 'test2');

-- add roles
INSERT INTO `queasy`.`role` (`id`, `name`) VALUES (1, 'USER');
INSERT INTO `queasy`.`role` (`id`, `name`) VALUES (2, 'ADMIN');
INSERT INTO `queasy`.`role` (`id`, `name`) VALUES (3, 'OPERATOR');

INSERT INTO `queasy`.`user_roles` (`role_id`, `user_id`) VALUES (1, 2);
INSERT INTO `queasy`.`user_roles` (`role_id`, `user_id`) VALUES (2, 2);

INSERT INTO `queasy`.`quizzes` (`id`, `level`, `title`) VALUES (1, 1, 'Mon premier quiz');
INSERT INTO `queasy`.`questions` (id, text, quiz_id) VALUES (1, 'Quelle est la capitale de la France ?', 1);
INSERT INTO `queasy`.`answers` (id, text, correct, question_id) VALUES
  (1, 'Paris', true, 1),
  (2, 'Lyon', false, 1);

  
