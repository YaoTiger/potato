package com.seable.potato.data.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seable.potato.data.db.IDAOUserLogin;
import com.seable.potato.data.db.base.SQLiteDALBase;
import com.seable.potato.data.db.base.SQLiteDateBaseConfig;
import com.seable.potato.data.entity.Entity_Sql_User;

import java.util.List;


/**
 * @author 王维玉
 * @ClassName: UserLoginDAOImpl
 * @Description: 用户登陆账号信息数据库DAO实现
 * @date 2015-03-03 14:25
 */
public class DAOUserLoginImpl extends SQLiteDALBase implements IDAOUserLogin {

    public static final String DBTABLE_USER_LOGIN = "UserLogin";
    public static final String PRIMARY_KEY = "i_id";
    public static final String KEY_ID = "s_userlogin_id";
    public static final String KEY_NAME = "s_userlogin_name";
    public static final String KEY_PASS = "s_userlogin_pass";

    public DAOUserLoginImpl(Context p_Context) {
        super(p_Context);
    }

    @Override
    public void OnCreate(SQLiteDatabase p_DataBase) {
        // TODO Auto-generated method stub
        StringBuilder createTable = new StringBuilder();
        createTable.append("		Create  TABLE " + DBTABLE_USER_LOGIN + "(");
        createTable.append("				[" + PRIMARY_KEY + "] integer PRIMARY KEY AUTOINCREMENT ");
        createTable.append("				,[" + KEY_ID + "] varchar");
        createTable.append("				,[" + KEY_NAME + "] varchar");
        createTable.append("				,[" + KEY_PASS + "] varchar");
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
        return new String[]{DBTABLE_USER_LOGIN, KEY_ID};
    }

    @Override
    protected int getDatabaseVersion() {
        // TODO Auto-generated method stub
        return SQLiteDateBaseConfig.GetInstance(getContext()).GetVersion();
    }

    @Override
    protected Object findModel(Cursor p_Cursor) {
        // TODO Auto-generated method stub
        Entity_Sql_User UserLogin = new Entity_Sql_User();
        UserLogin.setId(p_Cursor.getInt(p_Cursor.getColumnIndex(KEY_ID)));
        UserLogin.setUsername(p_Cursor.getString(p_Cursor.getColumnIndex(KEY_NAME)));
        UserLogin.setPassword(p_Cursor.getString(p_Cursor.getColumnIndex(KEY_PASS)));
        return UserLogin;
    }

    public ContentValues creatParms(Entity_Sql_User UserLogin) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, UserLogin.getId());
        contentValues.put(KEY_NAME, UserLogin.getUsername());
        contentValues.put(KEY_PASS, UserLogin.getPassword());
        return contentValues;
    }

    @Override
    public boolean addData(Entity_Sql_User UserLogin) {
        // TODO Auto-generated method stub
        ContentValues values = this.creatParms(UserLogin);
        return getDataBase().insert(DBTABLE_USER_LOGIN, null, values) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Entity_Sql_User> getAllData() {
        // TODO Auto-generated method stub
        String sql = " SELECT * FROM " + DBTABLE_USER_LOGIN + " WHERE 1 = 1 ";
        List<Entity_Sql_User> list = getList(sql);
        return list;
    }


    @Override
    public int getMaxDataID() {
        // TODO Auto-generated method stub
        String sql = "SELECT MAX(" + KEY_ID + ") AS " + KEY_ID + "FROM " + DBTABLE_USER_LOGIN;
        Cursor cursor = execSql(sql);
        int currentMaxID = -1;
        if (cursor.moveToNext()) {
            currentMaxID = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        }
        return currentMaxID;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Entity_Sql_User> getLocalDataList(int num/*, int newtype*/) {
        // TODO Auto-generated method stub
        String sql;
//		if(newtype == 0){
        sql = "SELECT * FROM " + DBTABLE_USER_LOGIN + " ORDER BY " + KEY_ID + " DESC LIMIT 0 , " + num;
//		}else{
//			sql = "SELECT * FROM "+DBTABLE_USER_LOGIN+" WHERE i_column_id = "+ newtype + " LIMIT 0 , " + num;
//		}
        List<Entity_Sql_User> list = getList(sql);
        return list;
    }

    @Override
    public boolean deleteDataByID(String name) {
        // TODO Auto-generated method stub
        return delete(DBTABLE_USER_LOGIN, " AND " + KEY_NAME + " = '" + name + "'");
    }

    @Override
    public boolean deleteAll() {

        return deleteAll(DBTABLE_USER_LOGIN);
    }

    @Override
    public int getMinDataID() {
        String sql = "SELECT MIN(" + KEY_ID + ") AS " + KEY_ID + " FROM " + DBTABLE_USER_LOGIN;
        Cursor cursor = execSql(sql);
        int currentMaxID = -1;
        if (cursor.moveToNext()) {
            currentMaxID = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        }
        return currentMaxID;
    }

    @Override
    public Entity_Sql_User getDataByID(int id) {
        // TODO Auto-generated method stub
        Entity_Sql_User UserLogin = null;
        String sql = "SELECT * FROM " + DBTABLE_USER_LOGIN + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = execSql(sql);
        if (cursor.moveToNext()) {
            UserLogin = (Entity_Sql_User) this.findModel(cursor);
        }
        return UserLogin;
    }

    @Override
    public boolean getDataByName(String name) {
        // TODO Auto-generated method stub
        String sql = "SELECT * FROM " + DBTABLE_USER_LOGIN + " WHERE " + KEY_NAME + " = '" + name + "'";
        Cursor cursor = execSql(sql);
        return cursor.moveToFirst();

    }

    @Override
    public boolean isLocalData() {
        // TODO Auto-generated method stub
        String sql = "SELECT COUNT(*) AS count FROM " + DBTABLE_USER_LOGIN;
        Cursor cursor = execSql(sql);
        int datanum = -1;
        if (cursor.moveToNext()) {
            datanum = cursor.getInt(cursor.getColumnIndex("count"));
        }
        return datanum > 0;
    }


}
