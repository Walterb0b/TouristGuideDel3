CREATE DATABASE touristGuideDel3;

USE touristGuideDel3;

CREATE TABLE tourist_attraction (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL UNIQUE,
                                    description TEXT NOT NULL,
                                    city VARCHAR(100) NOT NULL,
                                    price DOUBLE NOT NULL
);

CREATE TABLE tag (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE attraction_tag (
                                attraction_id INT,
                                tag_id INT,
                                PRIMARY KEY(attraction_id, tag_id),
                                FOREIGN KEY(attraction_id) REFERENCES tourist_attraction(id) ON DELETE CASCADE,
                                FOREIGN KEY(tag_id) REFERENCES tag(id)
);