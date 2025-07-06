package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * 我们来创建一个可添加绑定的 Hilt 模块。
 * 在 hilt 文件包下创建一个名为 di 的新文件包，
 * 并在该文件包中创建一个名为 DatabaseModule.kt 的新文件。
 *
 * 由于 LoggerLocalDataSource 的作用域限定为 application 容器，
 * 因此 LogDao 绑定需要在 application 容器中可用。
 * 我们通过传递与其相关联的 Hilt 组件的类（即 ApplicationComponent:class），使用 @InstallIn 注解指定该要求：
 */
@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }


    /**
     * 在 Kotlin 中，仅包含 @Provides 函数的模块可以是 object 类。
     * 通过这种方式，提供程序会得到优化，并几乎内嵌到生成的代码中。
     */
    @Provides
    fun provideLogDao(database: AppDatabase): LogDao {
        return database.logDao()
    }


}