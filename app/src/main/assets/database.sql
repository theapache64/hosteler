DROP TABLE IF EXISTS food_histories;

CREATE TABLE food_histories(
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	_date TEXT NOT NULL,
	breakfast INTEGER NOT NULL,
	dinner INTEGER NOT NULL,
	guest_breakfast INTEGER NOT NULL ,
    guest_dinner INTEGER NOT NULL ,
    additional_charge INTEGER NOT NULL,
    description TEXT,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (_date)
);

DROP TABLE IF EXISTS payment_histories;
CREATE TABLE payment_histories(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    from_date TEXT NOT NULL,
    to_date TEXT NOT NULL,
    amount_paid INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);