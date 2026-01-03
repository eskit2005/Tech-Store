-- Drop the foreign key constraint first
ALTER TABLE profiles
DROP CONSTRAINT profiles_users_id_fk;

-- Drop the old column
ALTER TABLE profiles
DROP COLUMN user_id;

-- Add the new column with identity and primary key
ALTER TABLE profiles
    ADD COLUMN id BIGINT GENERATED ALWAYS AS IDENTITY constraint profiles_id_pk PRIMARY KEY;
