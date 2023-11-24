package ru.uniyar.domain.storage.tables

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import ru.uniyar.domain.storage.entitys.ProjectEntity

object ProjectTable : Table<ProjectEntity>("PROJECTS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val title = varchar("TITLE").bindTo { it.title }
    val description = varchar("DESCRIPTION").bindTo { it.description }
    val targetFundSize = long("TARGET_FUND_SIZE").bindTo { it.targetFundSize }
    val startDate = date("START_DATE").bindTo { it.startDate }
    val endDate = date("END_DATE").bindTo { it.endDate }
    val deletedAt = date("DELETED_AT").bindTo { it.deletedAt }
    val userId = int("USER_ID").references(UserTable) { it.user }
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
