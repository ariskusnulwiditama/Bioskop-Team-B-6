CREATE TABLE "user" (
  "user_id" serial4,
  "first_name" varchar(50) not null,
  "last_name" varchar(50) null,
  "username" varchar(50) not null,
  "email_address" varchar(50) not null,
  "password" varchar(50) not null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT user_pkey PRIMARY KEY (user_id),
  CONSTRAINT user_ukey UNIQUE (username, email_address)
);

CREATE TABLE "studio" (
  "studio_id" serial4,
  "studio_name" char(1) not null,
  "type" varchar(50) not null,
  "capasity" smallint null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT studio_pkey PRIMARY KEY (studio_id)
);

CREATE TABLE "film" (
  "film_code" int4,
  "film_name" varchar(100) not null,
  "genre" varchar(20) not null,
  "is_playing" boolean not null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT film_pkey PRIMARY KEY (film_code)
);

CREATE TABLE "seat" (
  "seat_id" serial4,
  "seat_col" char(1) not null,
  "seat_row" smallint not null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT seat_pkey PRIMARY KEY (seat_id)
);

CREATE TABLE "schedule" (
  "schedule_id" serial4,
  "film_code" int4 not null,
  "date" date not null,
  "start_time" time not null,
  "end_time" time not null,
  "price" float8 not null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT schedule_pkey PRIMARY KEY (schedule_id),
  CONSTRAINT film_fkey FOREIGN KEY (film_code) REFERENCES "film"(film_code)
);

CREATE TABLE "seat_detail" (
  "seat_detail_id" serial4,
  "seat_id" int4 not null,
  "studio_id" int4 not null,
  "schedule_id" int4 not null,
  "is_available" boolean not null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT seat_detail_pkey PRIMARY KEY (seat_detail_id),
  CONSTRAINT seat_fkey FOREIGN KEY (seat_id) REFERENCES "seat"(seat_id),
  CONSTRAINT schedule_fkey FOREIGN KEY (schedule_id) REFERENCES "schedule"(schedule_id)
);

CREATE TABLE "reservation" (
  "reservation_id" serial4,
  "seat_detail_id" int4 not null,
  "user_id" int4 not null,
  "is_active" boolean not null,
  "created_at" timestamp not null,
  "updated_at" timestamp not null,
  CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id),
  CONSTRAINT seat_detail_fkey FOREIGN KEY (seat_detail_id) REFERENCES "seat_detail"(seat_detail_id),
  CONSTRAINT user_fkey FOREIGN KEY (user_id) REFERENCES "user"(user_id)
);
