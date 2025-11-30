
DROP DATABASE IF EXISTS BIBLIOTECA_DB;
CREATE DATABASE BIBLIOTECA_DB;
USE BIBLIOTECA_DB;

CREATE TABLE ESTADO (
	ID INT PRIMARY KEY,
    DESCRIPCION VARCHAR(20) NOT NULL
);

CREATE TABLE USUARIO (
	ID INT AUTO_INCREMENT PRIMARY KEY,
	NOMBRE VARCHAR(100),
    EMAIL VARCHAR(100) UNIQUE,
    PASSWORD VARCHAR(100),
    ROL ENUM('ESTUDIANTE','BIBLIOTECARIO') DEFAULT 'ESTUDIANTE',
    ID_ESTADO INT DEFAULT 1,
    FOREIGN KEY (ID_ESTADO) REFERENCES ESTADO(ID)
);

CREATE TABLE LIBRO (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    TITULO VARCHAR(100),
    AUTOR VARCHAR(100),
    CATEGORIA VARCHAR(100),
    DISPONIBLE BOOLEAN DEFAULT TRUE,
    ID_ESTADO INT DEFAULT 1,
    FOREIGN KEY (ID_ESTADO) REFERENCES ESTADO(ID)
);

CREATE TABLE PRESTAMO (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_USUARIO INT NOT NULL,
    ID_LIBRO INT NOT NULL,
    FECHA_SALIDA DATE DEFAULT (CURRENT_DATE),
    FECHA_DEVOLUCION DATE,
    ID_ESTADO INT DEFAULT 1,
    FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),
    FOREIGN KEY (ID_LIBRO) REFERENCES LIBRO(ID),
    FOREIGN KEY (ID_ESTADO) REFERENCES ESTADO(ID)
);

CREATE TABLE RESERVA(
	ID INT AUTO_INCREMENT PRIMARY KEY,
	ID_USUARIO INT NOT NULL,
    ID_LIBRO INT NOT NULL,
    FECHA_RESERVA DATE DEFAULT (CURRENT_DATE),
    ID_ESTADO INT DEFAULT 1,
	FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),
    FOREIGN KEY (ID_LIBRO) REFERENCES LIBRO(ID),
    FOREIGN KEY (ID_ESTADO) REFERENCES ESTADO(ID)
);

CREATE TABLE MULTA(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_USUARIO INT NOT NULL,
    ID_PRESTAMO INT NOT NULL,
    MONTO DECIMAL(10,2) NOT NULL,
    FECHA_GENERACION DATE DEFAULT (CURRENT_DATE),
    ID_ESTADO INT DEFAULT 1,
	FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),
    FOREIGN KEY (ID_PRESTAMO) REFERENCES PRESTAMO(ID),
    FOREIGN KEY (ID_ESTADO) REFERENCES ESTADO(ID)
);

-- ===================================================================================
-- 1. ESTADOS (Fijos)
-- ===================================================================================
INSERT INTO ESTADO (ID, DESCRIPCION) VALUES
(1, 'ACTIVO'),
(2, 'INACTIVO'),
(3, 'PENDIENTE'), 
(4, 'PAGADA');

-- ===================================================================================
-- 2. USUARIOS (10 Usuarios Activos)
-- ===================================================================================
INSERT INTO USUARIO (NOMBRE, EMAIL, PASSWORD, ROL, ID_ESTADO) VALUES
('Carlos Bibliotecario', 'admin@biblio.com', 'admin', 'BIBLIOTECARIO', 1),
('Ana Estudiante', 'ana@est.com', '1234', 'ESTUDIANTE', 1),
('Pedro Perez', 'pedro@est.com', '1234', 'ESTUDIANTE', 1),
('Maria Rodriguez', 'maria@est.com', '1234', 'ESTUDIANTE', 1),
('Luis Gonzalez', 'luis@est.com', '1234', 'ESTUDIANTE', 1),
('Sofia Martinez', 'sofia@est.com', '1234', 'ESTUDIANTE', 1),
('Jorge Ramirez', 'jorge@est.com', '1234', 'ESTUDIANTE', 1),
('Elena Fernandez', 'elena@est.com', '1234', 'ESTUDIANTE', 1),
('Miguel Torres', 'miguel@est.com', '1234', 'ESTUDIANTE', 1),
('Lucia Sanchez', 'lucia@est.com', '1234', 'ESTUDIANTE', 1),
-- Pruebas y muestras
('Admin Biblioteca', 'a', 'a', 'BIBLIOTECARIO', 1),
('Gabriel Osorio', 'g', 'g', 'ESTUDIANTE', 1);

