GRANT ALL PRIVILEGES ON *.* TO 'user'@'%';

DROP DATABASE IF EXISTS `todo`;

CREATE DATABASE todo CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

DROP TABLE IF EXISTS `todo`.`todo`;

CREATE TABLE `todo`.`todo` (
       `id` INTEGER NOT NULL AUTO_INCREMENT,
       `title` VARCHAR(256) NOT NULL,
       `content` VARCHAR(256) NOT NULL,
       `created_date` DATETIME(6) NOT NULL DEFAULT NOW(6),
       `last_modified_date` DATETIME(6) NOT NULL DEFAULT NOW(6),
       PRIMARY KEY (`id`)
);
