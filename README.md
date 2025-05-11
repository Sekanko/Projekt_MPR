# Project MPR
## Introducing: Computer Table Manager 💼
 
### 📖 Background
This was my project for the "Programming Methods" class.

Idea is straightforward: you manage a list of computers with the ability to
add, edit, delete or even generate PDF with the computer details.
The purpose of this project and entire class was to learn Spring Boot, Gradle/Maven,
Unit tests, working with different dependencies and REST API.

### 🛠️ Technologies Used:
- **Frontend**: HTML, CSS, JavaScript (used for small animation scripts)
- **Backend**: Java 21, Spring Boot, Thymeleaf, MySQL
- **Additional**: IntelliJ IDEA, Gradle, MySQL Workbench, Git

### 🧱 Build Instructions:
1. Clone the Repository
2. Create a local MySQL database
3. Set the following environment variables:

```
DATABASE_USER=your_username
DATABASE_PASSWORD=your_password
DATABASE_URL=your_database_url
```

> 💡 Example:
> ```
> DATABASE_USER=dataManager
> DATABASE_PASSWORD=123
> DATABASE_URL=mysql://localhost:3306/STFanDatabase
> ```

4. Run:
- Client
- API


### 🔗 Application Endpoint

The application should run at:
```
http://localhost:8082/view/all
```
### ⚙️ Tests
**API:**

Run all tests in the API module

**Client:**

To run Client tests, start both the Client and API modules using the `test`
profile. Then you can run all tests in the Client module.

### ❗ Important ❗
Restart the API module before running Client tests again—otherwise, 
Selenium tests may fail.

## 💬 My comment
Even though I don’t see this as a project worth continuing, I must say it
gave me a lot of satisfaction. What I found most rewarding was 
building the `ComputerService` in the API module — 
especially the flexible functions that, looking back, 
were unnecessary for such a small project 😅.

It was my first real experience with Spring Boot, and I learned 
a lot throughout its development. Now I can proudly leave 
it as a memory of my very first steps with Spring Boot.

## 🙏 Thank You for Your Time
