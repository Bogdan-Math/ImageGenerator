CREATE TABLE IF NOT EXISTS pattern (

  type     TEXT,
  location TEXT,

  CONSTRAINT pattern_pk PRIMARY KEY (
    type
  ),

  CONSTRAINT type_constraint CHECK (
    type IN ('COMMON', 'FLAG', 'PLAIN')
  )
);

INSERT INTO pattern (type, location)
VALUES ('COMMON', 'images/colors'),
       ('FLAG', 'images/flags'),
       ('PLAIN', 'images/plains')