INSERT INTO sport_website_test.sports (sport_id, sport_name)
VALUES
(1, 'Football'),
(2, 'Volleyball'),
(3, 'Basketball');

INSERT INTO sport_website_test.teams (team_id, team_name, sport_id)
VALUES
(1, 'Women Football', 1),
(2, 'Men Football', 1),
(3, 'Women Volleyball', 2),
(4, 'Men Volleyball', 2),
(5, 'Women Basketball', 3),
(6, 'Men Basketball', 3);

INSERT INTO sport_website_test.user_types (user_type_id, user_name)
VALUES
(1, 'COACH'),
(2, 'PLAYER');

INSERT INTO sport_website_test.users (user_id, user_type_id, first_name, last_name, email, team_id, age, gender, phone)
VALUES
(1, 1, 'Bobby', 'Lee', 'bobby@lea.com', 2, 45, 'male', '+4550870452'),
(2, 1, 'Roxy', 'Jane', 'roxy@jane.com', 3, 30, 'female', '+4580530425'),
(3, 2, 'Melvin', 'Student', 'melvin@student.com', 2, 18, 'male', '+4530450892'),
(4, 2, 'Sheela', 'Student', 'shella@student.com', 3, 20, 'female', '+4580350417'),
(5, 1, 'Coach', 'Coach', 'coach@coach.com', 5, 35, 'female', '+458053041');

INSERT INTO sport_website_test.user_statuses(status_id, status_name)
VALUES
(1, 'READY'),
(2, 'UNAVAILABLE'),
(3, 'PICKED'),
(4, 'HAS NOT ANSWERED');

INSERT INTO sport_website_test.activity_types(activity_type_id, activity_type_name)
VALUES
(1, 'MATCH'),
(2, 'TRAINING');

INSERT INTO sport_website_test.locations(location_id, court_name)
VALUES
(1, 'Football Pitch 1'),
(2, 'Football Pitch 2'),
(3, 'Volleyball Pitch 1'),
(4, 'Volleyball Pitch 2'),
(5, 'Basketball Pitch 1'),
(6, 'Basketball Pitch 2');

INSERT INTO sport_website_test.reservations(reservation_id, date_time, location_id)
VALUES
(1, '2020-11-20 17:00:00', 1),
(2, '2020-11-20 18:00:00', 2),
(3, '2020-11-20 19:00:00', 3),
(4, '2020-11-20 19:30:00', 3);

INSERT INTO SPORT_WEBSITE_TEST.ACTIVITIES(ACTIVITY_ID, ACTIVITY_NAME, CAPACITY, DESCRIPTION, CREATOR_ID, ACTIVITY_TYPE_ID, IS_CANCELLED, RESERVATION_ID, TEAM_ID)
VALUES
(1,'Football Training Friday', 24, 'Football training after the season', 1, 2, CAST(0 AS BINARY(1)), 1, 2),
(2,'Football Training Friday', 24, 'Football training', 1, 2, CAST(0 AS BINARY(1)), 2, 1),
(3,'Volleyball Match against Norrebro', 12, 'Our 6th match this season', 2, 1, CAST(0 AS BINARY(1)), 3, 3),
(4,'Volleyball Match against Valby', 12, 'The last game of the season', 5, 1, CAST(0 AS BINARY(1)), 4, 4);

INSERT INTO SPORT_WEBSITE_TEST.ACTIVITY_STATUSES(STATUS_ID, USER_ID, ACTIVITY_ID)
VALUES
(1,3,1),
(1,3,2);

