**CineHub: Your Cinema Nexus 🍿🎬**

## Introduction
CineHub is a Java-based backend system designed for cinema management. Leveraging modern technologies, it offers a comprehensive suite of functionalities to manage cinemas, rooms, tickets, and showtimes through a RESTful API.
Dive into the world of cinema with CineHub, a cutting-edge cinema management system that bridges movie enthusiasts 🎥 with their favorite on-screen moments. Strategically designed for a chain of cinemas spanning various cities, CineHub seamlessly integrates functionalities that cater to both cinema owners 🏢 and movie-goers 🎟️.

**Features:**
- **City-wise Cinema Listings 🌍**: Explore cinemas across multiple cities, with details about each cinema right at your fingertips.
- **Dynamic Room Insights 🪑**: Discover each cinema's individual rooms, their numbers, and seating capacities.
- **Real-time Movie Scheduling 📅**: Stay updated with the latest movies being broadcasted, ensuring you're always in the loop.
- **Reactive Seat Reservation ⚡**: A user-friendly and instantaneous booking system that guarantees you get the seat you want.
- **Caching Mechanism 🚀**: Experience lightning-fast load times with our advanced caching, ensuring content is always up to date and quickly accessible.

Whether you're a cinema enthusiast looking for the next movie to watch 🍿 or a cinema chain seeking efficient management 🔍, CineHub is the ultimate platform that synergizes cinematic experiences with top-notch technology. Join us and immerse yourself in the CineHub universe, where every day is a movie day! 🌟

## Technologies
- **Java JDK 17**
- **Spring**
- **JPA**
- **Redis**
- **Flyway**
- **Docker**

## Getting Started

### Prerequisites
- Java JDK 17
- Maven
- PostgreSQL
- Docker (optional for containerized deployment)

### Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/GGerginov/CineHub.git
   ```
2. **Navigate to the project directory**:
   ```bash
   cd CineHub
   ```
3. **Configure the `.env` file**: Set up the necessary environment variables in the `.env` file. This includes:
   - `SERVER_PORT`: The port on which the server will run.
   - `REDIS_HOST`: The hostname for the Redis server.
   - `REDIS_PORT`: The port number for the Redis server.
   - `DB_HOST`: The hostname for your database server.
   - `DB_PORT`: The port number for your database server.
   - `DB_NAME`: The name of your database.
   - `DB_USERNAME`: The username for your database.
   - `DB_PASSWORD`: The password for your database.
   
   Ensure all these values are correctly set to match your local or production environment.
4. **Build the project using Maven**:
   ```bash
   mvn clean install
   ```
5. **Run the application**:
   ```bash
   java -jar target/cinehub-0.0.1-SNAPSHOT.jar
   ```

## API Endpoints
The application exposes various endpoints, such as:

- `PUT /api/ticket/book/{id}`: Book a ticket.
- `GET /api/ticket/check`: Check a ticket.
- `GET /api/cinemas`: List all cinemas.
- `GET /api/cinemas/city/{cityName}`: Cinemas by city.
- `GET /api/rooms/movies-in-room-for-period`: Movies in a room for a specified period.
- `GET /api/rooms/upcoming-broadcasts`: Upcoming broadcasts in rooms.
- `GET /api/rooms/{slug}`: Details of a specific room.

## Contributing
Contributions are welcome. To contribute:

1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more information.

## Contact
- Project Link: [https://github.com/GGerginov/CineHub](https://github.com/GGerginov/CineHub)

---
