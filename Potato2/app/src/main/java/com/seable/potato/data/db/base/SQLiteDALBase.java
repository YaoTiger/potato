package com.seable.potato.data.db.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seable.potato.data.db.base.SQLiteHelper.SQLiteDataTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: SQLiteDALBase
 * @Description: 抽象数据库基础
 * @date 2015-03-03 13:29
 */
public abstract class SQLiteDALBase implements SQLiteDataTable {

    private Context mCont;
    private SQLiteDatabase mDataBase;

    public SQLiteDALBase(Context p_Context) {
        mCont = p_Context;
    }

    protected Context getContext() {
        return mCont;
    }

    public SQLiteDatabase getDataBase() {
        if (mDataBase == null) {
            mDataBase = SQLiteHelper.getInstance(mCont).getWritableDatabase();
        }

        return mDataBase;
    }

    /**
     * 封装开始事物
     */
    public void beginTransaction() {
        mDataBase.beginTransaction();
    }

    /**
     * 数据成功封装事物
     */
    public void setTransactionSuccessful() {
        mDataBase.setTransactionSuccessful();
    }

    /**
     * 结束事物
     */
    public void endTransaction() {
        mDataBase.endTransaction();
    }

    /**
     * 数据行数
     *
     * @param p_Condition
     * @return
     */
    public int getCount(String p_Condition) {
        String _String[] = getTableNameAndPK();
        Cursor _Cursor = execSql("Select " + _String[1] + " From " + _String[0] + " Where 1=1 " + p_Condition);
        int _Count = _Cursor.getCount();
        _Cursor.close();
        return _Count;
    }

    /**
     * 根据条件去数据库存在数据的行数
     *
     * @param p_PK
     * @param p_TableName
     * @param p_Condition
     * @return
     */
    public int getCount(String p_PK, String p_TableName, String p_Condition) {
        Cursor _Cursor = execSql("Select " + p_PK + " From " + p_TableName + " Where 1=1 " + p_Condition);
        int _Count = _Cursor.getCount();
        _Cursor.close();
        return _Count;
    }

    /**
     * 删除数据
     *
     * @param p_TableName 表明
     * @param p_Condition 条件
     * @return true 代表删除 false代表为删除
     */
    protected Boolean delete(String p_TableName, String p_Condition) {
        return getDataBase().delete(p_TableName, " 1=1 " + p_Condition, null) >= 0;
    }

    /**
     * 删除全部数据
     *
     * @param p_TableName 表名
     * @return true 代表删除 false代表未删除
     */
    protected Boolean deleteAll(String p_TableName) {
        return getDataBase().delete(p_TableName, " 1=1 ", null) >= 0;
    }

    /**
     * 取出数据表
     *
     * @return
     */
    protected abstract String[] getTableNameAndPK();

    /**
     * 取出数据库版本号
     *
     * @return
     */
    protected abstract int getDatabaseVersion();

    /**
     * 取出所有数据
     *
     * @param p_SqlText
     * @return
     */
    protected List getList(String p_SqlText) {
        Cursor _Cursor = execSql(p_SqlText);
        return CursorToList(_Cursor);
    }

    /**
     * 找出数据模型
     *
     * @param p_Cursor
     * @return
     */
    protected abstract Object findModel(Cursor p_Cursor);

    /**
     * 根据cursor 取数据
     *
     * @param p_Cursor
     * @return
     */
    protected List CursorToList(Cursor p_Cursor) {
        List _List = new ArrayList();
        while (p_Cursor.moveToNext()) {
            Object _Object = findModel(p_Cursor);
            _List.add(_Object);
        }
        p_Cursor.close();
        return _List;
    }

    /**
     * 执行SQL语句
     *
     * @param p_SqlText
     * @return
     */
    public Cursor execSql(String p_SqlText) {
        return getDataBase().rawQuery(p_SqlText, null);
    }
}
