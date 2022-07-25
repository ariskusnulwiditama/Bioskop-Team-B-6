CREATE TABLE public.user (
  user_id serial4,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NULL,
  username varchar(50) NOT NULL,
  email_address varchar(50) NOT NULL,
  password varchar NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (user_id),
  CONSTRAINT user_ukey UNIQUE (username, email_address)
);

CREATE TABLE public.role (
  role_id serial4 NOT NULL,
  name varchar(255) NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT roles_pkey PRIMARY KEY (role_id)
);

CREATE TABLE public.studio (
  studio_id serial4,
  studio_name char(1) NOT NULL,
  type varchar(50) NOT NULL,
  capasity smallint NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT studio_pkey PRIMARY KEY (studio_id)
);

CREATE TABLE public.film (
  film_code int4,
  film_name varchar(100) NOT NULL,
  genre varchar(20) NOT NULL,
  is_playing boolean NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT film_pkey PRIMARY KEY (film_code)
);

CREATE TABLE public.seat (
  seat_id serial4,
  seat_col char(1) NOT NULL,
  seat_row smallint NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT seat_pkey PRIMARY KEY (seat_id)
);

CREATE TABLE public.user_roles (
  user_id int8 NOT NULL,
  role_id int8 NOT NULL,
  CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT user_fkey FOREIGN KEY (user_id) REFERENCES public.user(user_id),
  CONSTRAINT role_fkey FOREIGN KEY (role_id) REFERENCES public.role(role_id)
);

CREATE TABLE public.schedule (
  schedule_id serial4,
  film_code int4 NOT NULL,
  date date NOT NULL,
  start_time time NOT NULL,
  end_time time NOT NULL,
  price float8 NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT schedule_pkey PRIMARY KEY (schedule_id),
  CONSTRAINT film_fkey FOREIGN KEY (film_code) REFERENCES public.film(film_code)
);

CREATE TABLE public.seat_detail (
  seat_detail_id serial4,
  seat_id int4 NOT NULL,
  studio_id int4 NOT NULL,
  schedule_id int4 NOT NULL,
  is_available boolean NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT seat_detail_pkey PRIMARY KEY (seat_detail_id),
  CONSTRAINT seat_fkey FOREIGN KEY (seat_id) REFERENCES public.seat(seat_id),
  CONSTRAINT schedule_fkey FOREIGN KEY (schedule_id) REFERENCES public.schedule(schedule_id)
);

CREATE TABLE public.reservation (
  reservation_id serial4,
  seat_detail_id int4 NOT NULL,
  user_id int4 NOT NULL,
  is_active boolean NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id),
  CONSTRAINT seat_detail_fkey FOREIGN KEY (seat_detail_id) REFERENCES public.seat_detail(seat_detail_id),
  CONSTRAINT user_fkey FOREIGN KEY (user_id) REFERENCES public.user(user_id)
);
