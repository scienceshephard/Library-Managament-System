# 📚 Library Management System

A full-stack Library Management System built with **Spring Boot** (backend) and **JavaFX** (frontend). This application allows users to manage books in a library with full CRUD (Create, Read, Update, Delete) operations.

---

## 🏗️ Project Structure

```
Library Management System/
├── library-backend/     # Spring Boot REST API
│   └── src/main/java/com/demo/
│       ├── controllers/     # REST Controllers
│       ├── dto/             # Data Transfer Objects
│       ├── model/           # Entity classes
│       ├── repo/            # JPA Repositories
│       ├── service/         # Business logic
│       └── exceptions/      # Custom exceptions
│
└── library-frontend/    # JavaFX Desktop Application
    └── src/main/java/com/
        ├── controller/      # FXML Controllers
        ├── model/           # Data models (Book, PageResponse)
        ├── service/         # HTTP Client services
        └── util/            # Utility classes
```

---

## 🔧 Technologies Used

### Backend
| Technology | Version |
|------------|---------|
| Java | 17 |
| Spring Boot | 4.0.1 |
| Spring Data JPA | Latest |
| H2 Database | In-memory |
| Maven | Latest |

### Frontend
| Technology | Version |
|------------|---------|
| Java | 17 |
| JavaFX | 21.0.2 |
| Jackson Databind | 2.20.1 |
| Maven | Latest |

---

## ✨ Features

- **📖 View Books** - Display all books in a paginated table view
- **🔍 Search Books** - Filter books by title or author
- **📄 Pagination** - Navigate through book pages with Previous/Next controls
- **➕ Add Books** - Add new books with title, author, ISBN, and published date
- **✏️ Update Books** - Modify existing book information
- **🗑️ Delete Books** - Remove books from the library
- **🔄 Refresh** - Reload the book list from the server

---

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.6+** or use the included Maven wrapper
- **JavaFX SDK 21** (for frontend development)

---

## 🚀 Getting Started

### Step 1: Clone the Repository

```bash
git clone https://github.com/scienceshephard/Library-Managament-System.git
cd Library-Managament-System
```

### Step 2: Run the Backend

Navigate to the backend directory and start the Spring Boot application:

```bash
cd library-backend

# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# OR using installed Maven
mvn spring-boot:run
```

The backend server will start on **http://localhost:8080**

> **Note:** The backend uses an **H2 in-memory database**, so all data will be reset when the server restarts.

### Step 3: Run the Frontend

Open a **new terminal**, navigate to the frontend directory, and run the JavaFX application:

```bash
cd library-frontend

# Using Maven Wrapper
./mvnw javafx:run

# OR using installed Maven
mvn javafx:run
```

The desktop application window will open automatically.

---

## 🌐 API Endpoints

The backend exposes the following REST API endpoints:

| Method | Endpoint | Parameters | Description |
|--------|----------|------------|-------------|
| `GET` | `/api/books` | `page`, `size` | Retrieve paginated books |
| `GET` | `/api/books/search` | `query`, `page`, `size` | Search books by title or author |
| `GET` | `/api/books/{id}` | - | Retrieve a specific book |
| `POST` | `/api/books` | - | Add a new book |
| `PUT` | `/api/books/{id}` | - | Update an existing book |
| `DELETE` | `/api/books/{id}` | - | Delete a book |

### Query Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number (0-indexed) |
| `size` | int | 10 | Number of items per page |
| `query` | string | - | Search term for title/author |

### Sample Request Body (POST/PUT)

```json
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0-7432-7356-5",
  "publishedDate": "1925-04-10"
}
```

### Sample Paginated Response (GET)

```json
{
  "content": [
    {
      "id": 1,
      "title": "The Great Gatsby",
      "author": "F. Scott Fitzgerald",
      "isbn": "978-0-7432-7356-5",
      "publishedDate": "1925-04-10"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

---

## 🗄️ Database Configuration

The backend uses an **H2 in-memory database** for simplicity. Access the H2 Console at:

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

---

## 📸 Application Screenshot

The JavaFX frontend provides a clean interface with:
- A **search bar** to filter books by title or author
- A **table** displaying all books (Title, Author, ISBN, Published Date)
- **Pagination controls** (Previous/Next buttons with page indicator)
- Input fields for book information
- Action buttons for Add, Update, Delete, and Refresh

---

## 📦 Book Entity

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated unique identifier |
| `title` | String | Book title |
| `author` | String | Author name |
| `isbn` | String | ISBN number |
| `publishedDate` | LocalDate | Publication date |

---

## 🛠️ Building the Project

### Build Backend JAR

```bash
cd library-backend
./mvnw clean package
```

The JAR file will be created in `target/library-backend-0.0.1-SNAPSHOT.jar`

### Run the JAR

```bash
java -jar target/library-backend-0.0.1-SNAPSHOT.jar
```

---

## ⚠️ Troubleshooting

### Frontend can't connect to backend

1. Ensure the backend is running on `http://localhost:8080`
2. Check for any firewall blocking the port
3. Verify no other application is using port 8080

### JavaFX module not found

If you encounter JavaFX module errors, ensure:
1. JavaFX SDK is properly installed
2. The `JAVA_HOME` environment variable is set correctly
3. You're using Java 17 or higher

### Maven build fails

Try cleaning the project:

```bash
./mvnw clean
./mvnw install
```

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 👤 Author

**Science Shephard**

- GitHub: [@scienceshephard](https://github.com/scienceshephard)

---

<p align="center">
  Made with ❤️ using Spring Boot and JavaFX
</p>
