package ru.uniyar.domain.storage.tables

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserTable : Table<Nothing>("USERS") {
    val id = int("ID").primaryKey()
    val firstName = varchar("FIRST_NAME")
    val lastName = varchar("LAST_NAME")
    val deletedAt = date("DELETED_AT")
}
/*
CREATE TABLE "users" (
	"id"	INTEGER NOT NULL UNIQUE,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"deleted_at"	datetime  DEFAULT NULL
);
 */