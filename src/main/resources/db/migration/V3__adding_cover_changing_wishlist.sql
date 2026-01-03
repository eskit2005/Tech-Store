Alter Table products
    Add Column cover_url text;

--uniquely identifying each row in the table
Alter Table wishlist
    Add constraint wishlist_pk PRIMARY KEY (user_id,product_id);

-- Drop existing foreign keys
ALTER TABLE public.wishlist
    DROP CONSTRAINT wishlist_products_id_fk,
    DROP CONSTRAINT wishlist_users_id_fk;

-- Add new foreign keys with ON DELETE CASCADE
ALTER TABLE public.wishlist
    ADD CONSTRAINT wishlist_products_id_fk
        FOREIGN KEY (product_id)
            REFERENCES public.products (id)
            ON DELETE CASCADE,
    ADD CONSTRAINT wishlist_users_id_fk
        FOREIGN KEY (user_id)
        REFERENCES public.users (id)
        ON DELETE CASCADE;
