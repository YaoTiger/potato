package com.seable.potato.ui.activity.taskcenter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.common.Constants;
import com.seable.potato.data.entity.CheckEntity;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.data.entity.tigerentity.CheckTaskEntity;
import com.seable.potato.data.entity.tigerentity.TaskInfoEntity;
import com.seable.potato.ui.activity.ApplyDialog;
import com.seable.potato.ui.adapter.Adapter_Record;
import com.seable.potato.util.LFormat;

import java.util.ArrayList;

/**
 * @author 王维玉
 * @ClassName: Activity_Test_Record
 * @Description: 检验记录
 * @date 2015-03-31 14:51
 */
public class Activity_Handle_TestRecord extends LActivity implements
        OnClickListener {
    public final static int REQUEST_CODE = 300;
    private static Button btnOK, btnArea, btnPreview;

    // private static GridView mGridView;
    // private static List<String> list;
    // private static SimpleAdapter sAdapter;
    // private static ArrayList<HashMap<String, Object>> mList;
    private GridView gridView;
    private ArrayList<String> dataList = new ArrayList<String>();
    private Adapter_Record gridImageAdapter;
    private static int type;
    private static String id;
    private ApplyDialog dialog;
    private double aireNum;
    private TaskInfoEntity info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_handle_testrecord);
        findViewById();
        getIntentData();
        dialog = new ApplyDialog(this);
    }

    private void getIntentData() {

        Bundle bundle = getIntent().getBundleExtra(
                Constants.JUMP_PARAMETER_KEY_BUNDLE);
        id = bundle.getString(Constants.JUMP_PARAMETER_KEY_ID);
        type = bundle.getInt(Constants.JUMP_PARAMETER_KEY_TYPE);
        info = (TaskInfoEntity) bundle.getSerializable("info");
        if (type == Constants.TYPE_TEST_4_STOREROOM) {
            btnArea.setVisibility(View.GONE);
        }
    }

    private void findViewById() {
        gridView = (GridView) findViewById(R.id.gv_test_record);
        btnOK = (Button) findViewById(R.id.btn_task_record_ok);
        btnArea = (Button) findViewById(R.id.btn_task_record_area);
        btnPreview = (Button) findViewById(R.id.btn_task_record_preview);
        btnArea.setOnClickListener(this);
        btnPreview.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        dataList.add("camera_default");
        gridImageAdapter = new Adapter_Record(this, dataList);
        gridView.setAdapter(gridImageAdapter);
        initListener();
    }

    private void initListener() {

        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {

            private AlertDialog dialog;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

//				if ((position < dataList.size() - 1 && dataList.size() > 1)) {//点击最后一项
                if ((position == 0 && dataList.size() > 0)) {//点击第一项，添加
                    addRecord();
                } else {
                    // 点击修改
                    motifyRecord(position);
                }
            }
        });
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
            case R.id.btn_task_record_preview:
                T.ss("敬请期待...");
                break;
            case R.id.btn_task_record_area:
                dialog.show();
                break;
            case R.id.btn_task_record_ok:
                if (dataList.size() > 1) {
                    mLApplication.mHttpRequestManager.saveInfo(info.getId(),
                            info.getId(), mLApplication.getUserInfoEntity().getId(),
                            entity.get(0).entities.get(0).getNum() + "",
                            entity.get(0).entities.get(1).getNum() + "",
                            entity.get(0).entities.get(2).getNum() + "",
                            entity.get(0).entities.get(3).getNum() + "",
                            entity.get(0).entities.get(4).getNum() + "",
                            entity.get(0).entities.get(5).getNum() + "",
                            entity.get(0).entities.get(6).getNum() + "",
                            entity.get(0).entities.get(7).getNum() + "",
                            entity.get(0).entities.get(8).getNum() + "",
                            entity.get(0).entities.get(9).getNum() + ""
                            , entity.get(0).recordType,
                            entity.get(0).picId,
                            entity.get(0).remark,
                            new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    Log.i("?????", responseInfo.result);
                                    setResult(RESULT_OK);
                                    finish();

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {

                                }
                            });

                } else {
                    T.ss(R.string.task_center_test_record_not_null);
                }

                break;

            default:
                break;
        }
    }

    private void addRecord() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.JUMP_PARAMETER_KEY_ID, -1);
        bundle.putBoolean(Constants.JUMP_PARAMETER_KEY_TYPE, true);
        jumpActivityBundleForResult(REQUEST_CODE, Activity_Check.class,
                Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);

    }

    private void motifyRecord(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.JUMP_PARAMETER_KEY_ID, 1);
        bundle.putBoolean(Constants.JUMP_PARAMETER_KEY_TYPE, false);
        Log.i("ttt", entity.get(position - 1).entities.toString());
        bundle.putSerializable("check_entity", entity.get(position - 1));
        jumpActivityBundleForResult(REQUEST_CODE, Activity_Check.class,
                Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);

    }

    ArrayList<CheckTaskEntity> entity = new ArrayList<CheckTaskEntity>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                int listNum = dataList.size();
                boolean isadd = data.getBooleanExtra("isAdd", false);
                CheckTaskEntity checkTaskEntity = (CheckTaskEntity) data.getSerializableExtra("check_entity");
//                if(isadd) {
                entity.add(checkTaskEntity);


                if (!isadd) {
                    entity.remove(entity.size() - 2);
                } else
                    dataList.add(mContext
                            .getString(R.string.task_center_test_record_point)
                            + listNum);
                gridImageAdapter.notifyDataSetChanged();
//                }else {
//                  int index=  entity.indexOf(checkTaskEntity);
//
//                    if(index!=-1)
//                    entity.get(index).clone(checkTaskEntity);
//                }
//                //添加在最前

//                //添加在最后
//				dataList.add(listNum-1,mContext
//						.getString(R.string.task_center_test_record_point)
//						+ listNum);
//                updatePic();

            }
        }

    }

    public void getAreNum(double aireNum) {
        this.aireNum = aireNum;
    }


}