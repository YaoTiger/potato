package com.seable.potato.ui.activity.taskcenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.seable.potato.data.entity.Entity_Check;
import com.seable.potato.data.entity.Entity_PublicDialogItem;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.data.entity.tigerentity.CheckTaskEntity;
import com.seable.potato.data.listener.PublicDialogUtil;
import com.seable.potato.ui.adapter.Adapter_Check;
import com.seable.potato.ui.view.View_TitleBar;
import com.seable.potato.util.LFormat;
import com.seable.potato.util.addImage.ImageManager2;
import com.seable.potato.util.dialog.BaseEffects;
import com.seable.potato.util.dialog.Effectstype;
import com.seable.potato.util.dialog.PublicDialogListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Activity_Check
 * @Description: 检测记录
 * @date 2015-03-27 13:30
 */
public class Activity_Check extends LActivity implements OnClickListener,
        PublicDialogListener {

    private static GridView mListView;
    private static Adapter_Check mAdapter;
    private static List<Entity_Check> list;

    private View_TitleBar mTitleBar;
    private Button btnOK, btnAdd;
    private ImageView ivImage;
    private CheckBox cbSample, cbCommon;
    private TextView tvSample, tvCommon;
    private EditText etAddImage, etRemark;
    private RelativeLayout ShowView;

    // upload pic
    private static String uploadImg;
    private static final int CAMERA_WITH_DATA = 15; // 拍照
    private static final int PHOTO_PICKED_WITH_DATA = 16; // gallery
    private static File mCurrentPhotoFile;
    private static Effectstype effect;
    private static AlertDialog dialog;
    private CheckTaskEntity entity;

    /**
     * 业务逻辑
     */
//	private IBizSubdistrictActivity biz;
    private static int id;
    private static Boolean isAddRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        findViewById();
        getIntentData();
        closeInputView();


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

    private void getIntentData() {

        Bundle bundle = getIntent().getBundleExtra(
                Constants.JUMP_PARAMETER_KEY_BUNDLE);
        id = bundle.getInt(Constants.JUMP_PARAMETER_KEY_ID);
        isAddRecord = bundle.getBoolean(Constants.JUMP_PARAMETER_KEY_TYPE);
        entity = (CheckTaskEntity) bundle.getSerializable("check_entity");
//        if(entity==null);
//        entity=new CheckTaskEntity();
        // 根据id搜索数据库,id在数据库有值
        if (!isAddRecord) {
            // 修改记录
            mTitleBar.setTitleName(mContext
                    .getString(R.string.task_center_test_record_motify));
        } else {
            // 添加记录
            mTitleBar.setTitleName(mContext
                    .getString(R.string.task_center_test_record_add));
            if (entity == null) ;
            entity = new CheckTaskEntity();
        }
        Log.i("tttt", entity.entities.toString());
        setDataInfo(null);

    }

    private void findViewById() {
        uploadImg = "";
//		biz = new Impl_SubdistrictBizActivity(this);
        mTitleBar = (View_TitleBar) findViewById(R.id.TitleBar);
        ShowView = (RelativeLayout) findViewById(R.id.ShowView);
        ivImage = (ImageView) findViewById(R.id.iv_Image);
        tvSample = (TextView) findViewById(R.id.tv_Image_sample);
        tvCommon = (TextView) findViewById(R.id.tv_Image_common);
        cbSample = (CheckBox) findViewById(R.id.cb_Image_sample);
        cbCommon = (CheckBox) findViewById(R.id.cb_Image_common);
        etAddImage = (EditText) findViewById(R.id.et_Image);
        etRemark = (EditText) findViewById(R.id.et_check);
        mListView = (GridView) findViewById(R.id.lv_check);
        btnOK = (Button) findViewById(R.id.btn_check_save);
        btnAdd = (Button) findViewById(R.id.btn_check_add);
        setCheckBox(0);
//		biz.getSubdistrictList();

        initView();
        // uploadImg = infoMotify.getDatas().getImg();
        // if (uploadImg != null && (!"".equals(uploadImg))) {
        // Picasso.with(mContext).load(uploadImg)
        // .placeholder(R.drawable.upload_picture)
        // .error(R.drawable.load_picture_error)
        // /* .resize(40, 40) */.into(ivImage);
        // }
    }

    private void initView() {
        // 初始化view
        btnOK.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        ivImage.setOnClickListener(this);
        tvSample.setOnClickListener(this);
        tvCommon.setOnClickListener(this);
        cbSample.setOnClickListener(this);
        cbCommon.setOnClickListener(this);
        closeInputView();
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
                            setDataInfo(msg);
                            initView();
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

    private void setDataInfo(LMessage msg) {
        String[] str = {"混杂", "类病毒", "总病毒", "重花叶", "卷叶", "环腐", "青枯",
                "黑胫病", "丝核菌立枯病", "晚疫病",};
//		Entity_SubdistrictListBase base = (Entity_SubdistrictListBase) msg
//				.getObj();
//		Entity_SubdistrictList listData = base.getDatas();
//		List<Entity_Subdistrict> mlist = listData.getCivil();
        list = new ArrayList<Entity_Check>();
        if (entity != null && entity.entities != null && entity.entities.size() > 0) {
            list = entity.entities;
        } else {
            for (int n = 0; n < str.length; n++) {
                Entity_Check entity = new Entity_Check();
                // entity.setName(mlist.get(n).getTitle());
                entity.setName(str[n]);
                entity.setNum(0);
                list.add(entity);
            }
        }

        mAdapter = new Adapter_Check(mContext, list);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_save:
//                if (isAddRecord) {
                String imgText = "";
                if (!TextUtils.isEmpty(etAddImage.getText())) {
                    imgText = etAddImage.getText().toString();
                }
                updatePic(uploadImg, imgText);

//                }

                break;
            case R.id.btn_check_add:
//                T.ss("正在建设中...");
                break;
            case R.id.iv_Image:
                ivImage.setClickable(false);
                if (uploadImg != null && (!"".equals(uploadImg))) {
                    View viewDialog = View.inflate(Activity_Check.this,
                            R.layout.dialog_two_button, null);
                    dialog = new AlertDialog.Builder(Activity_Check.this)
                            .setTitle("").setView(viewDialog).create();
                    dialog.show();
                    effect = Effectstype.SlideRight;
                    BaseEffects animator = effect.getAnimator();
                    animator.start(viewDialog);
                    Button negative = (Button) viewDialog
                            .findViewById(R.id.negativeButton);// ȡ��
                    Button positive = (Button) viewDialog
                            .findViewById(R.id.positiveButton);// ɾ��
                    negative.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            ivImage.setClickable(true);
                        }
                    });
                    positive.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            uploadImg = "";
                            dialog.dismiss();
                            ivImage.setClickable(true);
                            setImage(uploadImg);
                        }
                    });
                } else {

                    ArrayList<Entity_PublicDialogItem> itemList = new ArrayList<Entity_PublicDialogItem>();
                    for (int i = 0; i < Constants.publicDialogItem.length; i++) {
                        Entity_PublicDialogItem item = new Entity_PublicDialogItem();
                        item.setName(Constants.publicDialogItem[i]);
                        item.setCode(i);
                        itemList.add(item);
                    }
                    PublicDialogUtil.getInstance(Activity_Check.this, mContext
                                    .getResources().getString(R.string.login_upload_titel),
                            itemList, this);
                }
                break;
            case R.id.tv_Image_sample:
            case R.id.cb_Image_sample:
                setCheckBox(0);
                break;
            case R.id.tv_Image_common:
            case R.id.cb_Image_common:
                setCheckBox(1);
                break;

            default:
                break;
        }

    }

    private void setCheckBox(int position) {
        switch (position) {
            case 0:
                cbSample.setBackgroundResource(R.drawable.cb_selected);
                cbSample.setChecked(true);
                cbCommon.setBackgroundResource(R.drawable.cb_no_selected);
                cbCommon.setChecked(false);
                break;
            case 1:
                cbCommon.setBackgroundResource(R.drawable.cb_selected);
                cbCommon.setChecked(true);
                cbSample.setBackgroundResource(R.drawable.cb_no_selected);
                cbSample.setChecked(false);
                break;

            default:
                break;
        }

    }

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
//        if (extras != null) {
//            bitmap = (Bitmap) extras.get("data");
//
//        }
//        // if(bitmap == null){
//        // bitmap = (Bitmap) extras.get("dat");
//        // }
//        if (bitmap != null) {
//            uploadImg = getImgFileUrl();
//            // biz.setMyHeader(url);
//            ivImage.setImageBitmap(bitmap);
//        } else {

        Uri selectedImageUri = data.getData();
        uploadImg = FileManagementUtil.getPathFromUrl(selectedImageUri, this)[0];
        //  uploadImg = getPath(selectedImageUri);
        // biz.setMyHeader(selectedImagePath);
        // reload();
        setImage(uploadImg);
