package com.seable.potato.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.ui.adapter.Adapter_Album_GridView;
import com.seable.potato.util.FileUtil;
import com.seable.potato.util.addImage.ImageManager2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 王维玉
 * @ClassName: AlbumActivity
 * @Description: 选择相泽中照片或拍照
 * @date 2015-01-17 13:30
 */
public class Activity_Album extends LActivity {

    // 上传头像选择拍照
    public final static int IMAGE_CODE_TAKING = 1;
    // 上下文对象
    private GridView gridView;
    private ArrayList<String> dataList = new ArrayList<String>();
    private HashMap<String, ImageView> hashMap = new HashMap<String, ImageView>();
    private ArrayList<String> selectedDataList = new ArrayList<String>();
    private ProgressBar progressBar;
    private Adapter_Album_GridView gridImageAdapter;
    private Button okButton;
    public static int size = 1;// 选择数量
    // 带返回值跳转
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Intent intent = getIntent();
        // size = intent.getIntExtra("size", 0) ;
        Bundle bundle = intent.getExtras();
        selectedDataList = (ArrayList<String>) bundle
                .getSerializable("dataList");
        init();
        initListener();
    }

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.myGrid);
        gridImageAdapter = new Adapter_Album_GridView(this, dataList,
                selectedDataList);
        gridView.setAdapter(gridImageAdapter);
        refreshData();
        okButton = (Button) findViewById(R.id.ok_button);
        initSelectImage();
    }

    private void initSelectImage() {
        if (selectedDataList == null)
            return;
        for (final String path : selectedDataList) {
            ImageView imageView = (ImageView) LayoutInflater.from(
                    mContext).inflate(R.layout.choose_imageview,
                    null, false);
            hashMap.put(path, imageView);
            ImageManager2.from(mContext).displayImage(imageView,
                    path, R.drawable.camera_default, 200, 200);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    removePath(path);
                    gridImageAdapter.notifyDataSetChanged();
                }
            });
        }
        okButton.setText("完成(" + selectedDataList.size() + "/" + size + ")");
    }

    private void initListener() {

        gridImageAdapter
                .setOnItemClickListener(new Adapter_Album_GridView.OnItemClickListener() {

                    @Override
                    public void onItemClick(final ToggleButton toggleButton,
                                            int position, final String path, boolean isChecked) {

                        if (selectedDataList.size() >= size) {
                            toggleButton.setChecked(false);
                            if (!removePath(path)) {
                                Toast.makeText(mContext,
                                        "只能选择" + size + "张图片", 200).show();
                            }
                            return;
                        }
                        if (position == 0) {
                            try {

                                Intent intent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, IMAGE_CODE_TAKING);
                                return;
                            } catch (Exception e) {

                                return;
                            }
                        }
                        ViewGroup pGroup = (ViewGroup) toggleButton.getParent();
                        final ImageView check = (ImageView) pGroup
                                .findViewById(R.id.check);
                        if (isChecked) {
                            check.setVisibility(View.VISIBLE);
                            if (!hashMap.containsKey(path)) {
                                ImageView imageView = (ImageView) LayoutInflater
                                        .from(mContext).inflate(
                                                R.layout.choose_imageview,
                                                null, false);

                                hashMap.put(path, imageView);
                                selectedDataList.add(path);
                                ImageManager2.from(mContext)
                                        .displayImage(imageView, path,
                                                R.drawable.camera_default, 200,
                                                200);
                                imageView
                                        .setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                toggleButton.setChecked(false);
                                                removePath(path);
                                                check.setVisibility(View.GONE);
                                            }
                                        });
                                okButton.setText("完成("
                                        + selectedDataList.size() + "/" + size
                                        + ")");
                            }
                        } else {
                            check.setVisibility(View.GONE);
                            removePath(path);
                        }
                    }
                });

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("dataList", selectedDataList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private boolean removePath(String path) {
        if (hashMap.containsKey(path)) {
            hashMap.remove(path);
            removeOneData(selectedDataList, path);
            okButton.setText("完成(" + selectedDataList.size() + "/" + size + ")");
            return true;
        } else {
            return false;
        }
    }

    private void removeOneData(ArrayList<String> arrayList, String s) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(s)) {
                arrayList.remove(i);
                return;
            }
        }
    }

    private void refreshData() {

        new AsyncTask<Void, Void, ArrayList<String>>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected ArrayList<String> doInBackground(Void... params) {
                ArrayList<String> listDirlocal = listAlldir();
                return listDirlocal;
            }

            protected void onPostExecute(ArrayList<String> tmpList) {

                if (mContext == null
                        || ((Activity) mContext).isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                dataList.clear();
                dataList.addAll(tmpList);
                gridImageAdapter.notifyDataSetChanged();
                return;

            }

            ;

        }.execute();

    }

    /**
     * 获取图库图片所有路径
     *
     * @return
     */
    private ArrayList<String> listAlldir() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = intent.getData();
        ArrayList<String> list = new ArrayList<String>();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            list.add(new File(path).getAbsolutePath());
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            Bitmap bitmap = null;
            if (requestCode == IMAGE_CODE_TAKING) {
                bitmap = (Bitmap) data.getExtras().get("data");
                SimpleDateFormat sDateFormat = new SimpleDateFormat(
                        "yyyyMMddhhmmss");
                String date = sDateFormat.format(new java.util.Date());
                String path = FileUtil.saveImage(mContext, bitmap, null, date);
                selectedDataList.add(path);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("dataList", selectedDataList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }

        }
    }


}
