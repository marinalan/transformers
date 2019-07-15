# transformers
some exercise it rest api and springboot

1. Create two databases in mysql for development and test.
   Examples of script to create database in mysql:
   ...............................

    CREATE DATABASE IF NOT EXISTS transformers CHARACTER SET utf8;

    create user 'user1'@'%' identified by 'password';

    grant all on transformers.* to 'user1'@'%';

    CREATE DATABASE IF NOT EXISTS test_transformers CHARACTER SET utf8;

    grant all on test_transformers.* to 'user1'@'%';

    flush privileges;
--------------------------------------------------------

2. Credentials for development database will be in
       src/main/resources/application.properties
   for running junit tests in
       src/test/resources/application.properties

    Copy corresponding files  application.properties.sample and rename application.properties,
    replace text in <..> with your dbname, user, password.
    for first run, set in src/main/resources/application.properties

   spring.jpa.hibernate.ddl-auto=create
     for next runs, make it
   spring.jpa.hibernate.ddl-auto=update


3. After starting in ide method main() in TransformerApplication.java such are examples of url / routes:
  ***
    GET
    http://localhost:8080/bots/
  shows all available transformers
  ***

    GET
    http://localhost:8080/bots/3
  information about record with id=3

  ***

    POST
    http://localhost:8080/bots/create
  create new record

  ---
    curl -kiX POST -H "Content-Type: application/json" -d @new_transformer.json  http://localhost:8080/bots/create
  sample contents of file new_transformer.json
  ---
    {
      "name": "Terminator",
      "type": "Autobot",
      "rank": 1,
      "strength": 8,
      "intelligence": 5,
      "speed": 3,
      "endurance": 5,
      "courage": 8,
      "firepower": 3,
      "skill": 6
    }

  ***
    PUT
    http://localhost:8080/bots/16
  update existing record
  ---
    curl -kiX PUT -H "Content-Type: application/json" -d @update_transformer.json  http://localhost:8080/bots/16
  sample contents of file update_transformer.json
  ---
    {
      "id": 16,
      "name": "Terminator III",
      "type": "Autobot",
      "rank": 3,
      "strength": 8,
      "intelligence": 5,
      "speed": 3,
      "endurance": 5,
      "courage": 8,
      "firepower": 3,
      "skill": 7
    }
  notice that "id" should be present and be id of one of existing records, else api will rather create new one instead updating...

  ***

    DELETE
    http://localhost:8080/bots/16
  delete existing record

  ---
        curl -kiX DELETE -H "Content-Type: application/json"  http://localhost:8080/bots/16
