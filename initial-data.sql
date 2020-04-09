-- INSERTING INITIAL DATA INTO TABLES

-- TABLE: Users
INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('brownee', 'John', 'Brown', 'brown@mail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('lolyba', 'Alex', 'Walker', 'a.walker@gmail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('krooowe', 'Martha', 'Stuart', 'littleone@yahoo.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('flower003', 'Kate', 'Jackson', 'jakie@mail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('rossien', 'Mike', 'Markov', 'loly@gmail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('oopsii', 'Cristian', 'Andres', 'androcri@gmail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('holymother', 'Nina', 'Wojtek', 'nina@cool.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('floriush', 'Lena', 'Markony', 'markony@mail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('bartonis', 'Billy', 'Kren', 'krenkren200@gmail.com');

INSERT INTO Users (Username, Firstname, Lastname, Email)
VALUES ('slkgac', 'Pavel', 'Minkr', 'minkr@gmail.com');


-- TABLE: Movies
INSERT INTO Movies (Title, Description,  YearOfRelease)
VALUES ('Inception', 'Amazing movie!', '2010');

INSERT INTO Movies (Title, Description,  YearOfRelease)
VALUES ('Titanic', 'Such a great movie', '1997');

INSERT INTO Movies (Title, Description,  YearOfRelease)
VALUES ('Mad Max: Fury Road', 'Brutal', '2015');

INSERT INTO Movies (Title, Description,  YearOfRelease)
VALUES ('Knives Out', 'Very mysterious', '2019');

-- TABLE Categories
INSERT INTO Categories(Category, MovieId)
VALUES ('Action', 1);

INSERT INTO Categories(Category, MovieId)
VALUES ('Adventure', 1);

INSERT INTO Categories(Category, MovieId)
VALUES ('Drama', 2);

INSERT INTO Categories(Category, MovieId)
VALUES ('Romance', 2);

INSERT INTO Categories(Category, MovieId)
VALUES ('Action', 3);

INSERT INTO Categories(Category, MovieId)
VALUES ('Adventure', 3);

INSERT INTO Categories(Category, MovieId)
VALUES ('Sci-Fi', 3);

INSERT INTO Categories(Category, MovieId)
VALUES ('Comedy', 4);

INSERT INTO Categories(Category, MovieId)
VALUES ('Crime', 4);

INSERT INTO Categories(Category, MovieId)
VALUES ('Drama', 4);

-- TABLE Rating
INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (1, 1, 10);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (2, 1, 8);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (3, 1, 9);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (4, 2, 8);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (5, 2, 9);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (6, 2, 6);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (7, 3, 5);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (8, 3, 8);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (9, 3, 8);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (10, 4, 7);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (1, 4, 8);

INSERT INTO Ratings(UserId, MovieId, Rating)
VALUES (2, 4, 10);

-- TABLE Actors
INSERT INTO Actors(Firstname, Lastname)
VALUES ('Daniel', 'Craig');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Chris', 'Evans');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Ana', 'de Armas');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Leonardo', 'DiCaprio');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Joseph', 'Gordon-Levitt');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Elien', 'Page');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Kate', 'Winslet');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Billy', 'Zane');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Tom', 'Hardy');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Charlize', 'Theron');

INSERT INTO Actors(Firstname, Lastname)
VALUES ('Nicholas', 'Hoult');

-- TABLE MovieActor
INSERT INTO MovieActor(MovieId, ActorId)
VALUES (4, 1);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (4, 2);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (4, 3);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (1, 4);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (1, 5);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (1, 6);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (1, 9);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (2, 4);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (2, 7);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (2, 8);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (3, 9);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (3, 10);

INSERT INTO MovieActor(MovieId, ActorId)
VALUES (3, 11);

-- TABLE Directors
INSERT INTO Directors(Firstname, Lastname)
VALUES ('Cristopher','Nolan');

INSERT INTO Directors(Firstname, Lastname)
VALUES ('James','Cameron');

INSERT INTO Directors(Firstname, Lastname)
VALUES('Rian','Johnson');

INSERT INTO Directors(Firstname, Lastname)
VALUES('George','Miller');

-- TABLE MovieDirector
INSERT INTO MovieDirector(MovieId, DirectorId)
VALUES(1,1);

INSERT INTO MovieDirector(MovieId, DirectorId)
VALUES(2,2);

INSERT INTO MovieDirector(MovieId, DirectorId)
VALUES(3,4);

INSERT INTO MovieDirector(MovieId, DirectorId)
VALUES(4,3);