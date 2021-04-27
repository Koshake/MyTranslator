package com.koshake1.mytranslator.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = arrayOf("word"), unique = true)))
data class HistoryEntity(
    @PrimaryKey()
    var word: String,
    var description: String?
)
