package com.seable.potato.common;

import com.seable.potato.R;


/**
 * @author 王维玉
 * @ClassName: Constants
 * @Description: 常量
 * @date 2015-01-17 16:19
 */
public interface Constants {
    /** 正式环境 base url */
//	public static final String URL_BASE = "http://withome.xingyebao.com";
    /**
     * 测试环境 base url
     */
    public static final String URL_BASE = "http://whihometest.xingyebao.com";
    public static final String URL_BASE_WEB = "/webview/";
    public static final String URL_BASE_INTERFACE = "/api/";
    //=========================================================
    /**
     * 主页底部
     */
    public static final String[] TASK_DETAIL_BTN = {"确认", "处理"};
    public static final String[] TASK_CENTER_TAB = {"待确认", "待处理", "待审核", "未通过", "已完成", "未通过", "已完成"};
    public static final String[] TASK_TYPE = {"一次田检", "二次田检", "收获后检验", "库房检验"};
    public static final String[] MAIN_TAB = {"任务中心", "面积测量", "任务统计", "个人中心",};
    public static final String[] TASK_COUNT = {"待确认任务", "待处理任务", "待审核任务", "历史未通过任务", "历史已完成任务"};
    public static final String TASK_COUNT_UNIT = "项";
    public static final int[] TASK_COUNT_COLOR = {R.color.bg_red, R.color.bg_task_count2, R.color.bg_task_count3, R.color.bg_task_count4, R.color.bg_task_count5};
    public static final int[] MY_COLOR = {R.drawable.my_update, R.drawable.my_about_us, R.drawable.my_change_password, R.drawable.my_logout};
    public static final String[] MY = {"版本更新", "关于我们", "编辑资料", "退出登录"};
    public static final String TASK_DETAIL_TITLE = "任务类型：";


    // 百度地图lqudvgrD5EHHYXfm86Xouxun
    // =================================部门职务类别================================================
    /**
     * 部门类别 工程project
     */
    public static final int TYPE_ORGANIZE_PROJECT = 1;
    /**
     * 部门类别 保洁cleaning
     */
    public static final int TYPE_ORGANIZE_CLEANING = 2;
    /**
     * 部门类别 安保Security
     */
    public static final int TYPE_ORGANIZE_SECURITY = 3;
    /**
     * 部门类别客服custom
     */
    public static final int TYPE_ORGANIZE_CUSTOM = 4;
    /**
     * 职务类别 1领导
     */
    public static final int TYPE_DUTY_LEADER = 1;
    /**
     * 职务类别 2职员worker 客服组长 保安队长
     */
    public static final int TYPE_DUTY_WORKER = 2;
    /**
     * 职务类别 3职员 客服前台 保安班长
     */
    public static final int TYPE_DUTY_WORKER_THREE = 3;
    /**
     * 职务类别 4职员客服专员
     */
    public static final int TYPE_DUTY_WORKER_FOUR = 4;
    // =================================页面跳转返回标志================================================
    /**
     * 修改为不合格UnQualified
     */
    public static final int RESULT_CHANGE_SCANCODE_SET_UNQUALIFIED = 200;
    /**
     * 修改为合格
     */
    public static final int RESULT_CHANGE_SCANCODE_SET_QUALIFIED = 201;
    // =================================网络请求区分标志================================================
    /**
     * 软件提供商信息
     */
    public static final int REQUEST_GET_COMPANY_INFO = 100;
    /**
     * 检查更新
     */
    public static final int REQUEST_GET_UPDATE = 101;
    /**
     * 修改密码
     */
    public static final int REQUEST_MOTIFY_PASSWORD = 102;
    /**
     * 街道办
     */
    public static final int REQUEST_GET_SUBDISTRICT = 103;

    // =================================网络相关 URL==============================================

    /**
     * 登陆 post
     *
     * @param username
     * 用户名 password 密码
     * @return duty职务 organizeId部门 id
     */
    public static final String URL_DO_LOGIN = URL_BASE + URL_BASE_INTERFACE + "/homeLogin";
    /**
     * 修改密码 post
     *
     * @param username
     * 用户名 old_password 原密码 new_password 新密码 repeat_password 重复密码
     * @return
     */
    public static final String URL_DO_MODIF_PWD = URL_BASE + URL_BASE_INTERFACE + "password";

    /**
     * 关于我们 get
     *
     * @param
     * @return name 名称 tel 电话 url 网址 email 邮箱 address 地址 content 简介
     */
    public static final String URL_GET_ABOUT_US = URL_BASE + URL_BASE_INTERFACE + "homeAboutUs";
    /**
     * 版本更新 get
     *
     * @param
     * @return dateId 版本号 url 下载地址
     */
    public static final String URL_GET_UPDATE = URL_BASE + URL_BASE_INTERFACE + "homeDate";
    /**
     * 获取街道办帖子列表
     * get
     *
     * @param
     * @return civil 民政优抚  数组：id url跳转链接 title 标题
     * population 人口 数组：id url跳转链接 title 标题
     * other 综合服务 数组：id url跳转链接 title 标题
     */
    public static final String URL_GET_SUBDISTRICT_LIST = URL_BASE + URL_BASE_INTERFACE + "street";
    // =================================webview地址================================================

