-- database: tuo_server_pg

create table courses (
    id serial primary key,
    owner_id int,
    semester varchar(20),
    department_code varchar(20),
    course_code varchar(20),
    section_code var,
    title varchar(1024),
    instructor_name varchar(1024),
    color int,
    date_created int,
    last_modified int
);


GRANT USAGE, SELECT ON SEQUENCE courses_id_seq TO tuo_server_u;

GRANT all on courses to tuo_server_u;

--create index tasks_auth_index on tasks (public_key, secret_token);