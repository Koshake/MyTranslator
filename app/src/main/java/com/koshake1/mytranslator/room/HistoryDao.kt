package com.koshake1.mytranslator.room

import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    suspend fun all() : List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word : String) : List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entity: List<HistoryEntity>)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)
}