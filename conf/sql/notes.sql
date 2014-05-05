-- database: tuo_server_pg

create table notes (
    id serial primary key,
    owner_id int,
    attachment text,
    content text,
    date_created int,
    last_modified int,
    related_task_id int
);


GRANT USAGE, SELECT ON SEQUENCE notes_id_seq TO tuo_server_u;

GRANT all on notes to tuo_server_u;

--create index tasks_auth_index on tasks (public_key, secret_token);