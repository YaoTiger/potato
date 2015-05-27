package com.seable.potato.common;//package com.seable.potato.common;
//
//import org.json.JSONException;
//
//import com.leo.base.activity.LActivity;
//import com.leo.base.activity.fragment.LFragment;
//import com.leo.base.adapter.LBaseAdapter;
//import com.leo.base.entity.LMessage;
//import com.leo.base.exception.LLoginException;
//import com.leo.base.handler.LHandler;
//import com.leo.base.net.ILNetwork.LReqResultState;
//import com.leo.base.service.LService;
//
//public abstract class MHandler extends LHandler {
//    
//	
//	protected LActivity activity;
//	protected LFragment  fragment;
//
//	
//	
//	public void dismissProgress(){
//		activity.dismissProgressDialog();
//	}
//	
//	
//	public MHandler(LActivity activity) {
//		super(activity);
//		this.activity = activity;
//		init();
//	}
//
//	public MHandler(LFragment fragment) {
//		super(fragment);
//		this.fragment = fragment;
//		init();
//	}
//
//	public <T> MHandler(LBaseAdapter<T> baseAdapter) {
//		super(baseAdapter);
//		init();
//	}
//
//	public MHandler(LService service) {
//		super(service);
//		init();
//	}
//    
//	
//	
//	
//	/**
//	 * 初始化网络请求监听，非常重要
//	 */
//	private void init() {
//		initNetwork(new MNetwork());
//	}
//
//	@Override
//	public void onException(LReqResultState state, int requestId) {
//		if(activity != null){
//			activity.dismissProgressDialog();
//		}else if (fragment != null) {
//			((LActivity) fragment.getActivity()).dismissProgressDialog();
//			 fragment.noMoreData=true;
//			 fragment.isRefreshing=false;
//		}
//			
//		switch (state) {
//		case NETWORK_EXC:
//			onNetWorkExc(requestId);
//			break;
//		case PARSE_EXC:
//			onParseExc(requestId);
//			break;
//		case LOGIN_ERROR:
//			onLoginError(requestId);
//			break;
//		case LOGIN_NONE:
//			onLoginNone(requestId);
//			break;
//		case STOP:
//			onStop(requestId);
//			break;
//		default:
//			onOtherExc(requestId);
//			break;
//		}
//	}
//
//	@Override
//	public abstract LMessage onParse(String strs, int requestId)
//			throws LLoginException, JSONException, Exception;
//
//	@Override
//	public void onDownloadProgress(int size, int current, int requestId) {
//		// ... 如果在下载文件时需要进度控制，请重写此方法
//	}
//
//	@Override
//	public void onUploadProgress(int curFileId, long curFileLength,
//			long curFileAllLength, int allFileCount, long allFileCurLength,
//			long allFileLength, int requestId) {
//		// ... 如果在下载文件时需要进度控制，请重写此方法
//	}
//
//	/**
//	 * 网络请求异常
//	 */
//	protected abstract void onNetWorkExc(int requestId);
//
//	/**
//	 * 数据解析失败
//	 */
//	protected abstract void onParseExc(int requestId);
//
//	/**
//	 * 自动登录错误
//	 */
//	protected abstract void onLoginError(int requestId);
//
//	/**
//	 * 未登录用户
//	 */
//	protected abstract void onLoginNone(int requestId);
//
//	/**
//	 * 线程停止
//	 */
//	protected abstract void onStop(int requestId);
//
//	/**
//	 * 其它异常
//	 */
//	protected abstract void onOtherExc(int requestId);
//	
//
//}