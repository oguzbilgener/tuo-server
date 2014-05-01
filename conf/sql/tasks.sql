-- database: tuo_server_pg

create table users (
    id serial primary key,
    owner_id long,
    name varchar(1024),
    desc text,
    status int,
    tags text,
    related_tasks text,
    related_notes text,
    personal int,
    date_created int,
    last_modified int,
    begin_date int,
    end_date int,
    course text,
    course_code_combined text,
    text_owner_name_combined text
);