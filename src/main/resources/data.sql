INSERT INTO member (name, role)
VALUES ('브라운', 'COACH'),
       ('몽이', 'CREW');

INSERT INTO reservation (date, time, state, coach_id, crew_id)
VALUES ('2030-03-11', '11:00', 'WAITING', 1, 2),
       ('2030-03-11', '11:30', 'WAITING', 1, 2);