    /**
     * 投诉及保修列表 get
     *
     * @param id
     * (登陆者ID) duty(职责)
     */
    public static final String URL_WEB_CUSTOM_RETURN_VISIT = URL_BASE
            + "/visit.php";

    // =================================网络请求参数================================================

    /**
     * 网络请求参数 关键字 key 设备ID
     */
    public static final String PARAMETER_KEY_DEVICE_ID = "deviceId";
    /**
     * 网络请求参数 关键字 key 部门id
     */
    public static final String PARAMETER_KEY_ORGANIZED_ID = "organizeId";
    /**
     * 网络请求参数 关键字 key 用户id
     */
    public static final String PARAMETER_KEY_USER_ID = "userId";
    /**
     * 网络请求参数 关键字 key 职务
     */
    public static final String PARAMETER_KEY_DUTY = "duty";
    /**
     * 网络请求参数 关键字 key id
     */
    public static final String PARAMETER_KEY_ID = "id";
    /**
     * 网络请求参数 关键字 key 上传数据
     */
    public static final String PARAMETER_KEY_DATA = "data";
    /**
     * 网络请求参数 关键字 key 上传类型
     */
    public static final String PARAMETER_KEY_TASK_TYPE = "task_type";
    /**
     * 网络请求参数 关键字 key 上传类型
     */
    public static final String PARAMETER_KEY_TYPE = "type";
    /**
     * 网络请求参数 关键字 key m_title
     */
    public static final String PARAMETER_KEY_MTITLE = "m_title";
    /**
     * 网络请求参数 关键字 key name
     */
    public static final String PARAMETER_KEY_NAME = "name";
    /**
     * 网络请求参数 关键字 key info
     */
    public static final String PARAMETER_KEY_INFO = "info";
    /**
     * 网络请求参数 关键字 key 登陆用户名
     */
    public static final String PARAMETER_KEY_LOGIN_USERNAME = "username";
    /**
     * 网络请求参数 关键字 key 登陆密码
     */
    public static final String PARAMETER_KEY_LOGIN_PASSWORD = "password";
    /**
     * 网络请求参数 关键字 key 老登陆密码
     */
    public static final String PARAMETER_KEY_LOGIN_OLD_PASSWORD = "old_password";
    /**
     * 网络请求参数 关键字 key 新登陆密码
     */
    public static final String PARAMETER_KEY_LOGIN_NEW_PASSWORD = "new_password";
    /**
     * 网络请求参数 关键字 key 重复登陆密码
     */
    public static final String PARAMETER_KEY_LOGIN_TWICE_PASSWORD = "repeat_password";
    /**
     * 网络请求参数 关键字 key 扫码履职 条码号
     */
    public static final String PARAMETER_KEY_SCAN_CODE = "barcode";
    /**
     * 网络请求参数 关键字 key 扫码履职 内容
     */
    public static final String PARAMETER_KEY_CON = "con";
    /**
     * 网络请求参数 关键字 key 扫码履职 图片
     */
    public static final String PARAMETER_KEY_IMG = "img";
    /**
     * 网络请求参数 关键字 key buildingId
     */
    public static final String PARAMETER_KEY_PERSONID = "personId";
    /**
     * 网络请求参数 关键字 key 扫码履职 选择类别
     */
    public static final String PARAMETER_KEY_SIGN = "sign";

