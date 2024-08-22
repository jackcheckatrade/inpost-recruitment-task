package pl.inpost.recruitmenttask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideDb(@ApplicationContext applicationContext: Context): AppDatabase =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "app-db"
            ).build()

        @Provides
        @Singleton
        fun provideShipmentDao(db: AppDatabase) = db.shipmentDao()
    }
}