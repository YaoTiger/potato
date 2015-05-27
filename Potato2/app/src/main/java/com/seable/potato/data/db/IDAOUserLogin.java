package com.seable.potato.data.db;

import com.seable.potato.data.entity.Entity_Sql_User;

import java.util.List;


/**
 * @author 王维玉
 * @ClassName: IScancodeDAO
 * @Description: 离线用户登陆信息数据库DAO
 * @date 2015-03-03 13:45
 */
public interface IDAOUserLogin {

    /**
     * 添加离线用户登陆信息对象
     *
     * @param Scancode
     * @return
     */
    public boolean addData(Entity_Sql_User Scancode);


    /**
     * 根据ID 删除全部离线用户登陆信息对象
     *
     * @return
     */
    public boolean deleteAll();

    /**
     * 根据ID 删除离线用户登陆信息对象
     *
     * @param id
     * @return
     */
    public boolean deleteDataByID(String name);

    /**
     * 根据ID取出离线用户登陆信息对象
     *
     * @param id
     * @return
     */
    public Entity_Sql_User getDataByID(int id);

    /**
     * 判断离线用户登陆信息是否有某条数据
     *
     * @param id
     * @return
     */
    public boolean getDataByName(String name);

    /**
     * 取出全部离线用户登陆信息对象
     *
     * @return
     */
    public List<Entity_Sql_User> getAllData();


    /**
     * 取出当前离线用户登陆信息id最大的ID
     *
     * @return
     */
    public int getMaxDataID();

    /**
     * 取出当前离线用户登陆信息id最小的ID
     *
     * @return
     */
    public int getMinDataID();


    /**
     * 取出本地数据 按id排序，最新的num条
     *
     * @param num 条数
     * @return
     */
    public List<Entity_Sql_User> getLocalDataList(int num/*, int newtype*/);

    /**
     * 判断本地是否存在数据
     *
     * @return
     */
    public boolean isLocalData();


}
