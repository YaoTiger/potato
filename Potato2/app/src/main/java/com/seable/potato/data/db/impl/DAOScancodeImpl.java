package com.seable.potato.data.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seable.potato.data.db.IDAOScancode;
import com.seable.potato.data.db.base.SQLiteDALBase;
import com.seable.potato.data.db.base.SQLiteDateBaseConfig;
import com.seable.potato.data.entity.Entity_Sql_Scancode;

import java.util.List;


/**
 * @author 王维玉
 * @ClassName: ScancodeDAOImpl
 * @Description: 离线扫码信息数据库DAO实现
 * @date 2015-03-03 14:25
 */
public class DAOScancodeImpl extends SQLiteDALBase implements IDAOScancode {

    public static final String DBTABLE_SCANCODE = "Scancode";
    public static final String PRIMARY_KEY = "i_id";
    public static final String KEY_NUM = "s_scancode_num";
    public static final String KEY_CONTENT = "s_scancode_content";

    public DAOScancodeImpl(Context p_Context) {
        super(p_Context);
    }

    @Override
    public void OnCreate(SQLiteDatabase p_DataBase) {
        // TODO Auto-generated method stub
        StringBuilder createTable = new StringBuilder();
        createTable.append("		Create  TABLE " + DBTABLE_SCANCODE + "(");
        createTable.append("				[" + PRIMARY_KEY + "] integer PRIMARY KEY AUTOINCREMENT ");
        createTable.append("				,[" + KEY_NUM + "] varchar");
        createTable.append("				,[" + KEY_CONTENT + "] varchar");
        createTable.append("				)");
        p_DataBase.execSQL(createTable.toString());
    }

    @Override
    public void OnUpgrade(SQLiteDatabase p_DataBase) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String[] getTableNameAndPK() {
        // TODO Auto-generated method stub
        return new String[]{DBTABLE_SCANCODE, PRIMARY_KEY};
    }

    @Override
    protected int getDatabaseVersion() {
        // TODO Auto-generated method stub
        return SQLiteDateBaseConfig.GetInstance(getContext()).GetVersion();
    }

    @Override
    protected Object findModel(Cursor p_Cursor) {
        // TODO Auto-generated method stub
        Entity_Sql_Scancode Scancode = new Entity_Sql_Scancode();
        Scancode.setId(p_Cursor.getInt(p_Cursor.getColumnIndex(PRIMARY_KEY)));
        Scancode.setmScancodeNum(p_Cursor.getString(p_Cursor.getColumnIndex(KEY_NUM)));
        Scancode.setmScancodeContent(p_Cursor.getString(p_Cursor.getColumnIndex(KEY_CONTENT)));
        return Scancode;
    }

    public ContentValues creatParms(Entity_Sql_Scancode Scancode) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRIMARY_KEY, Scancode.getId());
        contentValues.put(KEY_NUM, Scancode.getmScancodeNum());
        contentValues.put(KEY_CONTENT, Scancode.getmScancodeContent());
        return contentValues;
    }

    @Override
    public boolean addData(Entity_Sql_Scancode Scancode) {
        // TODO Auto-generated method stub
        ContentValues values = this.creatParms(Scancode);
        return getDataBase().insert(DBTABLE_SCANCODE, null, values) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Entity_Sql_Scancode> getAllData() {
        // TODO Auto-generated method stub
        String sql = " SELECT * FROM " + DBTABLE_SCANCODE + " WHERE 1 = 1 ";
        List<Entity_Sql_Scancode> list = getList(sql);
        return list;
    }


    @Override
    public int getMaxDataID() {
        // TODO Auto-generated method stub
        String sql = "SELECT MAX(" + PRIMARY_KEY + ") AS " + PRIMARY_KEY + "FROM " + DBTABLE_SCANCODE;
        Cursor cursor = execSql(sql);
        int currentMaxID = -1;
        if (cursor.moveToNext()) {
            currentMaxID = cursor.getInt(cursor.getColumnIndex(PRIMARY_KEY));
        }
        return currentMaxID;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Entity_Sql_Scancode> getLocalDataList(int num/*, int newtype*/) {
        // TODO Auto-generated method stub
        String sql;
//		if(newtype == 0){
        sql = "SELECT * FROM " + DBTABLE_SCANCODE + " ORDER BY " + PRIMARY_KEY + " DESC LIMIT 0 , " + num;
//		}else{
//			sql = "SELECT * FROM "+DBTABLE_SCANCODE+" WHERE i_column_id = "+ newtype + " LIMIT 0 , " + num;
//		}
        List<Entity_Sql_Scancode> list = getList(sql);
        return list;
    }

    @Override
    public boolean deleteDataByID(int id) {
        // TODO Auto-generated method stub
        return delete(DBTABLE_SCANCODE, " AND " + PRIMARY_KEY + " = " + id);
    }

    @Override
    public boolean deleteAll() {

        return deleteAll(DBTABLE_SCANCODE);
    }

    @Override
    public int getMinDataID() {
        String sql = "SELECT MIN(" + PRIMARY_KEY + ") AS " + PRIMARY_KEY + " FROM " + DBTABLE_SCANCODE;
        Cursor cursor = execSql(sql);
        int currentMaxID = -1;
        if (cursor.moveToNext()) {
            currentMaxID = cursor.getInt(cursor.getColumnIndex(PRIMARY_KEY));
        }
        return currentMaxID;
    }

    @Override
    public Entity_Sql_Scancode getDataByID(int id) {
        // TODO Auto-generated method stub
        Entity_Sql_Scancode Scancode = null;
        String sql = "SELECT * FROM " + DBTABLE_SCANCODE + " WHERE " + PRIMARY_KEY + " = " + id;
        Cursor cursor = execSql(sql);
        if (cursor.moveToNext()) {
            Scancode = (Entity_Sql_Scancode) this.findModel(cursor);
        }
        return Scancode;
    }

    @Override
    public boolean isLocalData() {
        // TODO Auto-generated method stub
        String sql = "SELECT COUNT(*) AS count FROM " + DBTABLE_SCANCODE;
        Cursor cursor = execSql(sql);
        int datanum = -1;
        if (cursor.moveToNext()) {
            datanum = cursor.getInt(cursor.getColumnIndex("count"));
        }
        return datanum > 0;
    }


}
