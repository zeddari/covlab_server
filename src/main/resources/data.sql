INSERT INTO `jhi_user` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`activated`,`activation_key`,`email`,`first_name`,`image_url`,`lang_key`,`last_name`,`login`,`password_hash`,`reset_date`,`reset_key`) VALUES (1,'system',NULL,'system',NULL,1,NULL,'system@localhost','System','','en','System','system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG',NULL,NULL);
INSERT INTO `jhi_user` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`activated`,`activation_key`,`email`,`first_name`,`image_url`,`lang_key`,`last_name`,`login`,`password_hash`,`reset_date`,`reset_key`) VALUES (2,'system',NULL,'system',NULL,1,NULL,'anonymous@localhost','Anonymous','','en','User','anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO',NULL,NULL);
INSERT INTO `jhi_user` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`activated`,`activation_key`,`email`,`first_name`,`image_url`,`lang_key`,`last_name`,`login`,`password_hash`,`reset_date`,`reset_key`) VALUES (3,'system',NULL,'system',NULL,1,NULL,'admin@localhost','Administrator','','en','Administrator','admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC',NULL,NULL);
INSERT INTO `jhi_user` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`activated`,`activation_key`,`email`,`first_name`,`image_url`,`lang_key`,`last_name`,`login`,`password_hash`,`reset_date`,`reset_key`) VALUES (4,'system',NULL,'system',NULL,1,NULL,'user@localhost','User','','en','User','user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K',NULL,NULL);
INSERT INTO `jhi_user` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`activated`,`activation_key`,`email`,`first_name`,`image_url`,`lang_key`,`last_name`,`login`,`password_hash`,`reset_date`,`reset_key`) VALUES (5,'anonymousUser','2020-11-07 11:59:17','anonymousUser','2020-11-07 11:59:17',1,'4WAdFQE58wDzcuJE6L3G','user@user.com',NULL,NULL,'en',NULL,'usernew','$2a$10$2U.wzkwnTNvlFGuljxdz2el8hbiVisAxQ8maPxJONMbpVvoxQFRmO',NULL,NULL);
INSERT INTO `jhi_user` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`activated`,`activation_key`,`email`,`first_name`,`image_url`,`lang_key`,`last_name`,`login`,`password_hash`,`reset_date`,`reset_key`) VALUES (6,'anonymousUser','2020-11-07 11:59:55','anonymousUser','2020-11-07 11:59:55',0,'wRRctCuu0btUndW2Doqn','azeddari@gmail.com',NULL,NULL,'en',NULL,'zeddari','$2a$10$McJpRNm6NpUA54JvLzyavuwBXpdJaKM4uAJ3o4OSx6ztUiTlePxDW',NULL,NULL);



INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_ACQUISITION');
INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_CONSTRUCTION');
INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_INTEGRATION');
INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_USER');



INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (3,'ROLE_ADMIN');
INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (1,'ROLE_USER');
INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (3,'ROLE_USER');
INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (4,'ROLE_USER');
INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (5,'ROLE_USER');
INSERT INTO `jhi_user_authority` (`user_id`,`authority_name`) VALUES (6,'ROLE_USER');