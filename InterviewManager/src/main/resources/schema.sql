create table users(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(256) NOT NULL,
  iconURL VARCHAR(256) NOT NULL
);

create table companies(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  name VARCHAR(256) NOT NULL,
  link VARCHAR(256),
  FOREIGN KEY(userid) REFERENCES users(id)
);

create table flowBlocks(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  whichCompanyId BIGINT,
  blockName VARCHAR(64) NOT NULL,
  date VARCHAR(32),
  memo VARCHAR(128),
  FOREIGN KEY(whichCompanyId) REFERENCES companies(id)
);