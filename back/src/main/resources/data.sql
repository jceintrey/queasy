use queasy;

INSERT INTO `queasy`.`app_user` (`email`, `password`, `role`, `username`) VALUES ('test@some.com', '$2a$10$rsZVQ9Bc32UN/EsVHfCcEupKyCT8XiSNBLMofA68DAYs9NyYVFYnO', 'USER', 'test');
INSERT INTO `queasy`.`app_user` (`email`, `password`, `role`, `username`) VALUES ('test2@some.com', '$2a$10$rsZVQ9Bc32UN/EsVHfCcEupKyCT8XiSNBLMofA68DAYs9NyYVFYnO', 'USER', 'test2');

INSERT INTO `queasy`.`quizzes` (`id`, `level`, `title`) VALUES (1, 1, 'Mon premier quiz');
INSERT INTO `queasy`.`questions` (id, text, quiz_id) VALUES (1, 'Quelle est la capitale de la France ?', 1);
INSERT INTO `queasy`.`answers` (id, text, correct, question_id) VALUES
  (1, 'Paris', true, 1),
  (2, 'Lyon', false, 1);

  