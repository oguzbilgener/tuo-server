-- database: tuo_server_pg

create table users (
    id serial primary key,
    email_address varchar(255) not null unique,
    password_hashed varchar(255) not null,
    first_name varchar(255)
    last_name varchar(255)
    public_key varchar(255) not null unique,
    secret_token varchar(255) not null unique,
    reset_key varchar(255),
    reset_due int,
    state smallint,
    devices json,
    school_name varchar(255),
    department_name varchar(255),
    created int,
    birthday int
);

GRANT USAGE, SELECT ON SEQUENCE users_id_seq TO tuo_server_u;

create index users_auth_index on users (public_token, secret_key);