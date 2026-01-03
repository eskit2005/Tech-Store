-- Drop the primary key constraint first
ALTER TABLE profiles
DROP CONSTRAINT profiles_id_pk;

-- Drop the old column
ALTER TABLE profiles
    DROP COLUMN id;

Alter Table profiles
    Add Column id bigint not null;
-- 2️⃣ Ensure id is PRIMARY KEY
ALTER TABLE profiles
    ADD CONSTRAINT profiles_id_pk PRIMARY KEY (id);

-- 3️⃣ Make id also a FOREIGN KEY to users(id)
ALTER TABLE profiles
    ADD CONSTRAINT profiles_id_users_id_fk FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE;
