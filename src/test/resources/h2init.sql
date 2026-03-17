DROP TABLE IF EXISTS attraction_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS tourist_attraction;

CREATE TABLE tourist_attraction (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(100) NOT NULL UNIQUE,
                                    description VARCHAR(500),
                                    city VARCHAR(100) NOT NULL,
                                    price DOUBLE NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tag (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE attraction_tag (
                                attraction_id BIGINT,
                                tag_id BIGINT,
                                PRIMARY KEY(attraction_id, tag_id),
                                FOREIGN KEY(attraction_id) REFERENCES tourist_attraction(id) ON DELETE CASCADE,
                                FOREIGN KEY(tag_id) REFERENCES tag(id)
);

-- init-data
INSERT INTO tourist_attraction (name, description, city, price) VALUES ('Tivoli', 'sjovt', 'COPENHAGEN', 100.0);
INSERT INTO tourist_attraction (name, description, city, price) VALUES ('Rundetaarn', 'højt', 'COPENHAGEN', 150.0);

INSERT INTO tag (name) VALUES ('KID_FRIENDLY');
INSERT INTO tag (name) VALUES ('HISTORIC');
INSERT INTO tag (name) VALUES ('ENTERTAINMENT');

INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (1, 1);
INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (1, 3);
INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (2, 2);
