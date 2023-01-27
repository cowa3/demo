create table todo (
                      userId integer not null ,
                      id serial primary key,
                      title varchar not null,
                      completed boolean not null default false
);