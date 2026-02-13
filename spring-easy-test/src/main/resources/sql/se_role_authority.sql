create table se_role_authority
(
    role      varchar(50)  not null,
    authority varchar(255) not null,
    primary key (role, authority)
);

INSERT INTO test.se_role_authority (role, authority) VALUES ('USER', '/api/userIndex:read');
INSERT INTO test.se_role_authority (role, authority) VALUES ('USER', 'index:read');
