package com.yamasoft.vehiclecompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yamasoft.vehiclecompanion.data.local.entity.PoiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {
    @Query("SELECT * FROM favorite_pois ORDER BY savedAt DESC")
    fun getFavoritePois(): Flow<List<PoiEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pois WHERE id = :id)")
    suspend fun isPoiFavorite(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoi(poi: PoiEntity)

    @Query("DELETE FROM favorite_pois WHERE id = :id")
    suspend fun deletePoi(id: Int)
}