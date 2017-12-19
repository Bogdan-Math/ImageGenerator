CREATE TABLE IF NOT EXISTS pattern_image (
  name TEXT,
  path TEXT,
  type TEXT
);

ALTER TABLE pattern_image ADD CONSTRAINT type_constraint CHECK (
  type IN ('COMMON', 'FLAG', 'PLAIN')
);