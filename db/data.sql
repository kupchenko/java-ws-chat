INSERT INTO `ws_chat`.`roles` (`id`, `name`, `description`) VALUES ('1', 'ROLE_USER', 'user');
INSERT INTO `ws_chat`.`roles` (`id`, `name`, `description`) VALUES ('2', 'ROLE_ADMIN', 'admin');

INSERT INTO `ws_chat`.`users` (`id`, `username`, `password`) VALUES ('1', 'user1', 'pass');
INSERT INTO `ws_chat`.`users` (`id`, `username`, `password`) VALUES ('2', 'user2', 'pass');
INSERT INTO `ws_chat`.`users` (`id`, `username`, `password`) VALUES ('3', 'user3', 'pass');
INSERT INTO `ws_chat`.`users` (`id`, `username`, `password`) VALUES ('4', 'user4', 'pass');
INSERT INTO `ws_chat`.`users` (`id`, `username`, `password`) VALUES ('5', 'user5', 'pass');

INSERT INTO `ws_chat`.`user_has_roles` (`id`, `user`, `role`) VALUES ('1', '1', '2');
INSERT INTO `ws_chat`.`user_has_roles` (`id`, `user`, `role`) VALUES ('2', '1', '1');
INSERT INTO `ws_chat`.`user_has_roles` (`id`, `user`, `role`) VALUES ('3', '2', '1');
INSERT INTO `ws_chat`.`user_has_roles` (`id`, `user`, `role`) VALUES ('4', '3', '1');
INSERT INTO `ws_chat`.`user_has_roles` (`id`, `user`, `role`) VALUES ('5', '4', '1');
INSERT INTO `ws_chat`.`user_has_roles` (`id`, `user`, `role`) VALUES ('6', '5', '1');

INSERT INTO `ws_chat`.`conversations` (`id`, `name`) VALUES ('1', 'Confa1');
INSERT INTO `ws_chat`.`conversations` (`id`, `name`) VALUES ('2', 'Confa2');
INSERT INTO `ws_chat`.`conversations` (`id`, `name`) VALUES ('3', 'Confa3');
INSERT INTO `ws_chat`.`conversations` (`id`, `name`) VALUES ('4', 'Confa4');
INSERT INTO `ws_chat`.`conversations` (`id`, `name`) VALUES ('5', 'Confa5');

INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('1','1', '1');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('2','2', '1');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('3','3', '1');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('4','4', '1');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('5','5', '1');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('6','1', '2');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('7','2', '2');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('8','3', '3');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('9','4', '4');
INSERT INTO `ws_chat`.`user_has_conversations` (`id`, `conversation`, `participant`) VALUES ('10','5', '5');


INSERT INTO `ws_chat`.`messages` (`text`, `sender`, `conversation`, `timestamp`)
VALUES ('Hello!', '1', '1', '2017-12-22 22:39:40.831000');
INSERT INTO `ws_chat`.`messages` (`text`, `sender`, `conversation`, `timestamp`)
VALUES ('Hi!', '2', '1', '2017-12-22 22:41:06.315000');

INSERT INTO `ws_chat`.`messages` (`text`, `sender`, `conversation`, `timestamp`)
VALUES ('How are u???!', '1', '1', '2017-12-22 22:42:40.831000');
INSERT INTO `ws_chat`.`messages` (`text`, `sender`, `conversation`, `timestamp`)
VALUES ('Fine, thnx, u???!', '2', '1', '2017-12-22 22:43:06.315000');

INSERT INTO `ws_chat`.`messages` (`text`, `sender`, `conversation`, `timestamp`)
VALUES ('I am FINE!', '1', '1', '2017-12-22 22:39:44.831000');
INSERT INTO `ws_chat`.`messages` (`text`, `sender`, `conversation`, `timestamp`)
VALUES ('ok!', '2', '1', '2017-12-22 22:45:06.315000');