package com.seable.potato.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.seable.potato.R;
import com.seable.potato.biz.L;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.T;
import com.seable.potato.data.entity.Entity_Sql_User;
import com.seable.potato.data.entity.Entity_UserPass;
import com.seable.potato.data.entity.HttpResponseObject;
import com.seable.potato.data.entity.tigerentity.UserInfoEntity;
import com.seable.potato.ui.adapter.Adapter_PopListData;
import com.seable.potato.ui.view.View_ListPopupWindow;
import com.seable.potato.ui.view.View_TitleBar;
import com.seable.potato.util.PreferencesService;

import org.w3c.dom.Text;

import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 *
 * @author YuFeng
 */
public class Activity_Login extends LActivity implements OnClickListener {

    private EditText etName, etPass;
    // private CheckBox cbPass, cbLogin;
    // private static boolean fSavePass, fSelfLogin;
    private static Entity_UserPass info;
    private PreferencesService service;
    /**
     * 业务逻辑
     */
    private View_TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(mContext);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(mContext);
        super.onPause();
    }

    private void initLayout() {
        titleBar = (View_TitleBar) findViewById(R.id.TitleBar);
        titleBar.setBackImshow(false);
        etName = (EditText) findViewById(R.id.login_name_edt);
        etPass = (EditText) findViewById(R.id.login_pwd_edt);
        TextView tvLogin = (TextView) findViewById(R.id.login_commit);
        tvLogin.setOnClickListener(this);
        service = new PreferencesService(mContext);
        info = service.getUserInfo(mContext);
        String[] values=mLApplication.mShareDataManager.getUserNamePsw();
        if (!TextUtils.isEmpty(values[0])&&!TextUtils.isEmpty(values[1])) {

            etName.setText(info.getUsername());
            etPass.setText(info.getPassword());
            login(values[0],values[1]);
        } else {
            etName.setText("");
            etPass.setText("");
            findViewById(R.id.show_history_accounts).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            showHistoryUser();
                        }
                    });
        }
    }

    // 客户端登录校验
    private boolean checkUserInfo() {
        // TODO 手机登录开启注释
        String u = etName.getText().toString().trim();
        if (TextUtils.isEmpty(u)) {
            T.ss("用户名不能为空");
            return false;
        }
        String p = etPass.getText().toString();
        if (TextUtils.isEmpty(p)) {
            T.ss("密码不能为空");
            return false;
        } else if (p.length() < 5 || p.length() > 18) {
            T.ss("请输入6到18位密码");
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_commit:
                L.i("登录");
                if (checkUserInfo()) {
                    String name = etName.getText().toString().trim();
                    String pass = etPass.getText().toString();
                    login(name, pass);
//                    //TODO
//                    defaultJumpActivityAndFinsh(Activity_Main.class);
//                    info.setUsername(etName.getText().toString().trim());
//                    info.setPassword(etPass.getText().toString());
//                    service.saveUserInfo(mContext, info);
                }


                break;

        }
    }

    // =======================历史记录============================================
    private View_ListPopupWindow mPopWindow;
    private Adapter_PopListData<Entity_Sql_User> mAdapter;

    private void showHistoryUser() {
        if (mPopWindow == null) {
            return;
        }
        if (!mPopWindow.isShowing()) {
            mPopWindow.show();
        } else {
            mPopWindow.dismiss();
        }
    }

    // if (biz.isLocalInfo()) {
    // biz.deleteLocalAllInfo();
    // }
    // for (int i = 0; i < num.length; i++) {
    // Entity_Sql_Scancode info = new Entity_Sql_Scancode();
    // info.setId(i);
    // info.setmScancodeNum(num[i]);
    // info.setmScancodeContent("这是第" + i + "条数据的内容，条码号：" + num[i] + "。。。");
    // biz.addLocalInfo(info);
    // }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void login(String username, final String pwd) {
        showProgressDialog("正在登陆",false,false);
        mLApplication.mHttpRequestManager.loginRequest(username, pwd, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                Gson gson = new Gson();
                HttpResponseObject<UserInfoEntity> object = gson.fromJson(result, new TypeToken<HttpResponseObject<UserInfoEntity>>() {
                }.getType());
                if (object.isSuccess()) {
                    mLApplication.userInfoEntity = object.getData();
                    if (mLApplication.userInfoEntity == null) {
                        T.ss("登陆失败，请校验姓名和密码是否正确");
                        return;
                    }
                    Log.i("????", mLApplication.userInfoEntity.getUserName() + "?????????");
                    mLApplication.mShareDataManager.saveSharedUserInfo(mLApplication.userInfoEntity.getUserName(), pwd, mLApplication.userInfoEntity.getId());
                    defaultJumpActivityAndFinsh(Activity_Main.class);
                    dismissProgressDialog();
                    finish();
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {
dismissProgressDialog();
                T.ss("登陆失败，请检查网络");
            }
        });
    }
}
