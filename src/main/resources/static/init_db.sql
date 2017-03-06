CREATE TABLE IF NOT EXISTS email_address
(
  id SERIAL PRIMARY KEY,
  email TEXT UNIQUE,
  body TEXT,
  subject TEXT,
  sent BOOLEAN
);