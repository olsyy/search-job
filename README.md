# search-job
Server-side with a Vacancies database and client for job search.

# 📢 Search Job

![Kotlin](https://img.shields.io/badge/kotlin-2.0.10-orange.svg)
![Apache 2](https://img.shields.io/badge/license-Apache2-green.svg?style=flat)

## 📓 Description
Server-side with a Vacancies and Companies database + Client-side for job search with personal Resume.

## 💡 Usage
Server-Side API:
num | type | endpoint | description
----| ------------ | ------------ | -------------------
1 | GET | /companies | Returns a list of all companies.
2 | GET | /companies/{companyId} | Returns a specific company by its ID.
3 | GET | /vacancies | Returns a list of all vacancies.
4 | GET | /vacancies/{vacancyId} | Returns a specific vacancy by its ID.
5 | GET | /resumes | Returns a list of all resumes.
6 | GET | /resumes/{resumeId} | Returns a specific resume by its ID.
6 | POST | /resumes/update/{resumeId} | Updates an existing resume.


![App work](https://github.com/lsyyx/search-job/blob/main/img/Screen_recording_20250122_165044.gif)

## 🏁 Start

1. Clone the repository:
```bash
git clone https://github.com/yourusername/search-job.git
```
2. Open the server in IntelliJ IDEA and client in Android Studio or another Kotlin-compatible IDE.
3. Sync the project with Gradle files.
4. Build and run the server and client application.
5. Access the server endpoints via http://localhost:8080. You can use Postman or any other API testing tool to interact with the API.

## 🏠 Architecture
Kotlin: The primary programming language used for the project.
Ui: XML.
Ktor Framework: The server is built using the Ktor framework for handling HTTP requests and routing.
SQL Exposed: For interacting with the H2 database and mapping data to entities.
H2 Database: The database stores the zones and incidents, and H2 is used for local storage during development.
Clean Architecture:  The project follows Clean Architecture principles for maintainability, scalability, and testability, ensuring a clear separation of concerns between the server's layers.

## License
This project is licensed under the Apache License 2.0.
