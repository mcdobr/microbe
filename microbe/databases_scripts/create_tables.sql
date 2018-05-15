CREATE DATABASE IF NOT EXISTS microbe;
USE microbe;

CREATE TABLE IF NOT EXISTS leagues (
	leagueID INT UNSIGNED AUTO_INCREMENT,
	leagueName VARCHAR(50) NOT NULL,
	
	CONSTRAINT pk_league PRIMARY KEY (leagueID),
	CONSTRAINT uk_league UNIQUE KEY (leagueName)
);

CREATE TABLE IF NOT EXISTS coaches (
	coachID INT UNSIGNED AUTO_INCREMENT,
	coachName VARCHAR(50) NOT NULL,
	
	CONSTRAINT pk_coach PRIMARY KEY (coachID)
);

CREATE TABLE IF NOT EXISTS teams (
	teamID INT UNSIGNED AUTO_INCREMENT,
	teamName VARCHAR(50) NOT NULL,
	leagueID INT UNSIGNED NOT NULL,
	coachID INT UNSIGNED,
	
	CONSTRAINT pk_team PRIMARY KEY (teamID),
	CONSTRAINT uk_team UNIQUE KEY (teamName),
	CONSTRAINT fk_league_team FOREIGN KEY (leagueID) REFERENCES leagues(leagueID),
	CONSTRAINT fk_coach_team FOREIGN KEY (coachID) REFERENCES coaches(coachID)
);

CREATE TABLE IF NOT EXISTS matches (
	matchID	INT UNSIGNED AUTO_INCREMENT,
	homeTeamID INT UNSIGNED NOT NULL,
	awayTeamID INT UNSIGNED NOT NULL,
	playDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
	
	CONSTRAINT pk_match PRIMARY KEY (matchID),
	CONSTRAINT uk_match UNIQUE KEY (homeTeamID, awayTeamID, playDate),
	CONSTRAINT ck_match CHECK (homeTeamID != awayTeamID)
);

/*
CREATE TABLE IF NOT EXISTS players (
	playerID INT UNSIGNED AUTO_INCREMENT,
	playerName VARCHAR(50) NOT NULL,
	position ENUM('striker', 'midfielder', 'defender', 'goalkeeper') NOT NULL,
	teamID INT UNSIGNED NOT NULL,
	
	CONSTRAINT pk_player PRIMARY KEY (playerID),
	CONSTRAINT fk_team_player FOREIGN KEY (teamID) REFERENCES teams(teamID)
);

CREATE TABLE IF NOT EXISTS goals (
	goalID INT UNSIGNED AUTO_INCREMENT,
	matchID INT UNSIGNED NOT NULL,
	teamID INT UNSIGNED NOT NULL,
	playerID INT UNSIGNED NOT NULL,
	goalTime FLOAT UNSIGNED NOT NULL,
	
	CONSTRAINT pk_goal PRIMARY KEY (goalID),
	CONSTRAINT uk_goal UNIQUE KEY (matchID, teamID, playerID, goalTime),
	CONSTRAINT ck_goal CHECK (goalTime < 120)
);
*/


/* Leagues inserts */
INSERT INTO leagues VALUES (default, 'Liga 1');
INSERT INTO leagues VALUES (default, 'Premier League');
INSERT INTO leagues VALUES (default, 'Bundesliga');
INSERT INTO leagues VALUES (default, 'La Liga');
INSERT INTO leagues VALUES (default, 'Serie A');

/* Coaches inserts */
INSERT INTO coaches VALUES (default, 'Ioan Andone');
INSERT INTO coaches VALUES (default, 'Gheorghe Hagi');
INSERT INTO coaches VALUES (default, 'Mircea Lucescu');
INSERT INTO coaches VALUES (default, 'Zinedine Zidane');
INSERT INTO coaches VALUES (default, 'Pep Guardiola');
INSERT INTO coaches VALUES (default, 'Razvan Lucescu');
INSERT INTO coaches VALUES (default, 'Ernesto Valverde');

/* Teams inserts */
INSERT INTO teams VALUES (default, 'Steaua Bucuresti', 1, 3);
INSERT INTO teams VALUES (default, 'Dinamo Bucuresti', 1, 1);
INSERT INTO teams VALUES (default, 'Rapid Bucuresti', 1, 6);
INSERT INTO teams VALUES (default, 'Real Madrid', 4, 4);
INSERT INTO teams VALUES (default, 'FC Barcelona', 4, 7);

/* Matches inserts */
INSERT INTO matches VALUES (default, 1, 2, '2018-01-07 20:30:00');
INSERT INTO matches VALUES (default, 1, 3, '2018-02-12 21:45:00');
INSERT INTO matches VALUES (default, 2, 1, '2018-03-02 16:45:00');
INSERT INTO matches VALUES (default, 3, 1, '2018-06-06 18:30:00');
INSERT INTO matches VALUES (default, 2, 3, '2018-05-21 19:00:00');

