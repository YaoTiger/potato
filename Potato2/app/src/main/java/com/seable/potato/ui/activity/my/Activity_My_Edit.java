package com.seable.potato.ui.activity.my;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LBitmap;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.common.Constants;
import com.seable.potato.common.MApplication;
import com.seable.potato.data.entity.Entity_PublicDialogItem;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.data.listener.BaseListener;
import com.seable.potato.data.listener.PublicDialogUtil;
import com.seable.potato.ui.activity.Activity_Login;
import com.seable.potato.ui.activity.taskcenter.Activity_Task_Detail;
import com.seable.potato.ui.view.View_RoundImageView;
import com.seable.potato.util.LFormat;
import com.seable.potato.util.PreferencesService;
import com.seable.potato.util.addImage.ImageManager2;
import com.seable.potato.util.dialog.PublicDialogListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author 王维玉
 * @ClassName: MyEditActivity
 * @Description: 编辑个人资料
 * @date 2015-01-21 10:40
 */
public class Activity_My_Edit extends LActivity implements OnClickListener,
        PublicDialogListener {

    private RelativeLayout rlImage;
    private View_RoundImageView ivImage;
    private TextView tvName, tvDuty;
    private Button pwd;
    // private LinearLayout ShowView;

    /**
     * 业务逻辑
     */
    // private IBizMyActivity biz;

    // 修改密码
    private String strOldPwd, strPassword, strPasswordTwo;
    private EditText etOldPwd, etNewPwd, etNewPwdTwo;
    private boolean aBoolean;
    private  EditText et=null;

    /**
     * 业务逻辑
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // biz = new Impl_MyBizActivity(this);
        setContentView(R.layout.activity_my_edit);
        // ShowView = (LinearLayout) findViewById(R.id.ShowView);
        rlImage = (RelativeLayout) findViewById(R.id.ll_myEdit_image);
        ivImage = (View_RoundImageView) findViewById(R.id.iv_myEdit);
        tvName = (TextView) findViewById(R.id.tv_myEdit_name);
        tvDuty = (TextView) findViewById(R.id.tv_myEdit_duty);
        pwd = (Button) findViewById(R.id.pwd_submit);

        rlImage.setOnClickListener(this);
//        pwd.setOnClickListener(this);
        tvDuty.setOnClickListener(this);
        tvName.setOnClickListener(this);
        // ImageUtils.getInstance(mContext).cancelDisplay(ivImage);
        // ivImage.setImageResource(R.drawable.my_header);
        //
        initLayout();
        setUserInfo();
        closeInputView();
        et=new EditText(this);
    }

    private void closeInputView() {
        // 判断隐藏软键盘是否弹出
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)

        {

            // 隐藏软键盘

            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        }

    }

    private void setUserInfo() {
        // if (MApplication.getUser() == null) {
        // T.ss(R.string.err_data);
        // return;
        // }
        // String str = MApplication.getUser().getImg();
        String str = "djakjgkldfjaglk";
        Picasso.with(mContext).load(str).placeholder(R.drawable.upload_picture)
                .error(R.drawable.default_avatar)
                /* .resize(40, 40) */.into(ivImage);
        // if (str != null || (!"".equals(str))) {
        // Picasso.with(mContext).load(str)
        // .placeholder(R.drawable.upload_picture)
        // .error(R.drawable.load_picture_error)
        // /* .resize(40, 40) */.into(ivImage);
        // }
        // str = MApplication.getUser().getUserName();
        // if (str != null || (!"".equals(str))) {
        // tvName.setText(str);
        // }
        // str = MApplication.getUser().getDutyNumber();
        // if (str != null || (!"".equals(str))) {
        // if (str.length() > 8) {
        // String outMobile = str.replaceAll("(?<=\\d{3})\\d(?=\\d{3})",
        // "*");
        // tvDuty.setText(outMobile);
        //
        // } else {
        //
        // tvDuty.setText(str);
        // }
        // }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_myEdit_image:
                ArrayList<Entity_PublicDialogItem> itemList = new ArrayList<Entity_PublicDialogItem>();
                for (int i = 0; i < Constants.publicDialogItem.length; i++) {
                    Entity_PublicDialogItem item = new Entity_PublicDialogItem();
                    item.setName(Constants.publicDialogItem[i]);
                    item.setCode(i);
                    itemList.add(item);
                }
                PublicDialogUtil.getInstance(Activity_My_Edit.this, mContext
                                .getResources().getString(R.string.login_upload_titel),
                        itemList, this);
                break;
//            case R.id.pwd_submit:
//                checkInfos();
//                // View_PopWindowBottomInput.getInstance(mContext, llShowView,
//                // View_PopWindowBottomInput.TYPE_TEXT,"修改用户名",tvName.getText().toString(),
//                // new BaseListener() {
//                   // @Override
//                // public void onResult(int result, Object obj1, Object obj2) {
//                // if (result>0) {
//                // T.ss("用户名编辑成功，赶紧提交吧~");
//                // tvName.setText((String)obj2);
//                // } else {
//                // T.ss((String) obj2);
//                // }
//                // }
//                // }).popShow();
//                break;
            case R.id.tv_myEdit_duty:

                new AlertDialog.Builder(this).setTitle("请输入").setIcon(android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!TextUtils.isEmpty(et.getText())) {
                            String str = et.getText().toString();
                            tvDuty.setText(str);
                            modify(null,null,str,null,null,dialogInterface);

                        }

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.tv_myEdit_name:
               et.setText("");
                new AlertDialog.Builder(this).setTitle("请输入").setIcon(android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!TextUtils.isEmpty(et.getText())) {
                            String str = et.getText().toString();
                            tvName.setText(str);
                            modify(null, str, null, null, null,dialogInterface);

                        }

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                break;

        }
    }

    @Override
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            Entity_Base entity = (Entity_Base) msg.getObj();
            if (entity != null) {
                switch (entity.getCode()) {
                    case 1:

                        switch (requestId) {
                            case Constants.REQUEST_MOTIFY_PASSWORD:
                                T.ss("修改成功，请重新登陆");
                                // TODO 如果需要同步密码 再次操作
                                // defaultFinishActivity();
                                PreferencesService service = new PreferencesService(
                                        mContext);
                                service.clearLoginInfo();
                                MApplication.get().killActivities();
                                defaultJumpActivity(Activity_Login.class);
                                break;
                            // case Constants.REQUEST_SET_MY_HEADER:
                            //
                            // T.ss(R.string.edit_img_success);
                            // Entity_DataBase base = (Entity_DataBase) msg.getObj();
                            // MApplication.getUser().setImg(base.getDatas());
                            // if (bitmap != null) {
                            // ivImage.setImageBitmap(bitmap);
                            //
                            // } else {
                            // // ImageManager2.from(mContext).displayImage(ivImage,
                            // // base.getDatas(),
                            // // R.drawable.icon_headportrait, 100, 100);
                            // Picasso.with(mContext).load(base.getDatas())
                            // .placeholder(R.drawable.upload_picture)
                            // .error(R.drawable.load_picture_error)
                            // .into(ivImage);
                            // }
                            //
                            // break;
                            default:
                                break;
                        }
                        break;
                    case 0:

                        switch (requestId) {
                            case Constants.REQUEST_MOTIFY_PASSWORD:
                                if (!LFormat.isEmpty(entity.getMsg())) {
                                    T.ss(entity.getMsg());
                                } else {
                                    T.ss("修改密码失败");
                                }
                                break;
                            // case Constants.REQUEST_SET_MY_HEADER:
                            // if (!LFormat.isEmpty(entity.getMsg())) {
                            // T.ss(entity.getMsg());
                            // } else {
                            // T.ss(R.string.edit_img_failed);
                            // }
                            // break;
                            default:
                                if (!LFormat.isEmpty(entity.getMsg())) {
                                    T.ss(entity.getMsg());
                                } else {
                                    T.ss(R.string.err_data);
                                }
                                break;
                        }

                        break;
                    default:
                        break;
                }
            } else {
                T.ss(R.string.edit_img_failed);
            }
        } else {
            T.ss(R.string.edit_img_failed);
        }
        dismissProgressDialog();
    }

    // TODO==========================修改密码===============================

    private void initLayout() {
        etOldPwd = (EditText) findViewById(R.id.pwd_ori);
        etNewPwd = (EditText) findViewById(R.id.pwd_new);
        etNewPwdTwo = (EditText) findViewById(R.id.pwd_repeat);
        Button btnSubmit = (Button) findViewById(R.id.pwd_submit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkInfos()) {
                    // TODO
                    T.ss("修改成功，请重新登陆");
                    // TODO 如果需要同步密码 再次操作
                    // defaultFinishActivity();
                    PreferencesService service = new PreferencesService(
                            mContext);
                    service.clearLoginInfo();
                    MApplication.get().killActivities();
                    defaultJumpActivity(Activity_Login.class);
                    // biz.doChangePass(strOldPwd, strPassword, strPasswordTwo);
                }
            }
        });
    }

    private boolean checkInfos() {
        strOldPwd = etOldPwd.getText().toString();
        if (TextUtils.isEmpty(strOldPwd)) {
            T.ss("原密码不能为空");
            return false;
        }
        strPassword = etNewPwd.getText().toString();
        if (TextUtils.isEmpty(strPassword)) {
            T.ss("新密码不能为空");
            return false;
        }

        strPasswordTwo = etNewPwdTwo.getText().toString();
        if (LFormat.isEmpty(strPasswordTwo)
                || !LFormat.isEqual(strPassword, strPasswordTwo)) {
            T.ss("两次密码不相同");
            return false;
        }

        if (strPassword.length() < 6 || strPasswordTwo.length() < 6) {
            T.ss("密码不能少于6位");
            return false;
        }

        // TODO 如果密码需要持久化本地 则需要本地校验
        // if (!LFormat.isEqual(mUser.password, strOldPwd)) {
        // T.showShort("原密码输入错误");
        // return false;
        // }
        return true;
    }

    // TODO==========================拍照相册===============================
    public static final int RESULT_CODE_ME = 11;
    private static final int CAMERA_WITH_DATA = 15; // 拍照
    private static final int PHOTO_PICKED_WITH_DATA = 16; // gallery
    private static File mCurrentPhotoFile;
    private static String uploadImg;

    // 调用图片剪辑程序 剪裁后的图片跳转到新的界面
    private static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("return-data", true);

        return intent;
    }

    protected void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (Exception e) {
            T.ss("操作失败");
        }
    }

    private static Bitmap bitmap;

    private void uploadPhoto(Intent data) {
        Bundle extras = null;
        if (data != null) {
            extras = data.getExtras();
        }
        if (extras != null) {
            bitmap = (Bitmap) extras.get("data");
        }
        // if(bitmap == null){
        // bitmap = (Bitmap) extras.get("dat");
        // }
        if (bitmap != null) {
            uploadImg = getImgFileUrl();
            // biz.setMyHeader(url);
            ivImage.setImageBitmap(bitmap);
        } else {

            Uri selectedImageUri = data.getData();
            uploadImg = getPath(selectedImageUri);
            // biz.setMyHeader(selectedImagePath);
            // reload();
            setImage(uploadImg);
        }
    }

    private void setImage(String imgUrl) {
        if (imgUrl == null || "".equals(imgUrl)) {
            ivImage.setImageResource(R.drawable.camera_default);
        } else if (imgUrl.contains("http://")) {
            Picasso.with(mContext).load(imgUrl)
                    .placeholder(R.drawable.upload_picture)
                    .error(R.drawable.load_picture_error)
                        /* .resize(40, 40) */.into(ivImage);
        } else {
            ImageManager2.from(mContext).displayImage(ivImage, imgUrl,
                    R.drawable.load_picture_error, 100, 100);
        }
    }

    private String getImgFileUrl() {
        String filename = "NO" + System.currentTimeMillis() + ".jpg";
        String catchUrl = MApplication.get().getCacheUrl();
        String url = LBitmap.saveBmpToSd(bitmap, catchUrl, filename, 99);
        return url;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CAMERA_WITH_DATA: // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                    doCropPhoto(mCurrentPhotoFile);
                    break;
                case PHOTO_PICKED_WITH_DATA: // 相册
                    // 上传照片的方法

                    uploadPhoto(data);
                    break;
            }
        }
    }

    // 拍照
    private void getPicFromCapture() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = MApplication.get().getPhotoCache();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            mCurrentPhotoFile = new File(dir, /* getPhotoFileName() */
                    "user_header.jpg"); // 命名
            Intent intent = new Intent(
                        /* "android.media.action.IMAGE_CAPTURE" */MediaStore.ACTION_IMAGE_CAPTURE); // 存储
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(mCurrentPhotoFile));
            this.startActivityForResult(intent, CAMERA_WITH_DATA);
        }
    }

    // 用当前时间给取得的图片命名
    @SuppressLint("SimpleDateFormat")
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }


    // 相册
    private void getPicFromContent() {
        // try {
        Intent intent = getPhotoPickIntent();
        // startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        // } catch (Exception e) {
        // T.ss("操作失败");
        // }
        // Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PHOTO_PICKED_WITH_DATA);
    }

    // 对图片进行剪裁
    private static Intent getPhotoPickIntent() {
        // Intent intent = new Intent(Intent.ACTION_PICK, null);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    public void onPublicDialogResult(int position) {
        try {
            switch (position) {
                case 0:
                    // 手机照相
                    getPicFromCapture();
                    // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // startActivityForResult(intent, Constants.IMAGE_CODE_TAKING);
                    break;
                case 1:
                    // 手机相册
                    getPicFromContent();
                    // Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    // getAlbum.setType(Constants.IMAGE_TYPE);
                    // startActivityForResult(getAlbum, Constants.IMAGE_CODE_ALBUM);
                    break;
            }

        } catch (Exception e) {
            return;
        } finally {
            ivImage.setClickable(true);
        }

    }


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
            // 获取输入框当前的location位置
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

    public void modify(File filename,String name,String job,String oldpwd,String newpwd,final DialogInterface dialog){
        mLApplication.mHttpRequestManager.modifyUserInfo(mLApplication.userInfoEntity.getId(), filename, name, job, oldpwd, newpwd, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Log.i("ttt",responseInfo.result);
                dialog.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
}
