SELECT pg_catalog.setval(pg_get_serial_sequence('user', 'user_id'), (SELECT MAX(user_id) FROM public.user)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('seat', 'seat_id'), (SELECT MAX(seat_id) FROM public.seat)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('film', 'film_code'), (SELECT MAX(film_code) FROM public.film)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('schedule', 'schedule_id'), (SELECT MAX(schedule_id) FROM public.schedule)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('reservation', 'reservation_id'), (SELECT MAX(reservation_id) FROM public.reservation)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('seat_detail', 'seat_detail_id'), (SELECT MAX(seat_detail_id) FROM public.seat_detail)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('role', 'role_id'), (SELECT MAX(role_id) FROM public.role)+1);
SELECT pg_catalog.setval(pg_get_serial_sequence('studio', 'studio_id'), (SELECT MAX(studio_id) FROM public.studio)+1);