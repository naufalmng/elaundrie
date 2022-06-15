package com.xnfl16.elaundrie.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException


const val LAYOUT_PREFERENCE_NAME = "LAYOUT_PREFERENCE"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCE_NAME)

class DataStorePreferences(preferencesDataStore: DataStore<Preferences>) {
    companion object{
        private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("isLinearLayoutManager")
    }

    suspend fun saveLayoutSetting(ctx: Context,isLinearLayoutManager: Boolean){
        ctx.dataStore.edit {pref ->
            pref[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    }

    suspend fun clear(ctx: Context){
        ctx.dataStore.edit {pref ->
            pref.clear()
        }
    }

    val preferenceFlow: Flow<Boolean> = preferencesDataStore.data
        .catch {
            if(it is IOException){
            it.printStackTrace()
        }
        else throw it
    }.map { pref ->
        pref[IS_LINEAR_LAYOUT_MANAGER] ?: true
    }

    suspend fun readFragmentPref(ctx: Context): Boolean? = ctx.dataStore.data.first()[IS_LINEAR_LAYOUT_MANAGER]

}