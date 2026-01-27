Alter Table refresh_token
    Drop constraint refresh_token_users_fk;

Alter Table refresh_token
    Add constraint refresh_token_users_fk foreign key (user_id)
        references users(id) ON DELETE CASCADE;

