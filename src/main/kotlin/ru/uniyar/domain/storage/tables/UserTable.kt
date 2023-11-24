package ru.uniyar.domain.storage.tables

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import ru.uniyar.domain.storage.entitys.UserEntity

object UserTable : Table<UserEntity>("USERS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val firstName = varchar("FIRST_NAME").bindTo { it.firstName }
    val lastName = varchar("LAST_NAME").bindTo { it.lastName }
    val deletedAt = date("DELETED_AT").bindTo { it.deletedAt }
}
/*
CREATE TABLE "users" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"deleted_at"	datetime  DEFAULT NULL
);
 */
