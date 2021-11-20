package sharma.pankaj.recyclerviewitemselection.di

import android.content.Context
import sharma.pankaj.recyclerviewitemselection.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sharma.pankaj.recyclerviewitemselection.ui.adapter.AnimeListAdapter
import sharma.pankaj.recyclerviewitemselection.utils.PreferenceHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAdapter(): AnimeListAdapter = AnimeListAdapter()

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger()

    @Provides
    @Singleton
    fun provideLocalRepository(@ApplicationContext context: Context): PreferenceHandler {
        return PreferenceHandler(context)
    }
}