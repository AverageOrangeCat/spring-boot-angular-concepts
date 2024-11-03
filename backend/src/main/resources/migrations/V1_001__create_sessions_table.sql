CREATE TABLE "sessions" (
    "session_id" SERIAL PRIMARY KEY,

    -- Session data

    "session_token" VARCHAR(128) UNIQUE NOT NULL,

    "expiration_unix_date" INTEGER NOT NULL
);
