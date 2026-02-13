create table se_user_authority
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint ix_auth_username
        unique (username, authority),
    constraint fk_user_authorities_users
        foreign key (username) references se_user (username)
);

INSERT INTO test.se_user_authority (username, authority) VALUES ('apple', '/api/index:read');
INSERT INTO test.se_user_authority (username, authority) VALUES ('apple', 'ROLE_USER');
INSERT INTO test.se_user_authority (username, authority) VALUES ('user', 'ROLE_ADMIN');
