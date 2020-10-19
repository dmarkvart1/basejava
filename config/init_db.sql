alter table contact owner to postgres;

create table resume
(
    uuid char(36) not null
        constraint resume_pk
            primary key,
    full_name text not null
);

alter table resume owner to postgres;

create table contact
(
    id serial,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type text not null,
    value text not null
);

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);


alter table section owner to postgres;
create table section
(
    resume_uuid char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on delete cascade,
    type text,
    value text,
    id serial not null
        constraint section_pk
            primary key
);

alter table section owner to postgres;


