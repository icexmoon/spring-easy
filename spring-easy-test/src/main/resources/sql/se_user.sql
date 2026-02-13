create table se_user
(
    id       int auto_increment
        primary key,
    username varchar(50)          not null,
    password varchar(500)         not null,
    enabled  tinyint(1) default 1 not null,
    constraint username
        unique (username)
);

INSERT INTO test.se_user (id, username, password, enabled) VALUES (1, 'admin', '{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 1);
INSERT INTO test.se_user (id, username, password, enabled) VALUES (2, 'user', '{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 1);
INSERT INTO test.se_user (id, username, password, enabled) VALUES (3, 'apple', '{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 1);
