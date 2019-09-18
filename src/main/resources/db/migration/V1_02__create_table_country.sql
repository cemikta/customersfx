CREATE TABLE country (
  country_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  alpha2_code VARCHAR(02) NOT NULL,
  alpha3_code VARCHAR(03) NOT NULL,
  numeric_code SMALLINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  notes TEXT DEFAULT NULL,
  created_by BIGINT DEFAULT NULL,
  created_at DATETIME NOT NULL,
  updated_by BIGINT DEFAULT NULL,
  updated_at DATETIME DEFAULT NULL,
  version BIGINT DEFAULT 0,
  PRIMARY KEY (country_id),
  UNIQUE KEY (alpha2_code),
  UNIQUE KEY (alpha3_code),
  UNIQUE KEY (numeric_code),
  UNIQUE KEY (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
