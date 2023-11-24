package ru.uniyar.domain.storage.tables

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object ProjectTable : Table<Nothing>("PROJECTS") {
    val id = int("ID").primaryKey()
    val userId = int("USER_ID")
    val title = varchar("TITLE")
    val description = varchar("DESCRIPTION")
    val targetFundSize = long("TARGET_FUND_SIZE")
    val startDate = date("START_DATE")
    val endDate = date("END_DATE")
    val deletedAt = date("DELETED_AT")
}
/*
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
 */