package ru.uniyar.domain.storage.tables

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object SponsorTable : Table<Nothing>("SPONSORS") {
    val id = int("ID").primaryKey()
    val userId = int("USER_ID")
    val projectId = int("PROJECT_ID")
    val sum = long("SUM")
}
/*
CREATE TABLE "sponsors" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"user_id"	INTEGER NOT NULL,
	"project_id"	INTEGER NOT NULL,
	"sum"	BIGINT NOT NULL,
	FOREIGN KEY("user_id") REFERENCES "users"("id"),
	FOREIGN KEY("project_id") REFERENCES "projects"("id")
);
 */