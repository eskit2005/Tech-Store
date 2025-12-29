create table users
(
    id       bigint generated always as identity
        constraint users_pk
            primary key,
    name     varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255) not null
);

CREATE TABLE addresses
(
    id      BIGINT generated always as identity NOT NULL,
    street  VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    state   VARCHAR(255) NOT NULL,
    zip     VARCHAR(255) NOT NULL,
    user_id BIGINT       NOT NULL
        constraint addresses_users_id_fk  References users (id),
    CONSTRAINT addresses_pk PRIMARY KEY (id)
);

create table public.Categories
(
    id          smallint generated always as identity
        constraint Categories_pk
            primary key,
    name        varchar(100) not null,
    description text         not null
);

create table public.products
(
    id          uuid default gen_random_uuid() not null
        constraint products_pk
            primary key,
    description text,
    category_id smallint                       not null
        constraint products_Categories_id_fk
            references public.Categories (id),
    stock       smallint                       not null
);

create table public.profiles
(
    user_id        bigint            not null
        constraint profiles_users_id_fk
            references public.users (id),
    name           varchar(255)      not null,
    bio            text              not null,
    loyalty_points integer default 0 not null
);

create table public.wishlist
(
    product_id uuid   not null
        constraint wishlist_products_id_fk
            references public.products (id),
    user_id    bigint not null
        constraint wishlist_users_id_fk
            references public.users (id)
);

