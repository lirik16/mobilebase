package mdev.mobile.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TeamEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamEntityDao(): TeamEntityDao
}