-- ===================================================================================
-- 3. LIBROS (100 Libros)
-- ===================================================================================
INSERT INTO LIBRO (TITULO, AUTOR, CATEGORIA, DISPONIBLE, ID_ESTADO) VALUES
-- Libros de Tecnología (1-20)
('Clean Code', 'Robert C. Martin', 'Tecnologia', TRUE, 1),     -- ID 1 (Estuvo prestado, ya se devolvió)
('Java: The Complete Reference', 'Herbert Schildt', 'Tecnologia', FALSE, 1), -- ID 2 (Prestado Activo)
('Design Patterns', 'Erich Gamma', 'Tecnologia', FALSE, 1),   -- ID 3 (Reservado)
('Introduction to Algorithms', 'Thomas H. Cormen', 'Tecnologia', FALSE, 1), -- ID 4 (Reservado)
('The Pragmatic Programmer', 'Andrew Hunt', 'Tecnologia', TRUE, 1),
('Head First Java', 'Kathy Sierra', 'Tecnologia', TRUE, 1),
('Effective Java', 'Joshua Bloch', 'Tecnologia', TRUE, 1),
('Python Crash Course', 'Eric Matthes', 'Tecnologia', TRUE, 1),
('JavaScript: The Good Parts', 'Douglas Crockford', 'Tecnologia', TRUE, 1),
('You Dont Know JS', 'Kyle Simpson', 'Tecnologia', TRUE, 1),
('Spring Boot in Action', 'Craig Walls', 'Tecnologia', TRUE, 1),
('Docker Deep Dive', 'Nigel Poulton', 'Tecnologia', TRUE, 1),
('Kubernetes Up and Running', 'Kelsey Hightower', 'Tecnologia', TRUE, 1),
('Refactoring', 'Martin Fowler', 'Tecnologia', TRUE, 1),
('Code Complete', 'Steve McConnell', 'Tecnologia', TRUE, 1),
('The Mythical Man-Month', 'Fred Brooks', 'Tecnologia', TRUE, 1),
('Cracking the Coding Interview', 'Gayle Laakmann', 'Tecnologia', TRUE, 1),
('Database System Concepts', 'Abraham Silberschatz', 'Tecnologia', TRUE, 1),
('Computer Networking', 'James Kurose', 'Tecnologia', TRUE, 1),
('Operating System Concepts', 'Abraham Silberschatz', 'Tecnologia', TRUE, 1),

-- Novelas Clásicas y Modernas (21-40)
('Cien Años de Soledad', 'Gabriel Garcia Marquez', 'Novela', TRUE, 1),
('Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela', TRUE, 1),
('1984', 'George Orwell', 'Novela', TRUE, 1),
('Un Mundo Feliz', 'Aldous Huxley', 'Novela', TRUE, 1),
('Fahrenheit 451', 'Ray Bradbury', 'Novela', TRUE, 1),
('El Gran Gatsby', 'F. Scott Fitzgerald', 'Novela', TRUE, 1),
('Matar a un Ruiseñor', 'Harper Lee', 'Novela', TRUE, 1),
('Orgullo y Prejuicio', 'Jane Austen', 'Novela', TRUE, 1),
('Crimen y Castigo', 'Fiódor Dostoyevski', 'Novela', TRUE, 1),
('La Metamorfosis', 'Franz Kafka', 'Novela', TRUE, 1),
('El Retrato de Dorian Gray', 'Oscar Wilde', 'Novela', TRUE, 1),
('Los Miserables', 'Victor Hugo', 'Novela', TRUE, 1),
('Anna Karenina', 'Lev Tolstói', 'Novela', TRUE, 1),
('Madame Bovary', 'Gustave Flaubert', 'Novela', TRUE, 1),
('En Busca del Tiempo Perdido', 'Marcel Proust', 'Novela', TRUE, 1),
('Ulises', 'James Joyce', 'Novela', TRUE, 1),
('La Odisea', 'Homero', 'Novela', TRUE, 1),
('La Iliada', 'Homero', 'Novela', TRUE, 1),
('La Divina Comedia', 'Dante Alighieri', 'Novela', TRUE, 1),
('Hamlet', 'William Shakespeare', 'Novela', TRUE, 1),

