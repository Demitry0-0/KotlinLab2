CREATE TABLE "users" (
	"id"	INTEGER NOT NULL UNIQUE,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"deleted_at"	datetime  DEFAULT NULL
);
CREATE TABLE "projects" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"user_id"	INTEGER NOT NULL,
	"title"	TEXT NOT NULL,
	"description"	TEXT NOT NULL,
	"target_fund_size"	BIGINTEGER NOT NULL CHECK(target_fund_size>=0),
	"start_date"	DATETIME NOT NULL,
	"end_date"	DATETIME NOT NULL,
	"deleted_at"	DATETIME DEFAULT NULL,
	FOREIGN KEY("user_id") REFERENCES "users"("id")
);
CREATE TABLE "sponsors" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"user_id"	INTEGER NOT NULL,
	"project_id"	INTEGER NOT NULL,
	"sum"	BIGINT NOT NULL,
	FOREIGN KEY("user_id") REFERENCES "users"("id"),
	FOREIGN KEY("project_id") REFERENCES "projects"("id")
);