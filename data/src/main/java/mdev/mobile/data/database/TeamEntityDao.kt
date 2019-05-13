package mdev.mobile.data.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import io.reactivex.Flowable
import mdev.mobile.domain.repo.IdType

@Dao
interface TeamEntityDao {
    @Query("SELECT * FROM teamEntity")
    fun getAll(): Flowable<List<TeamEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(teamEntity: TeamEntity)

    @Query("DELETE FROM teamEntity WHERE id = :teamId")
    fun delete(teamId: IdType)
}

@Entity
class TeamEntity(@PrimaryKey val id: IdType, val name: String, val imageUri: String?)
