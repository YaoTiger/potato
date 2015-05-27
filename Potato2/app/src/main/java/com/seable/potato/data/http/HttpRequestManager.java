package com.seable.potato.data.http;


import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;

/**
 * Created by yaohu on 15/5/11.
 */
public class HttpRequestManager {

    private HttpUtils httpUtils;
    public static final String URL_BASE = "http://125.77.199.58:8081/potato";
    public static final int status_new = 0;
    public static final int status_wait_deal = 1;
    public static final int status_wait_check = 2;
    public static final int status_fail = 3;
    public static final int status_finish = 4;


    public HttpRequestManager() {

        httpUtils = new HttpUtils();
    }

    /**
     * userName:用户名
     * userPass:用户密码
     *
     * @param username
     * @param pwd
     */
    public void loginRequest(String username, String pwd, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/Login.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userName", username);
        params.addBodyParameter("userPass", pwd);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);

    }
/***userId:用户ID
 status:任务状态（不传为全部状态的任务，0为新任务，1为待处理，2为待审核，3为未通过，4为完成）
 pageNo:页号（考虑到后期的数据过多，最后前期就把分页的功能加上）
 pageSize:每页显示的数据量
 ***/
    /**
     * @param userId
     * @param status
     * @param pageNo
     * @param callback
     */
    public void getTaskList(String userId, int status, int pageNo, RequestCallBack<String> callback) {

        String url = URL_BASE + "/app/task/MyTask.action";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", userId);
        params.addQueryStringParameter("status", status + "");
        params.addQueryStringParameter("pageNo", pageNo + "");
        params.addQueryStringParameter("pageSize", "10");

        url="http://125.77.199.58:8081/potato/app/task/MyTask.action?userId=" +
                userId+"&status=" +
                status+"&pageNo=" +
                pageNo+"&pageSize=10";
        Log.i("ttt",url);

        httpUtils.send(HttpRequest.HttpMethod.GET, url, callback);
    }


    /**
     * ***id:任务的主键***
     */

    public void getTaskInfoById(String id, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/task/show.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }


    /**
     * ******
     * <p/>
     * 上传图片
     * *userId:当前用户的ID
     * fileName:文件名
     * fileData:文件的二进制数据
     * remark:描述信息
     * *********
     */
    public void updateImg(String userId, String fileName, String filePath, String remark, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/upload/picture.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("fileName", fileName);
        params.addBodyParameter("fileData", new File(filePath));
        params.addBodyParameter("remark", remark);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }

    /**
     * id:记录的主键（没有表示新增，有表示更新）
     * taskId:任务的主键
     * userId:当前用户的ID
     * hunza:混杂,
     * leibingdu:类病毒
     * zongbingdu:总病毒
     * zhonghuaye:重花叶
     * juanye:卷叶
     * huanfu:环腐
     * qingku:青枯
     * heijingbing:黑胫病
     * likubing:丝核菌立枯病
     * wanyibing:晚疫病
     * recordType:0表示取样，1表示常规
     * picId:图片的主键，多个用逗号分隔（ID的获取参加4.1的接口说明）
     * remark:备注
     * **
     */
    public void saveInfo(String id, String taskId, String userId, String hunza, String leibingdu, String zongbingdu,
                         String zhonghuaye, String juanye, String huanfu, String qingku, String heijingbing, String
            likubing, String wanyibing, int recordType, String picIds, String remark, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/taskLandRecord/save.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter("taskId", taskId);
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("hunza", hunza);
        params.addBodyParameter("leibingdu", leibingdu);
        params.addBodyParameter("zongbingdu", zongbingdu);
        params.addBodyParameter("zhonghuanye", zhonghuaye);
        params.addBodyParameter("juanye", juanye);
        params.addBodyParameter("huanfu", huanfu);
        params.addBodyParameter("qingku", qingku);
        params.addBodyParameter("heijingbing", heijingbing);
        params.addBodyParameter("likubing", likubing);
        params.addBodyParameter("wanyibing", wanyibing);
        params.addBodyParameter("recordType", recordType + "");
        params.addBodyParameter("picId", picIds);
        params.addBodyParameter("remark", remark);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }


    public void getCheckInfo(String taskId, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/taskLandRecord/index.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("taskId", taskId);

        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }

    /**
     * 收货后检查
     *
     * @param taskId id:记录的主键（没有表示新增，有表示更新）
     *               taskId:任务的主键
     *               userId:当前用户的ID
     *               totalNum:数量,
     *               exampleNum:取样量
     *               recordType:0表示取样，1表示常规
     *               picId:图片的主键，多个用逗号分隔（ID的获取参加4.1的接口说明）
     *               remark:备注
     */
    public void getHarvestCheckInfo(String id, String taskId, String uerId, String totalNum, String exampleNum,
                                    String recordType, String picId, String remark, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/taskHarvestRecord/save.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter("taskId", taskId);
        params.addBodyParameter("totalNum", totalNum);
        params.addBodyParameter("exampleNum", exampleNum);
        params.addBodyParameter("recordType", recordType);
        params.addBodyParameter("picId", picId);
        params.addBodyParameter("remark", remark);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }

    /**
     * taskId:任务的主键
     *
     * @param taskId
     * @param callback
     */

    public void getList(String taskId, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/taskHarvestRecord/index.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("taskId", taskId);

        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }

    /**
     * /app/taskWareRecord/save.action
     * <p/>
     * id:记录的主键（没有表示新增，有表示更新）
     * taskId:任务的主键
     * userId:当前用户的ID
     * qingku:青枯病,
     * shifu:湿腐病
     * ruanfu:软腐病
     * wanyi:晚疫病
     * ganfu:干腐病
     * chuangjia:疮痂病
     * heizhi:黑痣病
     * kuaijinge:块茎峨
     * quexianshu:缺陷薯
     * dongshang:冻伤
     * zazhi:杂质
     * recordType:0表示取样，1表示常规
     * picId:图片的主键，多个用逗号分隔（ID的获取参加4.1的接口说明）
     * remark:备注
     */
    public void saveWarehouse(String id, String taskId, String userId, String qingku, String shifu, String ruanfu,
                              String wanyi, String ganfu, String chuangjia, String heizhi, String kuaijinge,
                              String quexianshu, String dongshang, String zazhi, int recordType, String picId, String remark, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/taskWareRecord/save.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter("taskId", taskId);
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("qingku", qingku);
        params.addBodyParameter("shifu", shifu);
        params.addBodyParameter("ruanfu", ruanfu);
        params.addBodyParameter("wanyi", wanyi);
        params.addBodyParameter("ganfu", ganfu);
        params.addBodyParameter("chuangjia", chuangjia);
        params.addBodyParameter("heizhi", heizhi);
        params.addBodyParameter("kuaijinge", kuaijinge);
        params.addBodyParameter("quexianshu", quexianshu);
        params.addBodyParameter("dongshang", dongshang);
        params.addBodyParameter("zazhi", zazhi);
        params.addBodyParameter("recordType", recordType + "");
        params.addBodyParameter("picId", picId);
        params.addBodyParameter("remark", remark);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }


    /**
     * /app/taskWareRecord/index.action
     */

    public void getWarehouseList(String taskId, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/taskWareRecord/index.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("taskId", taskId);

        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);

    }


    /**
     * /app/areaMeasure.action
     * <p/>
     * taskId:任务的主键
     * userId:当前用户的主键
     * measureNum:测量的面积（单位为亩）
     */

    public void areaMeasure(String taskId, String userId, String measureNum, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/areaMeasure.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("taskId", taskId);
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("measuerNum", measureNum);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);

    }


    /**
     * **
     * /app/user/save.action
     * <p/>
     * <p/>
     * id:当前用户的主键
     * avatarFile:用户头像的二制制数据
     * realName:姓名
     * position:职位
     * oldPass:旧密码（为空表示不修改）
     * newPass:新密码
     */


    public void modifyUserInfo(String id, File avatarFile, String realName, String position, String oldPass, String newPass, RequestCallBack<String> callback) {
        String url = URL_BASE + "/app/user/save.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        if(avatarFile!=null)
        params.addBodyParameter("avatarFile", avatarFile);
        if(!TextUtils.isEmpty(realName))
        params.addBodyParameter("realName", realName);
        if(!TextUtils.isEmpty(position))
        params.addBodyParameter("position", position);
        if(!TextUtils.isEmpty(oldPass))
        params.addBodyParameter("oldPass", oldPass);
        if(!TextUtils.isEmpty(newPass))
        params.addBodyParameter("newPass", newPass);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);

    }

    public void saveTask(String userId,String taskId,RequestCallBack<String> callback){

        String url = URL_BASE + "/app/task/AckTask.action";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("taskId", taskId);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
    }

}
