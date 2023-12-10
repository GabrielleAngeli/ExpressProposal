package br.edu.ifsp.scl.expressproposal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Company::class], version = 1)
abstract class CompanyDatabase: RoomDatabase() {

    abstract fun companyDAO(): CompanyDAO

    companion object {
        @Volatile
        private var INSTANCE: CompanyDatabase? = null
        fun getDatabase(context: Context): CompanyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CompanyDatabase::class.java,
                    "ExpressProposalRoom.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}