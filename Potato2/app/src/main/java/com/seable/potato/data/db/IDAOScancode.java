package com.seable.potato.data.db;

import com.seable.potato.data.entity.Entity_Sql_Scancode;

import java.util.List;


/**
 * @author 王维玉
 * @ClassName: IScancodeDAO
 * @Description: 离线扫码信息数据库DAO
 * @date 2015-03-03 13:45
 */
public interface IDAOScancode {

    /**
     * 添加离线扫码信息对象
     *
     * @param Scancode
     * @return
     */
    public boolean addData(Entity_Sql_Scancode Scancode);


    /**
     * 根据ID 删除全部离线扫码信息对象
     *
     * @return
     */
    public boolean deleteAll();

    /**
     * 根据ID 删除离线扫码信息对象
     *
     * @param id
     * @return
     */
    public boolean deleteDataByID(int id);

    /**
     * 根据ID取出离线扫码信息对象
     *
     * @param id
     * @return
     */
    public Entity_Sql_Scancode getDataByID(int id);

    /**
     * 取出全部离线扫码信息对象
     *
     * @return
     */
    public List<Entity_Sql_Scancode> getAllData();


    /**
     * 取出当前离线扫码信息id最大的ID
     *
     * @return
     */
    public int getMaxDataID();

    /**
     * 取出当前离线扫码信息id最小的ID
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
    public List<Entity_Sql_Scancode> getLocalDataList(int num/*, int newtype*/);

    /**
     * 判断本地是否存在数据
     *
     * @return
     */
    public boolean isLocalData();


}
