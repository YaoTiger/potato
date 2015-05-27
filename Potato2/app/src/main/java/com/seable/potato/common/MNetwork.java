package com.seable.potato.common;//package com.seable.potato.common;
//
//
//import com.leo.base.net.LNetwork;
//import com.leo.base.util.LSharePreference;
//
//public class MNetwork extends LNetwork {
//	
//	private LSharePreference preference ;
//	private String url;
//	
//	public MNetwork() {
//		super();
//		preference = LSharePreference.getInstance(MApplication.get().getContext());
//		url = MApplication.get().getAppServiceUrl()+"service/login/AutoLogin";
//	}
//
//	@Override
//	public LLoginState doLogin() {
////		String sPhoneNun = preference.getString(Config_Sharep.FLAG_LONGIN_PHONE);
////		if (sPhoneNun == null) {
////			return LLoginState.NONE;
////		}
////
////		Map<String, String> params = new HashMap<String, String>();
////		params.put("mobile", sPhoneNun);
////		params.put("mobile_imei", LMobileInfo.getImei());
////		params.put("equipment", LMobileInfo.MOBILE_SYSTEM);
////
////		String result = null;
////		try {
////			result = LCaller.doConn(url, params, true, LReqMothed.POST,LReqEncode.UTF8);
////		} catch (Exception e) {
////			return LLoginState.ERROR;
////		}
////       
////		LMessage lmsg = null;
////		try {
////			lmsg = Manager_JSON.parseJson(result, Entity_Login.class);
////			if (lmsg != null) {
////				Entity_Login entity = (Entity_Login) lmsg.getObj();
////				if(entity!=null){
////					if (entity.code == 400) {
////						return LLoginState.ERROR;
////					}else if(entity.code == 200){
////						if(entity.flag==0){
////							MApplication.User = entity.member;
////							MApplication.logining = true;
////							preference.setString(Config_Sharep.FLAG_USERID, entity.member.id);
////							MApplication.get().setSessionValue(entity.member.session_id);
////							return LLoginState.SUCCESS;
////						}else{
////							return LLoginState.NONE;
////						}
////						
////					}
////					
////				}else{
////					return LLoginState.NONE;
////				}
////			}
////		} catch (Exception e) {
////			return LLoginState.ERROR;
////		}
//
//		return LLoginState.ERROR;
//	}
//}
