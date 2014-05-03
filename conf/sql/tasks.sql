-- database: tuo_server_pg

create table tasks (
    id serial primary key,
    owner_id int,
    name varchar(1024),
    description text,
    status int,
    tags text,
    related_tasks text,
    related_notes text,
    personal boolean,
    date_created int,
    last_modified int,
    begin_date int,
    end_date int,
    course text,
    course_code_combined text,
    task_owner_name_combined text
);


GRANT USAGE, SELECT ON SEQUENCE tasks_id_seq TO tuo_server_u;

GRANT all on tasks to tuo_server_u;

--create index tasks_auth_index on tasks (public_key, secret_token);