    /**
     * 网络请求参数 值 value type 待确认
     */
    public static final int PARAMETER_VALUE_TASK0_CONFIRM = 0;
    /**
     * 网络请求参数 值 value type 待处理
     */
    public static final int PARAMETER_VALUE_TASK1_HANDLE = 1;
    /**
     * 网络请求参数 值 value type 待审核
     */
    public static final int PARAMETER_VALUE_TASK2_APPROVAL = 2;
    /**
     * 网络请求参数 值 value type 未通过
     */
    public static final int PARAMETER_VALUE_TASK3_FAILED = 3;
    /**
     * 网络请求参数 值 value type 已完成
     */
    public static final int PARAMETER_VALUE_TASK4_SUCCESS = 4;
    /**
     * 网络请求参数 值 value type 历史未通过
     */
    public static final int PARAMETER_VALUE_TASK5_HISTORY_FAILED = 5;
    /**
     * 网络请求参数 值 value type 历史已完成
     */
    public static final int PARAMETER_VALUE_TASK5_HISTORY_SUCCESS = 6;
    /**
     * 网络请求参数 值 value type 第一次田检
     */
    public static final int TYPE_TEST_1_FIRST_FEILD = 1;
    /**
     * 网络请求参数 值 value type 二次田检
     */
    public static final int TYPE_TEST_2_SECOND_FEILD = 2;
    /**
     * 网络请求参数 值 value type 收获后检测
     */
    public static final int TYPE_TEST_3_HARVEST = 3;
    /**
     * 网络请求参数 值 value type 库房检测
     */
    public static final int TYPE_TEST_4_STOREROOM = 4;
    /**
     * 网络请求参数 值 value type 第一次田检
     */
    public static final String PARAMETER_VALUE_TYPE1_TEXT_FORST_FEILD = "一次田检";
    /**
     * 网络请求参数 值 value type 二次田检
     */
    public static final String PARAMETER_VALUE_TYPE2_TEXT_SECOND_FEILD = "二次田检";
    /**
     * 网络请求参数 值 value type 收获后检测
     */
    public static final String PARAMETER_VALUE_TYPE3_TEXT_HARVEST = "收获后检测";
    /**
     * 网络请求参数 值 value type 库房检测
     */
    public static final String PARAMETER_VALUE_TYPE4_TEXT_STOREROOM = "库房检测";
    // =================================Activity标志、参数================================================
    /**
     * 跳转携带参数Bundle key
     */
    public static final String JUMP_PARAMETER_KEY_BUNDLE = "bundle";
    /**
     * 跳转携带参数 key capture
     */
    public static final String JUMP_PARAMETER_KEY_CAPTURE = "capture";
    /**
     * 跳转携带参数 key type
     */
    public static final String JUMP_PARAMETER_KEY_TYPE = "type";
    /**
     * 跳转携带参数 key num
     */
    public static final String JUMP_PARAMETER_KEY_NUM = "num";
    /**
     * 跳转携带参数 key task_type
     */
    public static final String JUMP_PARAMETER_KEY_TASK_TYPE = "task_type";
    /**
     * 跳转携带参数 key id
     */
    public static final String JUMP_PARAMETER_KEY_ID = "id";
    /**
     * 跳转携带参数 key current_tab
     */
    public static final String JUMP_PARAMETER_KEY_CURRENT_TAB = "current_tab";
    /**
     * 跳转携带参数 key position
     */
    public static final String JUMP_PARAMETER_KEY_POSITION = "position";
    // =================================String常量相关================================================
    public static final String SUBMIT_OK = "提交成功";
    public static final String TIPS = "暂无任务";
    public static final String NEW_LINE = " \n \r ";
    public static final String ClICK_DEAL = ".点击处理";
    public static final String ClICK_DEAL_RECEIVE = "收到的信息：";
    public static final String ClICK_DEAL_DONE = ".已处理";
    public static final String INFO_DONE = "已处理";
    public static final String INFO_UNDONE = "未处理";
    /**
     * 上传照片对话框显示
     */
    public static final String publicDialogItem[] = {"拍照", "相册"};
    /**
     * 打开webview的get参数personId
     */
    public static final String WEBVIEW_GET_PERSON_ID_QUESTION = "?personId=";
    /**
     * 打开webview的get参数id
     */
    public static final String WEBVIEW_GET_ID_QUESTION = "?id=";
    /**
     * 打开webview的get参数duty
     */
    public static final String WEBVIEW_GET_DUTY_AND = "&duty=";
    /**
     * 打开webview的get参数sign 1投诉2报修
     */
    public static final String WEBVIEW_GET_SIGN_AND = "&sign=";
    // =================================限制的数量================================================
    /**
     * 姓名长度
     */
    public static final int LIMIT_NAME_LENGTH = 6;
    /**
     * 输入内容最少字数
     */
    public static final int LIMIT_CONTENT_LEAST = 2;
    /**
     * 输入内容最多字数限制
     */
    public static final int LIMIT_CONTENT_MOST_NUM = 100;
    /**
     * list数据一次10条
     */
    public static final int LIMIT_NEIGHBOUR_LIST_NUM = 10;

    // =================================json相关================================================
    /**
     * json解析关键字 网络请求错误时需要 错误代码 1成功 0失败
     */
    public static final String JSON_KEY_CODE = "code";
    /**
     * json解析关键字 网络请求错误时需要 错误信息
     */
    public static final String JSON_KEY_MSG = "msg";
    /**
     * json解析关键字 网络请求正确时需要 返回数据
     */
    public static final String JSON_KEY_DATAS = "datas";
    /**
     * json解析关键字 网络请求正确时需要 返回数据
     */
    public static final String JSON_KEY_TYPE = "type";
    /**
     * json解析关键字 网络请求 是否分页
     */
    public static final String JSON_KEY_HAS_MAOR = "has_more";
    /**
     * json解析关键字 小区安防 - 消防安全知识列表
     */
    public static final String JSON_KEY_KNOWLEDGE_LIST_TITLE = "title";
    /**
     * json解析关键字 小区安防 - 消防安全知识列表
     */
    public static final String JSON_KEY_KNOWLEDGE_LIST_CONTENT = "content";

    // =================================推送相关================================================
    /**
     * content provider address
     */
    public String IM_AUTHORITY = "com.seable.potato.Pushimps";
}