/* Players inserts */
/*
INSERT INTO players VALUES (default, 'Banel Nicolita', 'midfielder', 1);
INSERT INTO players VALUES (default, 'Razvan Danciulescu', 'striker', 2);
INSERT INTO players VALUES (default, 'Lionel Messi', 'striker', 5);
INSERT INTO players VALUES (default, 'Cristiano Ronaldo', 'striker', 4);
INSERT INTO players VALUES (default, 'Wayne Rooney', 'striker', 3);
*/
/* Goal inserts */
/*
INSERT INTO goals VALUES (default, 1, 1, 1, 45);
INSERT INTO goals VALUES (default, 2, 3, 5, 20);
INSERT INTO goals VALUES (default, 3, 1, 1, 80);
INSERT INTO goals VALUES (default, 2, 1, 1, 55);
INSERT INTO goals VALUES (default, 1, 2, 2, 63);


 SELECT
	least (homeTeamID, awayTeamID) team1,
	greatest (homeTeamID, awayTeamID) team2,
	(SELECT COUNT(*) FROM goals)
FROM matches


SELECT 
	least (homeTeamID, awayTeamID) team1,
	greatest (homeTeamID, awayTeamID) team2,
	
	(SELECT COUNT(*)
	FROM goals
	WHERE teamID = team1
	GROUP BY matchID) AS team1Goals,
	
	(SELECT COUNT(*)
	FROM goals
	WHERE teamID = team2
	GROUP BY matchID) AS team2Goals
	
	
FROM matches;
*/

/*CREATE TABLE IF NOT EXISTS team(
	team_name VARCHAR(30),
	games_won INTEGER UNSIGNED NOT NULL DEFAULT 0,
	games_draw INTEGER UNSIGNED NOT NULL DEFAULT 0,
	games_lost INTEGER UNSIGNED NOT NULL DEFAULT 0,
	games_played INTEGER UNSIGNED AS (games_won + games_draw + games_lost),
	goal_scored INTEGER UNSIGNED NOT NULL DEFAULT 0,
	goal_received INTEGER UNSIGNED NOT NULL DEFAULT 0,
	goal_diff INTEGER UNSIGNED AS (goal_scored - goal_received),
	points INTEGER UNSIGNED AS (3 * games_won + games_draw),
	
	CONSTRAINT pk_microbe_team_teamName PRIMARY KEY (team_name)/*,
	CONSTRAINT ck_microbe_team_intsArePositive CHECK (games_won >= 0 AND games_draw >= 0 AND games_lost >= 0 AND goal_scored >= 0 AND goal_received >= 0)

);*/
/*
CREATE TABLE IF NOT EXISTS fixture(
	home_team VARCHAR(30),
	away_team VARCHAR(30),
	play_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	goals_home INTEGER UNSIGNED NOT NULL DEFAULT 0,
	goals_away INTEGER UNSIGNED NOT NULL DEFAULT 0,
	
	CONSTRAINT pk_microbe_fixture PRIMARY KEY (home_team, away_team, play_date),
	CONSTRAINT fk_microbe_fixture_homeTeam FOREIGN KEY (home_team) REFERENCES team(team_name),
	CONSTRAINT fk_microbe_fixture_awayTeam FOREIGN KEY (away_team) REFERENCES team(team_name)
	/*CONSTRAINT ck_microbe_fixture_goalsArePositive CHECK (goals_home >= 0 AND)
);
*/

/*
INSERT INTO team VALUES('Steaua Bucuresti', 2, 1, 0, default, 4, 2, default, default);
INSERT INTO team VALUES('CFR Cluj', 1, 3, 0, default, 4, 3, default, default);
INSERT INTO team VALUES('Viitorul', 5, 1, 0, default, 8, 4, default, default);
INSERT INTO team VALUES('Universitatea Craiova', 1, 3, 0, default, 2, 1, default, default);
INSERT INTO team VALUES('CSM Iași', 5, 1, 3, default, 7, 4, default, default);
*/

/*


SELECT matchID, COUNT(*)
FROM goals
GROUP BY matchID;


SELECT	teamID,
		teamName,
		
		(SELECT COUNT(*)
		FROM matches 
		WHERE homeTeamID = teamID or awayTeamID = teamID) AS gamesPlayed,
		
		(SELECT COUNT(*)
		FROM matches
		WHERE homeTeamID = teamID or awayTeamID = teamID
		GROUP BY homeTeamID, awayTeamID) AS gamesWon
		
FROM teams
WHERE leagueID = 1;


SELECT COUNT(*) AS gamesWon
FROM matches
WHERE homeTeamID = 1 or awayTeamID = 1
GROUP BY (matchID);*/