-- Fantasía y Ciencia Ficción (41-60)
('Harry Potter y la Piedra Filosofal', 'J.K. Rowling', 'Fantasia', TRUE, 1),
('Harry Potter y la Cámara Secreta', 'J.K. Rowling', 'Fantasia', TRUE, 1),
('Harry Potter y el Prisionero de Azkaban', 'J.K. Rowling', 'Fantasia', TRUE, 1),
('El Señor de los Anillos: La Comunidad', 'J.R.R. Tolkien', 'Fantasia', TRUE, 1),
('El Señor de los Anillos: Las Dos Torres', 'J.R.R. Tolkien', 'Fantasia', TRUE, 1),
('El Señor de los Anillos: El Retorno del Rey', 'J.R.R. Tolkien', 'Fantasia', TRUE, 1),
('El Hobbit', 'J.R.R. Tolkien', 'Fantasia', TRUE, 1),
('Juego de Tronos', 'George R.R. Martin', 'Fantasia', TRUE, 1),
('Choque de Reyes', 'George R.R. Martin', 'Fantasia', TRUE, 1),
('Tormenta de Espadas', 'George R.R. Martin', 'Fantasia', TRUE, 1),
('Festín de Cuervos', 'George R.R. Martin', 'Fantasia', TRUE, 1),
('Danza de Dragones', 'George R.R. Martin', 'Fantasia', TRUE, 1),
('El Nombre del Viento', 'Patrick Rothfuss', 'Fantasia', TRUE, 1),
('El Temor de un Hombre Sabio', 'Patrick Rothfuss', 'Fantasia', TRUE, 1),
('Dune', 'Frank Herbert', 'Ciencia Ficcion', TRUE, 1),
('El Mesías de Dune', 'Frank Herbert', 'Ciencia Ficcion', TRUE, 1),
('Hijos de Dune', 'Frank Herbert', 'Ciencia Ficcion', TRUE, 1),
('Fundación', 'Isaac Asimov', 'Ciencia Ficcion', TRUE, 1),
('Fundación e Imperio', 'Isaac Asimov', 'Ciencia Ficcion', TRUE, 1),
('Segunda Fundación', 'Isaac Asimov', 'Ciencia Ficcion', TRUE, 1),

-- Ciencia y Matemáticas (61-80)
('Breve Historia del Tiempo', 'Stephen Hawking', 'Ciencia', TRUE, 1),
('El Gen Egoísta', 'Richard Dawkins', 'Ciencia', TRUE, 1),
('Cosmos', 'Carl Sagan', 'Ciencia', TRUE, 1),
('Sapiens', 'Yuval Noah Harari', 'Historia', TRUE, 1),
('Homo Deus', 'Yuval Noah Harari', 'Historia', TRUE, 1),
('Principia Mathematica', 'Isaac Newton', 'Ciencia', TRUE, 1),
('El Origen de las Especies', 'Charles Darwin', 'Ciencia', TRUE, 1),
('Cálculo de una Variable', 'James Stewart', 'Matematicas', TRUE, 1),
('Cálculo Multivariable', 'James Stewart', 'Matematicas', TRUE, 1),
('Álgebra Lineal', 'David C. Lay', 'Matematicas', TRUE, 1),
('Física Universitaria Vol 1', 'Sears Zemansky', 'Ciencia', TRUE, 1),
('Física Universitaria Vol 2', 'Sears Zemansky', 'Ciencia', TRUE, 1),
('Química: La Ciencia Central', 'Brown LeMay', 'Ciencia', TRUE, 1),
('Biología', 'Neil Campbell', 'Ciencia', TRUE, 1),
('Estadística para Ingenieros', 'Walpole', 'Matematicas', TRUE, 1),
('Ecuaciones Diferenciales', 'Dennis G. Zill', 'Matematicas', TRUE, 1),
('Matemáticas Discretas', 'Richard Johnsonbaugh', 'Matematicas', TRUE, 1),
('Geometría Analítica', 'Lehmann', 'Matematicas', TRUE, 1),
('Teoría de Números', 'George Andrews', 'Matematicas', TRUE, 1),
('El Universo en una Cáscara de Nuez', 'Stephen Hawking', 'Ciencia', TRUE, 1),

