DROP TABLE IF EXISTS food_history;
CREATE TABLE food_history(
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	date_timestamp TEXT NOT NULL,
	has_breakfast INTEGER NOT NULL CHECK(has_breakfast IN (0,1)),
	has_dinner INTEGER NOT NULL CHECK(has_dinner IN (0,1)),
	has_guest_breakfast INTEGER NOT NULL CHECK(has_breakfast IN (0,1)),
    has_guest_dinner INTEGER NOT NULL CHECK(has_dinner IN (0,1)),
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);