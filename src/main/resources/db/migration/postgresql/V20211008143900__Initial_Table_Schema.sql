-- SEQUENCE: public.users_id_seq

-- DROP SEQUENCE public.users_id_seq;

CREATE SEQUENCE public.roles_id_seq
    INCREMENT 1
    START 1
    MINVALUE 0
    MAXVALUE 1111111111111
    CACHE 1;

ALTER SEQUENCE public.roles_id_seq
    OWNER TO postgres;

-- Table: public.roles

-- DROP TABLE public.roles;

CREATE TABLE public.roles (
    id integer NOT NULL   DEFAULT nextval('roles_id_seq'::regclass)  ,
    name character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);


 ------------------------------------------------------------------------------------------
CREATE SEQUENCE public.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 0
    MAXVALUE 1111111111111
    CACHE 1;

ALTER SEQUENCE public.users_id_seq
    OWNER TO postgres;

 -- Table: public.users

 -- DROP TABLE public.users;

 CREATE TABLE public.users
 (
     id bigint NOT NULL    DEFAULT nextval('users_id_seq'::regclass)  ,
     email character varying(255) COLLATE pg_catalog."default",
     is_moderator boolean,
     password character varying(255) COLLATE pg_catalog."default",
     username character varying(255) COLLATE pg_catalog."default",
     voted_point character varying(255) COLLATE pg_catalog."default",
     current_ticket character varying(255) COLLATE pg_catalog."default",
     CONSTRAINT users_pkey PRIMARY KEY (id),
     CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
     CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username)
 );
-------------------------------------------------------------

-- Table: public.user_roles

-- DROP TABLE public.user_roles;

CREATE TABLE public.user_roles
(
    user_id bigint NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
------------------------------------------------------------------------

CREATE SEQUENCE public.ticket_id_seq
    INCREMENT 1
    START 1
    MINVALUE 0
    MAXVALUE 1111111111111
    CACHE 1;

ALTER SEQUENCE public.ticket_id_seq
    OWNER TO postgres;

-- Table: public.ticket_points

-- DROP TABLE public.ticket_points;

CREATE TABLE public.ticket_points
(
    id bigint NOT NULL    DEFAULT nextval('ticket_id_seq'::regclass)   ,
    play_status boolean,
    points character varying(255) COLLATE pg_catalog."default",
    status boolean,
    ticket_number character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    user_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT ticket_points_pkey PRIMARY KEY (id)
);
-----------------------------------------------------------------------


INSERT INTO roles(id,name) VALUES(1,'ROLE_USER');
INSERT INTO roles(id,name) VALUES(2,'ROLE_MODERATOR');
INSERT INTO roles(id,name) VALUES(3,'ROLE_ADMIN');