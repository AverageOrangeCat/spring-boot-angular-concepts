CREATE TABLE "authenticated_sessions" (
    "authenticated_session_id" SERIAL PRIMARY KEY,

    -- Foreign keys

    "credentials_id" INTEGER NOT NULL REFERENCES "credentials" (credentials_id) ON DELETE CASCADE,

    "session_id" INTEGER NOT NULL REFERENCES "sessions" (session_id) ON DELETE CASCADE,

    -- Enforce uniqueness on key combination

    UNIQUE("credentials_id", "session_id")
);
