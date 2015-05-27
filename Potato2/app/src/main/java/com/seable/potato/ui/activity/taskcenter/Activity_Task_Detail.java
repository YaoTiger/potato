package com.seable.potato.ui.activity.taskcenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.common.Constants;
import com.seable.potato.common.MApplication;
import com.seable.potato.data.entity.DetailDisplayItem;
import com.seable.potato.data.entity.Entity_Task;
import com.seable.potato.data.entity.Entity_Task_Item;
import com.seable.potato.data.entity.HttpResponseObject;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.data.entity.tigerentity.HttpBaseQuery;
import com.seable.potato.data.entity.tigerentity.TaskInfoEntity;
import com.seable.potato.data.http.HttpRequestManager;
import com.seable.potato.util.LFormat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Activity_Task_Detail
 * @Description: 任务详情
 * @date 2015-03-30 13:43
 */
public class Activity_Task_Detail extends LActivity implements OnClickListener {
    private final static int REQUEST_RECORD = 201;
    private final static int REQUEST_HARVEST = 202;

    private Button btnOK, btnMotify;

    private static ListView lv;
    private static SimpleAdapter sAdapter;
    private static TextView tvType, tvStatus;
    private static Entity_Task info;
    private static TaskInfoEntity infoEntity;

    private int type;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        findViewById();
        getIntentData();
    }

    private void getIntentData(Entity_Task_Item task_item) {

        List<DetailDisplayItem> list = new ArrayList<DetailDisplayItem>();
        if (type == Constants.TYPE_TEST_1_FIRST_FEILD) {
            DetailDisplayItem item = new DetailDisplayItem();
//			item.setTitle(mContext.getString(R.string.task_center_detail_type));
//			item.setName(mContext
//					.getString(R.string.task_center_detail_type1_feild));
//			list.add(item);
//			item = new Entity_Task_Item();
            item.setTitle("种植人");
            item.setName(task_item.getManName());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("电话");
            item.setName(task_item.getManPhone());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("品种");
            item.setName(task_item.getBreed());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("面积");
            item.setName(task_item.getAreaNum());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种薯级别");
            item.setName(task_item.getGrade());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种子批编");
            item.setName(task_item.getBatchNo());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("播种日期");
            item.setName(task_item.getFarmDate());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬去年");
            item.setName(task_item.getBefFram());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬前年");
            item.setName(task_item.getBbefFram());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产方式");
            item.setName(task_item.getShield());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产厂家");
            item.setName(task_item.getCompany());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("地块位置");
            item.setName(task_item.getAddress());
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬及本茬\n农药使用情况");
            item.setName(task_item.getChemicals());
            list.add(item);
        } else if (type == Constants.TYPE_TEST_2_SECOND_FEILD) {
            DetailDisplayItem item = new DetailDisplayItem();
//			item.setTitle(mContext.getString(R.string.task_center_detail_type));
//			item.setName(mContext
//					.getString(R.string.task_center_detail_type2_feild));
//			list.add(item);
//			item = new DetailDisplayItem();
            item.setTitle("种植人");
            item.setName("张伟");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("电话");
            item.setName("13904517248");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("品种");
            item.setName("脱毒种薯");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("面积");
            item.setName("20亩");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种薯级别");
            item.setName("一级种");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种子批编");
            item.setName("02112023101235423");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("播种日期");
            item.setName("2015-03-21");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬去年");
            item.setName("玉米");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬前年");
            item.setName("土豆");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("地块位置");
            item.setName("黑龙江省哈尔滨市平房区松花路9号");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产方式");
            item.setName("精益生产");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产厂家");
            item.setName("常州市百家食品有限公司");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬及本茬\n农药使用情况");
            item.setName("农药驱虫，但是普通农药作用较差，多使用剧毒");
            list.add(item);
        } else if (type == Constants.TYPE_TEST_3_HARVEST) {
            DetailDisplayItem item = new DetailDisplayItem();
//			item.setTitle(mContext.getString(R.string.task_center_detail_type));
//			item.setName(mContext
//					.getString(R.string.task_center_detail_type3_harvest));
//			list.add(item);
//			item = new DetailDisplayItem();
            item.setTitle("种植人");
            item.setName("张伟");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("电话");
            item.setName("13904517248");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("品种");
            item.setName("脱毒种薯");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("面积");
            item.setName("20亩");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种薯级别");
            item.setName("一级种");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种子批编");
            item.setName("02112023101235423");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("播种日期");
            item.setName("2015-03-21");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬去年");
            item.setName("玉米");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬前年");
            item.setName("土豆");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("地块位置");
            item.setName("黑龙江省哈尔滨市平房区松花路9号");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产方式");
            item.setName("精益生产");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产厂家");
            item.setName("常州市百家食品有限公司");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("前茬及本茬\n农药使用情况");
            item.setName("农药驱虫，但是普通农药作用较差，多使用剧毒");
            list.add(item);
        } else if (type == Constants.TYPE_TEST_4_STOREROOM) {
            DetailDisplayItem item = new DetailDisplayItem();
//			item.setTitle(mContext.getString(R.string.task_center_detail_type));
//			item.setName(mContext
//					.getString(R.string.task_center_detail_type4_storeroom));
//			list.add(item);
//			item = new DetailDisplayItem();
            item.setTitle("品种");
            item.setName("脱毒种薯");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种薯级别");
            item.setName("一级种");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("库房负责人");
            item.setName("张伟");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("负责人联系方式");
            item.setName("13836147523");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种子批存储量");
            item.setName("5.5吨");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("收货日期");
            item.setName("2015-02-11");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("入库日期");
            item.setName("2015-03-21");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产方式");
            item.setName("精益生产");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("生产厂家");
            item.setName("常州市百家食品有限公司");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("种子批编");
            item.setName("02112023101235423");
            list.add(item);
            item = new DetailDisplayItem();
            item.setTitle("库房位置");
            item.setName("黑龙江省哈尔滨市平房区松花路9号");
            list.add(item);
        } else {
            T.ss(R.string.err_data);
            finish();
        }
        info.setList(list);
        setData();
    }

    private void getIntentData() {

        Bundle bundle = getIntent().getBundleExtra(
                Constants.JUMP_PARAMETER_KEY_BUNDLE);
        type = bundle.getInt(Constants.JUMP_PARAMETER_KEY_TYPE);
        id = bundle.getString(Constants.JUMP_PARAMETER_KEY_ID);
        final int taskType = bundle.getInt(Constants.JUMP_PARAMETER_KEY_TASK_TYPE);

        info = new Entity_Task();
        infoEntity = (TaskInfoEntity) bundle.getSerializable("entity");
        info.setId(id);
        info.setType(type);
        info.setStatus(taskType);

        mLApplication.mHttpRequestManager.getTaskInfoById(id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Log.i("ttt", responseInfo.result);
                Gson gson = new Gson();
                HttpResponseObject<Entity_Task_Item> object = gson.fromJson(responseInfo.result, new TypeToken<HttpResponseObject<Entity_Task_Item>>() {
                }.getType());
                info.setItem(object.getData());
                if (info.getItem() != null) {
                    getIntentData(info.getItem());
                } else {
                    try {
                       Entity_Task_Item items= mLApplication.dbUtils.findFirst(Selector.from(Entity_Task_Item.class).where("id", "=", id));
                    getIntentData(items);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
//        info.setList(list);

        //   setData();
    }

    private void findViewById() {
        tvStatus = (TextView) findViewById(R.id.tv_task_detail_status);
        tvType = (TextView) findViewById(R.id.tv_task_detail_type);
        lv = (ListView) findViewById(R.id.lv_task_detail);
        btnMotify = (Button) findViewById(R.id.btn_task_detail_motify);
        btnOK = (Button) findViewById(R.id.btn_task_detail_ok);
        btnMotify.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        // uploadImg = infoMotify.getDatas().getImg();
        // if (uploadImg != null && (!"".equals(uploadImg))) {
        // Picasso.with(mContext).load(uploadImg)
        // .placeholder(R.drawable.upload_picture)
        // .error(R.drawable.load_picture_error)
        // /* .resize(40, 40) */.into(ivImage);
        // }
    }

    private void setData() {
        // 初始化view
        tvType.setText(Constants.TASK_DETAIL_TITLE
                + Constants.TASK_TYPE[info.getType() - 1]);
        tvStatus.setText("（"
                + Constants.TASK_CENTER_TAB[info.getStatus()] + "）");
        switch (info.getStatus()) {
            case Constants.PARAMETER_VALUE_TASK0_CONFIRM://待确认
            case Constants.PARAMETER_VALUE_TASK1_HANDLE://待处理
                btnOK.setVisibility(View.VISIBLE);
                btnMotify.setVisibility(View.VISIBLE);
                btnOK.setText(Constants.TASK_DETAIL_BTN[info.getStatus()]);
                break;
            case Constants.PARAMETER_VALUE_TASK3_FAILED://未通过
                btnOK.setVisibility(View.VISIBLE);
                btnMotify.setVisibility(View.GONE);
                btnOK.setText(Constants.TASK_DETAIL_BTN[1]);
                break;
            case Constants.PARAMETER_VALUE_TASK4_SUCCESS://已完成
                btnOK.setVisibility(View.VISIBLE);
                btnMotify.setVisibility(View.GONE);
                btnOK.setText(Constants.TASK_DETAIL_BTN[0]);
                break;
            case Constants.PARAMETER_VALUE_TASK2_APPROVAL://待审核
            case Constants.PARAMETER_VALUE_TASK5_HISTORY_FAILED://历史未通过
            case Constants.PARAMETER_VALUE_TASK5_HISTORY_SUCCESS://历史已完成
                btnOK.setVisibility(View.GONE);
                btnMotify.setVisibility(View.GONE);
                break;
            default:
                break;
        }
//		if(info.getStatus()>Constants.PARAMETER_VALUE_TASK2_APPROVAL){
//			//未通过、已完成
//			btnOK.setVisibility(View.VISIBLE);
//			btnOK.setText(Constants.TASK_DETAIL_BTN[0]);
//			btnMotify.setVisibility(View.GONE);
//		}else if(info.getStatus()>Constants.PARAMETER_VALUE_TASK1_HANDLE){
//			//待审核
//			btnOK.setVisibility(View.GONE);
//			btnMotify.setVisibility(View.GONE);
//		}else{
//			//待确认、待处理
//			btnOK.setVisibility(View.VISIBLE);
//			btnMotify.setVisibility(View.VISIBLE);
//			btnOK.setText(Constants.TASK_DETAIL_BTN[info.getStatus()]);
//		}
        ArrayList<HashMap<String, Object>> mList;
        mList = new ArrayList<HashMap<String, Object>>();
        for (DetailDisplayItem item : info.getList()) {

            mList.add(getMapData(item.getTitle(), item.getName()));
        }
        sAdapter = new SimpleAdapter(mContext, mList,
                R.layout.item_task_detail, new String[]{"title", "content"},
                new int[]{R.id.tv_item_car_title, R.id.tv_item_car_content});
        lv.setAdapter(sAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long l) {
                final EditText et = new EditText(Activity_Task_Detail.this);

               String name= info.getList().get(position).getTitle();
                if(name.equals("面积")) {
                    et.setText(info.getList().get(position).getName());
                    new AlertDialog.Builder(Activity_Task_Detail.this).setTitle("请输入").setIcon(android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!TextUtils.isEmpty(et.getText())) {
                                String str = et.getText().toString();
                                info.getList().get(position).setName(str);
                                ((TextView) view.findViewById(R.id.tv_item_car_content)).setText(str);
                                dialogInterface.dismiss();
                            }

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
            }
        });
    }

    private HashMap<String, Object> getMapData(String title, String name) {
        HashMap<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("content", name);
        return map;
    }

    @Override
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            Entity_Base entity = (Entity_Base) msg.getObj();
            if (entity != null) {
                if (entity.getCode() == 1) {
                    switch (requestId) {

                        case Constants.REQUEST_GET_SUBDISTRICT:
                            break;
                    }

                } else if (entity.getCode() == 0) {
                    if (!LFormat.isEmpty(entity.getMsg())) {
                        T.ss(entity.getMsg());
                    } else {
                        T.ss(R.string.msg_loading_failed);
                    }
                }

            }
        }
        dismissProgressDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_task_detail_motify:
                break;
            case R.id.btn_task_detail_ok:
                switch (info.getStatus()) {
                    case Constants.PARAMETER_VALUE_TASK0_CONFIRM:
                        int num = MApplication.tabNum0;
                        if (num > 0) {
                            MApplication.tabNum0 = num - 1;
                            MApplication.tabNum1 += 1;
                        }
                        Entity_Task_Item item = info.getItem();
                        item.setStatus(HttpRequestManager.status_wait_deal);
                        infoEntity.setStatus(HttpRequestManager.status_wait_deal);
                        try {
                            mLApplication.dbUtils.saveOrUpdate(item);
                            mLApplication.dbUtils.saveOrUpdate(infoEntity);
                            mLApplication.mHttpRequestManager.saveTask(
                                    mLApplication.getUserInfoEntity().getId() + "", item.getId() + "", new RequestCallBack<String>() {
                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            Log.i("isOk", responseInfo.result);

                                        }

                                        @Override
                                        public void onFailure(HttpException error, String msg) {

                                        }
                                    }
                            );
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Constants.PARAMETER_VALUE_TASK1_HANDLE:

                        break;
                    case Constants.PARAMETER_VALUE_TASK2_APPROVAL:
                        num = MApplication.tabNum2;
                        if (num > 0) {
                            MApplication.tabNum2 = num - 1;
                        }


                        break;
                    case Constants.PARAMETER_VALUE_TASK3_FAILED:
                        num = MApplication.tabNum3;
                        if (num > 0) {
                            MApplication.tabNum3 = num - 1;
                        }
                        break;
                    case Constants.PARAMETER_VALUE_TASK4_SUCCESS:
                        num = MApplication.tabNum4;
                        if (num > 0) {
                            MApplication.tabNum4 = num - 1;
                        }
                        break;

                    default:
                        break;
                }

                if (info.getStatus() != Constants.PARAMETER_VALUE_TASK1_HANDLE) {
                    //不是待处理
                    Intent i = new Intent();
                    i.putExtra(Constants.PARAMETER_KEY_TASK_TYPE, info.getStatus());
                    setResult(-1, i);
                    finish();
                } else {
                    //待处理
                    if (info.getType() != Constants.TYPE_TEST_3_HARVEST) {
                        //非测产，用记录页面
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.JUMP_PARAMETER_KEY_ID,
                                info.getId());
                        bundle.putInt(Constants.JUMP_PARAMETER_KEY_TYPE,
                                info.getType());
                        bundle.putSerializable("info", infoEntity);
                        jumpActivityBundleForResult(REQUEST_RECORD, Activity_Handle_TestRecord.class,
                                Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);
                    } else {
                        //测产、收获后
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.JUMP_PARAMETER_KEY_ID,
                                info.getId());
                        bundle.putInt(Constants.JUMP_PARAMETER_KEY_TYPE,
                                info.getType());
                        jumpActivityBundleForResult(REQUEST_HARVEST, Activity_Handle_Harvest.class,
                                Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);
                    }

                }

                break;

            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request == REQUEST_RECORD) {
            if (result == RESULT_OK) {
                int num = MApplication.tabNum1;
                if (num > 0) {
                    MApplication.tabNum1 = num - 1;
                    MApplication.tabNum2 += 1;
                }
                infoEntity.setStatus(HttpRequestManager.status_wait_check);
                try {
                    mLApplication.dbUtils.saveOrUpdate(infoEntity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent();
                i.putExtra(Constants.PARAMETER_KEY_TASK_TYPE, info.getStatus());
                setResult(-1, i);
                finish();
            }

        } else if (request == REQUEST_HARVEST) {
            //TODO
        }

    }


}