package br.edu.ifsp.scl.expressproposal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.expressproposal.data.company.Company
import br.edu.ifsp.scl.expressproposal.data.company.CompanyDAO
import br.edu.ifsp.scl.expressproposal.data.item.Item
import br.edu.ifsp.scl.expressproposal.data.item.ItemDAO

@Database(entities = [Company::class, Item::class], version = 2)
abstract class ExpressProposalDatabase: RoomDatabase() {

    abstract fun companyDAO(): CompanyDAO

    abstract fun itemDAO(): ItemDAO


    companion object {

        @Volatile
        private var INSTANCE: ExpressProposalDatabase? = null
        fun getDatabase(context: Context): ExpressProposalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpressProposalDatabase::class.java,
                    "ProposalRoom.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}