DROP TABLE IF EXISTS transformer;
CREATE TABLE transformer (
  id SMALLSERIAL  PRIMARY KEY,
  courage integer NOT NULL,
  endurance integer NOT NULL,
  firepower integer NOT NULL,
  intelligence integer NOT NULL,
  name varchar(255) NOT NULL,
  rank integer NOT NULL,
  skill integer NOT NULL,
  speed integer NOT NULL,
  strength integer NOT NULL,
  type varchar(255) NOT NULL
);
