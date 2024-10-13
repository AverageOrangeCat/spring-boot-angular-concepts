CREATE TABLE sessions (
    "session_id" SERIAL PRIMARY KEY,

    -- foreign keys

    "credential_id" INTEGER NOT NULL REFERENCES credentials (credential_id) ON DELETE CASCADE,

    -- session data

    "access_token" VARCHAR(128) UNIQUE NOT NULL,

    "expiration_date" DATE NOT NULL,

    "expiration_time" TIME NOT NULL
);
