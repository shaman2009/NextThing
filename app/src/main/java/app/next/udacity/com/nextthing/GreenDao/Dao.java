package app.next.udacity.com.nextthing.GreenDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Shaman on 1/6/15.
 */
public class Dao {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;



    public Dao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "next-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Next next) {
        NextDao dao = daoSession.getNextDao();
        dao.insert(next);
    }

    public List<Next> get() {
        NextDao dao = daoSession.getNextDao();
        List<Next> list = dao.queryBuilder().build().list();
        return list;
    }
}
