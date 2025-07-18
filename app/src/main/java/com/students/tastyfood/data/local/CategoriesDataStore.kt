package com.students.tastyfood.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "categories")

object CategoriesDataStore {
    private val CATEGORIES_KEY = stringSetPreferencesKey("categories")
    val defaultCategories = setOf("Все", "Завтраки", "Азиатские", "Ланч", "Обед", "Ужин", "Салаты", "Десерты", "Напитки", "Закуски", "Постные", "Вегетарианские", "Веганские", "Без глютена", "Без молока")

    fun getCategories(context: Context): Flow<Set<String>> =
        context.dataStore.data.map { prefs ->
            prefs[CATEGORIES_KEY] ?: defaultCategories
        }

    suspend fun addCategory(context: Context, category: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[CATEGORIES_KEY]?.toMutableSet() ?: defaultCategories.toMutableSet()
            current.add(category)
            prefs[CATEGORIES_KEY] = current
        }
    }
}
