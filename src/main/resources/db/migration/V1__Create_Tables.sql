CREATE TABLE "tasks" (
    "id"            BIGSERIAL PRIMARY KEY,
    "title"         VARCHAR NOT NULL,
    "description"   VARCHAR
);

CREATE TABLE "calendars" (
    "id"            BIGSERIAL PRIMARY KEY,
    "title"         VARCHAR NOT NULL,
    "date_start"    TIMESTAMP DEFAULT (now() at time zone 'utc'),
    "date_end"      TIMESTAMP DEFAULT ((now()+interval '7' day) at time zone 'utc'),
    "description"   VARCHAR NOT NULL
);