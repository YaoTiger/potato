package com.seable.potato.ui.fragment;//package com.seable.potato.ui.fragment;
//
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.leo.base.activity.fragment.LFragment;
//import com.leo.base.entity.LMessage;
//import com.leo.base.util.LFormat;
//import com.leo.base.widget.T;
//import com.seable.potato.R;
//import com.seable.potato.data.entity.base.Entity_Base;
//import com.seable.potato.ui.view.View_TitleBar;
//
///**
// * 
// * @ClassName: Fragment_Area
// * @Description: 面积测量
// * @author 王维玉
// * @date 2015-03-25 11:45
// * 
// */
//public class Fragment_Area extends LFragment {
//
//	private View view;
//	private View_TitleBar titleBar;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.fragment_area, null);
//		findViewById();
//		initView();
//		return view;
//	}
//
//	private void findViewById() {
//		titleBar = (View_TitleBar) view.findViewById(R.id.TitleBar);
//		titleBar.setBackImshow(false);
//		// 初始化view
//	}
//
//	// @Override
//	// public void setUserVisibleHint(boolean isVisibleToUser) {
//	// super.setUserVisibleHint(isVisibleToUser);
//	// if (isVisibleToUser) {
//	// // 相当于Fragment的onResume
//	// } else {
//	// // 相当于Fragment的onPause
//	// }
//	// }
//
//	private void initView() {
//		// 初始化view
//	}
//	@Override
//	public void onResultHandler(LMessage msg, int requestId) {
//		super.onResultHandler(msg, requestId);
//		if(msg!=null){
//			Entity_Base entity = (Entity_Base) msg.getObj();
//            if(entity!=null){
//            	if(entity.getCode()==1){
//            		switch (requestId) {
//            		default:
//            			break;
//            		}
//            		
//	            }else if(entity.getCode()==0){
//	            	if(!LFormat.isEmpty(entity.getMsg())){
//						T.ss(entity.getMsg());
//					}else{
//						T.ss(R.string.msg_loading_failed);
//					}
//	            }
//            	
//            }
//		}
//		dismissProgressDialog();
//	}
//
//
//}