//        }
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
                    // uploadPhoto(data);
                    break;
                case PHOTO_PICKED_WITH_DATA: // 相册
                    // 上传照片的方法

                    uploadPhoto(data);
                    break;
            }
        }
    }

    // 拍照
    private void getPicFromCapture(String name) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = MApplication.get().getPhotoCache();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            mCurrentPhotoFile = new File(dir, /* getPhotoFileName() */
                    name + ".jpg"); // 命名
            Intent intent = new Intent(
                    /* "android.media.action.IMAGE_CAPTURE" */MediaStore.ACTION_IMAGE_CAPTURE); // 存储
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(mCurrentPhotoFile));
            this.startActivityForResult(intent, CAMERA_WITH_DATA);
        }
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
                    getPicFromCapture("photot" + position);
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

    public void updatePic(String filePath, String remark) {
        Log.i("ttt", filePath + "??????");
        if (TextUtils.isEmpty(filePath)) {

            if (!TextUtils.isEmpty(etRemark.getText()))
                entity.remark = etRemark.getText().toString();
            entity.entities = mAdapter.getMyList();
            Log.i("ttt", entity.entities.toString());
            Intent intent = getIntent();
            intent.putExtra("isAdd", isAddRecord);
            intent.putExtra("check_entity", entity);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        showProgressDialog("正在添加任务", false, false);
        String fileName = filePath.substring(filePath.lastIndexOf("/"));
        mLApplication.mHttpRequestManager.updateImg(mLApplication.getUserInfoEntity().getId(), fileName, filePath, remark, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    boolean isSuccess = object.getBoolean("success");
                    entity.picId = object.getJSONObject("data").getString("id");
                    T.ss("图片添加成功");
                    if (!TextUtils.isEmpty(etRemark.getText()))
                        entity.remark = etRemark.getText().toString();
                    entity.entities = mAdapter.getMyList();
                    Intent intent = getIntent();
                    intent.putExtra("check_entity", entity);
                    intent.putExtra("isAdd", isAddRecord);
                    setResult(RESULT_OK, intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
                finish();

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                dismissProgressDialog();
                T.ss("图片添加失败");
            }
        });
    }
}