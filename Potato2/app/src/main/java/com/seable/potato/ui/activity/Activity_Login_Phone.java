package com.seable.potato.ui.activity;
//package com.seable.withome.ui.activty;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.seable.australian.R;
//
//import com.leo.base.activity.LActivity;
//import com.leo.base.entity.LMessage;
//import com.leo.base.entity.LReqEntity;
//import com.leo.base.entity.LReqMothed;
//import com.leo.base.util.LFormat;
//import com.leo.base.util.LMobileInfo;
//import com.leo.base.util.LSharePreference;
//import com.leo.base.widget.T;
//import com.seable.australian.common.MApplication;
//import com.seable.australian.data.entity.Config_Sharep;
//import com.seable.australian.data.entity.Entity_Login;
//import com.seable.australian.data.handler.Handler_Login;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.EditText;
//import android.widget.TextView;
//
///**
// * 登录_手机
// * 
// * @author YuFeng
// *
// */
//public class Activity_Login_Phone extends LActivity implements OnClickListener {
//    public static final String key1 = "PHONENUMBER";
//	
//	private EditText edtPhoneNum;
//    private String sPhoneNun;
//    private LSharePreference preference;
//    public static final String KEY_TITLE = "KEY_TITLE";
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_loginphone);
//		preference = LSharePreference.getInstance(this);
//		initLayout();
//	}
//
//	private void initLayout() {
//		Intent intent = getIntent();
//		String title = intent.getStringExtra(KEY_TITLE);
//		TextView tvTitle = (TextView) findViewById(R.id.tv_title_text);
//		if(!LFormat.isEmpty(title)){
//			tvTitle.setText(title);
//		}else{
//			tvTitle.setText("登录");
//		}
//		TextView tvLeft = (TextView) findViewById(R.id.tv_title_left_img);
//		TextView tvLogin = (TextView) findViewById(R.id.login_phone_commit);
//		edtPhoneNum = (EditText)findViewById(R.id.login_phone_edit_num);
//		
//		tvLeft.setOnClickListener(this);
//		tvLogin.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_title_left_img:
//			defaultFinishActivity();
//			break;
//		case R.id.login_phone_commit:
//			if(verify()){
//				sendRequst();
//			}
//			//defaultJumpActivity(Activity_Main.class);
//			break;
//		}
//	}
//	
//	private void sendRequst(){
//		//T.ss("手机登录设置session:"+MApplication.get().getSessionValue());
//		
//		showProgressDialog("登录中...");
//		Handler_Banner mHandler  = new Handler_Banner(this);
//		LReqEntity entity = new LReqEntity();
//		entity.setUrl(MApplication.get().getAppServiceUrl()+ "service/login/MobileLogin");
//		entity.setReqMode(LReqMothed.POST);
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("mobile", sPhoneNun);
//		params.put("mobile_imei", LMobileInfo.getImei());
//		params.put("equipment", LMobileInfo.MOBILE_SYSTEM);
//		entity.setParams(params);
//		mHandler.request(entity);
//	}
//	
//	private boolean verify(){
//		sPhoneNun = edtPhoneNum.getText().toString().trim();
//		if(LFormat.isEmpty(sPhoneNun)){
//			T.ss("亲！电话号码不能为空呀");
//			return false;
//		}
//		
//		if(!LFormat.isMobile(sPhoneNun)){
//			T.ss("亲！手机号码不合法");
//			return false;
//		}
//		
//		return true;
//	}
//
//	@Override
//	public void onResultHandler(LMessage msg, int requestId) {
//		super.onResultHandler(msg, requestId);
//		if(msg!=null){
//			Entity_Login entity = (Entity_Login) msg.getObj();
//			
//			
//			if(entity!=null){
//				switch (entity.code) {
//					case 200:
//						int isVerify = entity.flag;
//						preference.setString(Config_Sharep.FLAG_LONGIN_PHONE, sPhoneNun);
//						if(isVerify==1){
//							MApplication.get().setSessionValue(entity.member.session_id);
//							defaultFinishActivity();
//							defaultJumpActivity(Activity_Login_Verify.class);
//						}else if(isVerify==0){
//							MApplication.logining =true;
//							MApplication.User  =entity.member;
//							preference.setString(Config_Sharep.FLAG_USERID, entity.member.id);
//							MApplication.get().setSessionValue(entity.member.session_id);
//							defaultJumpActivity(Activity_Main.class);
//							MApplication.msg = true;
//							//defaultFinishActivity();
//						}
//						//T.ss("手机登陆返回session:"+MApplication.get().getSessionValue());
//						break;
//					case 400:
//						T.ss("手机号码已被绑定或绑定失败");
//						break;
//					}
//			}
//			dismissProgressDialog();
//		}
//	}
//}
//    
//
