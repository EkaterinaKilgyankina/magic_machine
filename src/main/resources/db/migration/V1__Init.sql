CREATE TABLE "coffee_machine"
(
    "id"           bigserial PRIMARY KEY,
    "water_percentage" integer NOT NULL,
    "beans_percentage" integer NOT NULL,
    "milk_percentage"  integer NOT NULL
);

INSERT into "coffee_machine" (water_percentage, beans_percentage, milk_percentage)
VALUES (100, 100, 100);

CREATE TABLE "coffee_statistic"
(
    "id"   bigserial PRIMARY KEY,
    "coffee_machine_id"   bigint NOT NULL,
    "date" timestamp NOT NULL,
    "type" integer   NOT NULL
);