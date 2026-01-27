Create Table refresh_token(
    token text constraint refresh_token_pk Primary Key,
    issuedAt timestamptz not null default now(),
    expiry timestamptz not null,
    revoked boolean default false not null,
    user_id bigint not null constraint refresh_token_users_fk references users(id)

);

