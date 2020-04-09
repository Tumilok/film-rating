create table users(
    UserID number generated as identity
        constraint users_pk primary key,
    Username varchar2(20) NOT NULL,
    Firstname varchar2(30),
    Lastname varchar2(30),
    Email varchar2(50) NOT NULL
)

create table Actors(
    ActorID number generated as identity
        constraint actors_pk primary key,
    Firstname varchar2(30) not null,
    Lastname varchar2(30) not null
);

create table Directors(
    DirectorID number generated as identity
        constraint directors_pk primary key,
    Firstname varchar2(30) not null,
    Lastname varchar2(30) not null
);

create table Movies(
    MovieID number generated as identity
        constraint movies_pk primary key,
    Title varchar2(90) not null,
    Description varchar2(200),
    YearOfRelease varchar2(4) not null
);

create table Categories(
    MovieID number not null,
    Category varchar(30) not null,
    constraint c_movies_fk
        foreign key (MovieID)
        references Movies (MovieID)
);

create table MovieDirector(
    MovieID number not null,
    DirectorID number not null,
    constraint md_movies_fk
        foreign key (MovieID)
        references Movies (MovieID),
    constraint md_directors_fk
        foreign key (DirectorID)
        references Directors (DirectorID)
);

create table MovieActor(
    MovieID number not null,
    ActorID number not null,
    constraint ma_movies_fk
        foreign key (MovieID)
        references Movies (MovieID),
    constraint ma_actors_fk
        foreign key (ActorID)
        references Actors (ActorID)
);

create table Ratings(
    UserID number not null,
    ActorID number not null,
    Rating number not null,
    constraint r_users_fk
        foreign key (UserID)
        references Users (UserID),
    constraint r_actors_fk
        foreign key (ActorID)
        references Actors (ActorID),
    constraint rating_range
        check( rating>=1 and rating <=10)
);
