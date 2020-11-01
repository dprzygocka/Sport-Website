CREATE SCHEMA IF NOT EXISTS sport_website_test;

-- -----------------------------------------------------
-- Table sports
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sport_website_test.sports
(
    sport_id   INT         NOT NULL IDENTITY,
    sport_name VARCHAR(45) NOT NULL,
    PRIMARY KEY (sport_id)
);

-- -----------------------------------------------------
-- Table teams
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`teams`
(
    `team_id`   INT         NOT NULL,
    `team_name` VARCHAR(45) NOT NULL,
    `sport_id`  INT         NOT NULL,
    PRIMARY KEY (`team_id`),
    CONSTRAINT `sport_id`
        FOREIGN KEY (`sport_id`)
            REFERENCES sport_website_test.sports (sport_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `sport_website_test`.`user_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`user_types`
(
    `user_type_id` INT         NOT NULL AUTO_INCREMENT,
    `user_name`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_type_id`)
);


-- -----------------------------------------------------
-- Table `sport_website_test`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`users`
(
    `user_id`      INT         NOT NULL AUTO_INCREMENT,
    `user_type_id` INT         NOT NULL,
    `first_name`   VARCHAR(45) NOT NULL,
    `last_name`    VARCHAR(45) NOT NULL,
    `email`        VARCHAR(45) NOT NULL,
    `team_id`      INT         NOT NULL,
    `age`          INT         NULL,
    `gender`       VARCHAR(6)  NULL,
    `phone`        VARCHAR(20) NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `team_id`
        FOREIGN KEY (`team_id`)
            REFERENCES sport_website_test.teams (team_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `user_type_id`
        FOREIGN KEY (`user_type_id`)
            REFERENCES sport_website_test.USER_TYPES (user_type_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table `mydb`.`statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`user_statuses`
(
    `status_id`   INT         NOT NULL AUTO_INCREMENT,
    `status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_id`)
);

-- -----------------------------------------------------
-- Table `mydb`.`activity_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`activity_types`
(
    `activity_type_id`   INT         NOT NULL AUTO_INCREMENT,
    `activity_type_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`activity_type_id`)
);

-- -----------------------------------------------------
-- Table `mydb`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`locations`
(
    `location_id` INT         NOT NULL AUTO_INCREMENT,
    `court_name`  VARCHAR(45) NOT NULL,
    PRIMARY KEY (`location_id`)
);

-- -----------------------------------------------------
-- Table `mydb`.`reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`reservations`
(
    `reservation_id` INT      NOT NULL AUTO_INCREMENT,
    `date_time`      DATETIME NOT NULL,
    `location_id`    INT      NULL,
    PRIMARY KEY (`reservation_id`),
    CONSTRAINT `location_id`
        FOREIGN KEY (`location_id`)
            REFERENCES sport_website_test.locations (location_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table `mydb`.`activities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`activities`
(
    `activity_id`      INT          NOT NULL AUTO_INCREMENT,
    `activity_name`    VARCHAR(45)  NOT NULL,
    `capacity`         INT          NULL,
    `description`      VARCHAR(450) NULL,
    `creator_id`       INT          NULL,
    `activity_type_id` INT          NOT NULL,
    `is_cancelled`     BINARY(1)    NOT NULL,
    `reservation_id`   INT          NOT NULL,
    `team_id`          INT          NOT NULL,
    PRIMARY KEY (`activity_id`),
    CONSTRAINT `activity_type_id`
        FOREIGN KEY (`activity_type_id`)
            REFERENCES sport_website_test.ACTIVITY_TYPES (activity_type_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `creator`
        FOREIGN KEY (`creator_id`)
            REFERENCES sport_website_test.users (user_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `reservation_id`
        FOREIGN KEY (`reservation_id`)
            REFERENCES sport_website_test.reservations (reservation_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `team_id2`
        FOREIGN KEY (`team_id`)
            REFERENCES sport_website_test.teams (team_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `mydb`.`activity_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`activity_statuses`
(
    `status_id`   INT NOT NULL,
    `user_id`     INT NOT NULL,
    `activity_id` INT NOT NULL,
    PRIMARY KEY (`status_id`, `user_id`, `activity_id`),
    CONSTRAINT `user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES sport_website_test.users (user_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `status_id`
        FOREIGN KEY (`status_id`)
            REFERENCES sport_website_test.user_statuses (status_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `activity_id`
        FOREIGN KEY (`activity_id`)
            REFERENCES sport_website_test.activities (activity_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `mydb`.`responsibilities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`responsibilities`
(
    `responsibility_id`   INT         NOT NULL AUTO_INCREMENT,
    `responsibility_name` VARCHAR(45) NOT NULL,
    `sport_id`            INT         NULL,
    PRIMARY KEY (`responsibility_id`),
    CONSTRAINT `sport_id_2`
        FOREIGN KEY (`sport_id`)
            REFERENCES sport_website_test.sports (sport_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `mydb`.`user_responsibilities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`user_responsibilities`
(
    `responsibility_id` INT NOT NULL,
    `user_id`           INT NULL,
    `activity_id`       INT NOT NULL,
    PRIMARY KEY (`responsibility_id`, `activity_id`),
    CONSTRAINT `responsibility_id`
        FOREIGN KEY (`responsibility_id`)
            REFERENCES sport_website_test.responsibilities (responsibility_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `activity_id2`
        FOREIGN KEY (`activity_id`)
            REFERENCES sport_website_test.activities (activity_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `user_id2`
        FOREIGN KEY (`user_id`)
            REFERENCES sport_website_test.users (user_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table `mydb`.`matches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sport_website_test`.`matches`
(
    `matches_id`            INT NOT NULL AUTO_INCREMENT,
    `score`                 INT NULL,
    `player_of_the_matches` INT NULL,
    `activity_id`           INT NOT NULL,
    PRIMARY KEY (`matches_id`),
    CONSTRAINT `activity_id3`
        FOREIGN KEY (`activity_id`)
            REFERENCES sport_website_test.ACTIVITIES (activity_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `player_of_the_matches`
        FOREIGN KEY (`player_of_the_matches`)
            REFERENCES sport_website_test.users (user_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);