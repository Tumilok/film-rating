# film-rating
This repository contains database class project

# Opis projektu

Aplikacja służąca do dodawania filmów do bazy oraz ich oceniania przez użytkowników. Połączona jest z bazą Oracle SQL na serwerze AGH. Został użyty framework Hibernate.

Klasy, które mapują encje bazy danych:

 - Actor
 - Director
 - Movie
 - Rating
 - User
  
Klasa Authentication odpowiada za dodawanie i pobieranie danych z bazy:

  - logowanie użytkownika (metoda login)
  - rejestracja użytkownika (metoda addUser)
  - pobieranie list filmów, aktorów, reżyserów
  - dodawanie ocen, filmów, aktorów, reżyserów
  
Hasła zapisywane są w bazie danych w postaci zaszyfrowanej, wszystkie metody odpowiedzialne za szyfrowanie znajdują się w klasie Authentication.

Interfejs użytkownika został napisany w następujących klasach:

  - Login
  - Register
  - FilmList
  - AddFilm
  - MovieDetails
  
Aby móc wyświetlać listę filmów, dodawać pozycje oraz oceniać, użytkownik musi najpierw sie zalogować lub zarejestrować.
