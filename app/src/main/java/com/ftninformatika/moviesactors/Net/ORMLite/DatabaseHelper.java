package com.ftninformatika.moviesactors.Net.ORMLite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ftninformatika.moviesactors.Models.Movie;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Movie, String> movieDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Movie.class);
        } catch (SQLException | java.sql.SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            try {
                TableUtils.dropTable(connectionSource, Movie.class, true);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            try {
                TableUtils.createTable(connectionSource, Movie.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Dao<Movie, String> getMovieDao() throws SQLException {
        if (movieDao == null) {
            try {
                movieDao = getDao(Movie.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        return movieDao;
    }

    @Override
    public void close() {
        movieDao = null;

        super.close();
    }
}
