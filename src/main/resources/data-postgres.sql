INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (4,2,9,9,6,'Bluestreak',5,7,7,6,'Autobot');
INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (5,4,4,4,4,'Hubcap',4,4,4,4,'Autobot');
INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (6,5,7,4,7,'Optimus Prime',6,3,8,6,'Autobot');
INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (8,3,6,8,5,'Bender',5,5,7,8,'Decepticon');
INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (15,8,5,3,5,'Terminator',1,6,4,8,'Autobot');
INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (26,8,2,5,5,'Puppy',7,6,3,4,'Autobot');
INSERT INTO transformer(id, courage, endurance, firepower, intelligence, name, rank, skill, speed, strength, type)
VALUES (27,8,2,3,5,'Rude',3,6,3,4,'Decepticon');

ALTER sequence transformer_id_seq RESTART WITH 28;