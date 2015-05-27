package com.seable.potato.data.db.base;

import android.content.Context;

import com.seable.potato.R;

import java.util.ArrayList;


/**
 * @author 王维玉
 * @ClassName: SQLiteDateBaseConfig
 * @Description: 数据配置信息
 * @date 2015-03-03 13:38
 */
public class SQLiteDateBaseConfig {
    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "PROPERTY";
    /**
     * 资源文件下 数据库 表的数组
     */
    private static final int ARRAY_TABLE = R.array.SQLitetables;
    /**
     * dao文件实例所在的包的路径
     */
    private static final String PACKAGE_PATH = "com.seable.potato.data.db.impl.";
//												"com.seable.withome.data.db.impl.DAOScancodeImpl"


    //数据库版本号
    private static final int VERSION = 1;
    //数据库配置实例 单例模式
    private static SQLiteDateBaseConfig INSTANCE;
    private static Context mContext;

    private SQLiteDateBaseConfig() {
    }

    public static SQLiteDateBaseConfig GetInstance(Context pContext) {
        if (INSTANCE == null) {
            INSTANCE = new SQLiteDateBaseConfig();
            mContext = pContext;
        }

        return INSTANCE;
    }

    public String GetDataBaseName() {
        return DATABASE_NAME;
    }

    public int GetVersion() {
        return VERSION;
    }

    /**
     * 取出资源文件中数据库表名列表
     *
     * @return list集合
     */
    public ArrayList<String> GetTables() {
        ArrayList<String> _ArrayList = new ArrayList<String>();

        String[] _SQLiteDALClassName = mContext.getResources().getStringArray(ARRAY_TABLE);

        for (int i = 0; i < _SQLiteDALClassName.length; i++) {
            _ArrayList.add(PACKAGE_PATH + _SQLiteDALClassName[i]);
        }
        return _ArrayList;
    }
}
