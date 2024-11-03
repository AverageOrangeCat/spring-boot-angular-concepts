CREATE TABLE "credentials" (
    "credentials_id" SERIAL PRIMARY KEY,

    -- Account data

    "email" VARCHAR(64) UNIQUE NOT NULL,

    "password_salt" VARCHAR(16) NOT NULL,

    "password_hash" VARCHAR(64) NOT NULL,

    -- Personal data

    "first_name" VARCHAR(128) NOT NULL,

    "last_name" VARCHAR(128) NOT NULL,

    "birth_date" DATE NOT NULL,

    -- Location

    "address" VARCHAR(256) NOT NULL,

    "house_number" VARCHAR(16) NOT NULL,

    "postal_code" VARCHAR(16) NOT NULL,

    "city" VARCHAR(256) NOT NULL,

    "country" VARCHAR(256) NOT NULL
);