-- Historia y Varios (81-100)
('El Arte de la Guerra', 'Sun Tzu', 'Historia', TRUE, 1),
('Historia del Siglo XX', 'Eric Hobsbawm', 'Historia', TRUE, 1),
('La Segunda Guerra Mundial', 'Antony Beevor', 'Historia', TRUE, 1),
('Los Cañones de Agosto', 'Barbara Tuchman', 'Historia', TRUE, 1),
('Guns, Germs, and Steel', 'Jared Diamond', 'Historia', TRUE, 1),
('Meditaciones', 'Marco Aurelio', 'Filosofia', TRUE, 1),
('La República', 'Platón', 'Filosofia', TRUE, 1),
('Ética a Nicómaco', 'Aristóteles', 'Filosofia', TRUE, 1),
('Así Habló Zaratustra', 'Friedrich Nietzsche', 'Filosofia', TRUE, 1),
('Crítica de la Razón Pura', 'Immanuel Kant', 'Filosofia', TRUE, 1),
('El Príncipe', 'Maquiavelo', 'Politica', TRUE, 1),
('El Capital', 'Karl Marx', 'Economia', TRUE, 1),
('La Riqueza de las Naciones', 'Adam Smith', 'Economia', TRUE, 1),
('Padre Rico, Padre Pobre', 'Robert Kiyosaki', 'Economia', TRUE, 1),
('Piense y Hágase Rico', 'Napoleon Hill', 'Economia', TRUE, 1),
('El Poder del Ahora', 'Eckhart Tolle', 'Autoayuda', TRUE, 1),
('Los 7 Hábitos', 'Stephen Covey', 'Autoayuda', TRUE, 1),
('Inteligencia Emocional', 'Daniel Goleman', 'Psicologia', TRUE, 1),
('Pensar Rápido, Pensar Despacio', 'Daniel Kahneman', 'Psicologia', TRUE, 1),
('El Hombre en Busca de Sentido', 'Viktor Frankl', 'Psicologia', TRUE, 1);

-- ===================================================================================
-- 4. PRÉSTAMOS (2 Registros)
-- ===================================================================================

-- Préstamo 1: DEVUELTO (Ana Estudiante devolvió Clean Code)
INSERT INTO PRESTAMO (ID_USUARIO, ID_LIBRO, FECHA_SALIDA, FECHA_DEVOLUCION, ID_ESTADO) VALUES
(2, 1, '2025-10-01', '2025-10-15', 2); -- 2 = INACTIVO/DEVUELTO

-- Préstamo 2: ACTIVO (Pedro Perez tiene Java: The Complete Reference)
INSERT INTO PRESTAMO (ID_USUARIO, ID_LIBRO, FECHA_SALIDA, FECHA_DEVOLUCION, ID_ESTADO) VALUES
(3, 2, CURRENT_DATE, NULL, 1); -- 1 = ACTIVO

-- ===================================================================================
-- 5. RESERVAS (2 Activas)
-- ===================================================================================

-- Reserva 1: Maria Rodriguez reservó Design Patterns
INSERT INTO RESERVA (ID_USUARIO, ID_LIBRO, FECHA_RESERVA, ID_ESTADO) VALUES
(4, 3, CURRENT_DATE, 1);

-- Reserva 2: Luis Gonzalez reservó Introduction to Algorithms
INSERT INTO RESERVA (ID_USUARIO, ID_LIBRO, FECHA_RESERVA, ID_ESTADO) VALUES
(5, 4, CURRENT_DATE, 1);

-- ===================================================================================
-- 6. MULTAS (2 Registros)
-- ===================================================================================

-- Multa 1: PAGADA (Asociada al préstamo 1 de Ana)
INSERT INTO MULTA (ID_USUARIO, ID_PRESTAMO, MONTO, FECHA_GENERACION, ID_ESTADO) VALUES
(2, 1, 2500.00, '2025-10-15', 4); -- 4 = PAGADA

-- Multa 2: ACTIVA/PENDIENTE (Asociada también al préstamo 1 de Ana
-- Nota: Un préstamo devuelto puede generar deuda pendiente si no se pagó al momento.
INSERT INTO MULTA (ID_USUARIO, ID_PRESTAMO, MONTO, FECHA_GENERACION, ID_ESTADO) VALUES
(2, 1, 500.00, '2025-10-15', 3); -- 3 = PENDIENTE




