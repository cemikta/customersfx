CREATE TABLE app_preferences (
  app_preferences_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  items_per_page INTEGER NOT NULL DEFAULT 20,
  show_message_dialog BOOLEAN NOT NULL DEFAULT TRUE,
  show_info_popups BOOLEAN NOT NULL DEFAULT TRUE,
  show_tip_of_the_day BOOLEAN NOT NULL DEFAULT TRUE,
  report_export_directory VARCHAR(255) DEFAULT NULL,
  app_theme VARCHAR(50) NOT NULL,
  created_by BIGINT DEFAULT NULL,
  created_at DATETIME NOT NULL,
  updated_by BIGINT DEFAULT NULL,
  updated_at DATETIME DEFAULT NULL,
  version BIGINT DEFAULT 0,
  PRIMARY KEY (app_preferences_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
