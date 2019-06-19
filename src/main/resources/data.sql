DROP TABLE IF EXISTS PLANET;
CREATE TABLE PLANET (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  node VARCHAR(250) NOT NULL,
  name VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS ROUTE;
CREATE TABLE ROUTE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  origin VARCHAR(250) NOT NULL,
  destination VARCHAR(250) NOT NULL,
  distance DECIMAL
);

DROP TABLE IF EXISTS TRAFFIC;
CREATE TABLE TRAFFIC (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    origin VARCHAR(250) NOT NULL,
    destination VARCHAR(250) NOT NULL,
    traffic DECIMAL
);