CREATE TABLE Event (
  id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(256) ,
  type     VARCHAR(64)  ,
  title    VARCHAR(256) ,
  story    VARCHAR(256) ,
  status   VARCHAR(64)  ,
  image    VARCHAR(256) ,
  headline VARCHAR(64)  ,
  world_id BIGINT       ,
  user_id  BIGINT       ,
  timestamp TIMESTAMP   ,
  FOREIGN KEY (world_id) REFERENCES World(id),
  FOREIGN KEY (user_id) REFERENCES User(id)
);


