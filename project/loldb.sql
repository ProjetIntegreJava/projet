DROP DATABASE IF EXISTS lol;
CREATE DATABASE lol;
USE lol;


CREATE TABLE club (
    `name` VARCHAR(50) NOT NULL PRIMARY KEY,
    CEO VARCHAR(100) NOT NULL,
    nationality VARCHAR(50) NOT NULL
);

CREATE TABLE region (
    `name` VARCHAR(50) NOT NULL PRIMARY KEY,
    CONSTRAINT chk_region CHECK (`name` IN ('Amériques', 'Europe', 'Corée du sud', 'Chine', 'France', 'World'))
);

CREATE TABLE competition_level (
   `name` VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE competition (
    `name` VARCHAR(50) NOT NULL,
    `year` INT NOT NULL,
    region VARCHAR(255) NOT NULL,
    `level` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`name`, `year`),
    FOREIGN KEY (region) REFERENCES region(`name`),
    FOREIGN KEY (`level`) REFERENCES competition_level(`name`),
    CONSTRAINT chk_competition CHECK (`name` IN ('World Championship', 'LEC', 'LCS', 'LCK', 'LPL', 'MSI', 'CBLOL', 'VCS', 'LJL', 'LLA', 'LCO', 'PCS', 'LFL', 'EMEA Masters')),
    CONSTRAINT chk_year CHECK (`year` >= 2011)
);

CREATE TABLE team (
    `name` VARCHAR(50) NOT NULL PRIMARY KEY,
    club VARCHAR(50) NOT NULL,
    region VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
    founding_date DATE NOT NULL,
    has_been_world_champion BOOLEAN NOT NULL,
    `description` TEXT NULL,
    nb_followers INT NULL,
    FOREIGN KEY (club) REFERENCES club(`name`) ON DELETE CASCADE,
    FOREIGN KEY (region) REFERENCES region(`name`)
);

CREATE TABLE player (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    pseudo VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    nationality VARCHAR(255) NOT NULL
);

CREATE TABLE `role` (
    `name` VARCHAR(255) NOT NULL PRIMARY KEY,
    CONSTRAINT chk_role CHECK (`name` IN('Top', 'Middle', 'Jungle', 'Support', 'ADC'))
);

CREATE TABLE champion (
    `name` VARCHAR(255) NOT NULL PRIMARY KEY,
    race VARCHAR(255) NOT NULL
);

CREATE TABLE `match` (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    team_blue VARCHAR(255) NOT NULL,
    team_red VARCHAR(255) NOT NULL,
    competition_name VARCHAR(255) NOT NULL,
    competition_year INT NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    occurrence_date DATE NOT NULL,
    is_blue_win BOOLEAN NOT NULL,
    replay_link TEXT NULL,
    summary TEXT NULL,
    FOREIGN KEY (team_blue) REFERENCES team(`name`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (team_red) REFERENCES team(`name`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (competition_name, competition_year) REFERENCES competition(`name`, `year`)
);

CREATE TABLE participation (
    id_player INT NOT NULL,
    id_match INT NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    champion VARCHAR(255) NOT NULL,
    kills INT NOT NULL,
    assists INT NOT NULL,
    death INT NOT NULL,
    creep_score INT NOT NULL,
    damage INT NOT NULL,
    wards_score INT NOT NULL,
    golds_earn INT NOT NULL,
    damage_received INT NOT NULL,
    PRIMARY KEY (id_player, id_match),
    FOREIGN KEY (id_player) REFERENCES player(id),
    FOREIGN KEY (id_match) REFERENCES `match`(id) ON DELETE CASCADE,
    FOREIGN KEY (role) REFERENCES role(`name`),
    FOREIGN KEY (champion) REFERENCES champion(`name`)
);

CREATE TABLE bans (
    champion VARCHAR(255) NOT NULL,
    team VARCHAR(255) NOT NULL,
    id_match INT NOT NULL,
    PRIMARY KEY (champion, team, id_match),
    FOREIGN KEY (champion) REFERENCES champion(`name`),
    FOREIGN KEY (team) REFERENCES team(`name`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_match) REFERENCES `match`(id) ON DELETE CASCADE
);

CREATE TABLE team_history (
    id_player_one INT NOT NULL,
    id_player_two INT NOT NULL,
    id_player_three INT NOT NULL,
    id_player_four INT NOT NULL,
    id_player_five INT NOT NULL,
    team VARCHAR(255) NOT NULL,
    beginning_date DATE NOT NULL,
    ending_date DATE NOT NULL,
    PRIMARY KEY (id_player_one, id_player_two, id_player_three, id_player_four, id_player_five, team),
    FOREIGN KEY (id_player_one) REFERENCES player(id),
    FOREIGN KEY (id_player_two) REFERENCES player(id),
    FOREIGN KEY (id_player_three) REFERENCES player(id),
    FOREIGN KEY (id_player_four) REFERENCES player(id),
    FOREIGN KEY (id_player_five) REFERENCES player(id),
    FOREIGN KEY (team) REFERENCES team(`name`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT chk_date CHECK(beginning_date < ending_date)
);
