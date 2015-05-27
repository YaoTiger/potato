package com.seable.potato.data.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * @author 王维玉
 * @ClassName: SQLiteHelper
 * @Description: 数据库帮助类
 * @date 2015-03-03 13:37
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteDateBaseConfig SQLITE_DATEBASE_CONFIG;
    private static SQLiteHelper INSTANCE;
    private Context mContext;
    //反射实例 用于去数据库表名称
    private Reflection mReflection;

    public interface SQLiteDataTable {
        public void OnCreate(SQLiteDatabase p_DataBase);

        public void OnUpgrade(SQLiteDatabase p_DataBase);
    }

    private SQLiteHelper(Context pContext) {
        super(pContext, SQLITE_DATEBASE_CONFIG.GetDataBaseName(), null,
                SQLITE_DATEBASE_CONFIG.GetVersion());
        mContext = pContext;
    }

    public static SQLiteHelper getInstance(Context pContext) {
        if (INSTANCE == null) {
            SQLITE_DATEBASE_CONFIG = SQLiteDateBaseConfig.GetInstance(pContext);
            INSTANCE = new SQLiteHelper(pContext);
        }

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase pDataBase) {
        ArrayList<String> _ArrayList = SQLITE_DATEBASE_CONFIG.GetTables();
        mReflection = new Reflection();
        for (int i = 0; i < _ArrayList.size(); i++) {
            try {
                ((SQLiteDataTable) mReflection.newInstance(_ArrayList.get(i),
                        new Object[]{mContext},
                        new Class[]{Context.class})).OnCreate(pDataBase);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}

