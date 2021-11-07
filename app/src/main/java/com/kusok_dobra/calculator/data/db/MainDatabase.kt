package com.kusok_dobra.calculator.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kusok_dobra.calculator.data.db.history.HistoryItemDao
import com.kusok_dobra.calculator.data.db.history.HistoryItemEntity
import com.kusok_dobra.calculator.data.db.timeConverters.LocalDateTimeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [HistoryItemEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract val historyItemDao: HistoryItemDao

    companion object {
        fun create(context: Context): MainDatabase =
            Room.databaseBuilder(context, MainDatabase::class.java, "main_database")
                .addMigrations(MigrationFrom1to2())
                .build()
    }
}

class MigrationFrom1to2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE history_item_entity ADD COLUMN createdAt TEXT DEFAULT " +
                    "\"${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}\" " +
                    "NOT NULL"
        )
    }
}