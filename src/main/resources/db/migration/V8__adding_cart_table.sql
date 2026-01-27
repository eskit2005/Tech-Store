create table public.cart
(
    id           uuid default uuidv4()     not null
        constraint cart_pk
            primary key,
    data_created date default current_date not null
);

create table public.cart_items
(
    id         bigint generated always as identity
        constraint cart_items_pk
            primary key,
    cart_id    uuid          not null
        constraint cart_items_cart_id_fk
            references public.cart (id),
    product_id uuid        not null
        constraint cart_items_products_id_fk
            references public.products (id),
    quantity   int default 1 not null,
    constraint cart_items_pk_2
        unique (product_id, cart_id)
);
