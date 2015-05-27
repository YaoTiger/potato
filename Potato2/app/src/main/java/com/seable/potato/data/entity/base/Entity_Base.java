package com.seable.potato.data.entity.base;

import java.io.Serializable;

/**
 * @author yufeng
 *         <p/>
 *         图片实体
 */

@SuppressWarnings("serial")
public class Entity_Base implements Serializable {

    /**
     * 网络请求错误时需要  错误代码
     * 1成功 0失败 -1未登录
     */
    protected int code;

    /**
     * 网络请求错误时需要  错误信息
     */
    protected String msg;

    //**网络请求正确时需要  返回数据
//	protected String datas;
    //**网络请求   是否分页
//	private boolean has_more;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//	public String getDatas() {
//		return datas;
//	}
//
//	public void setDatas(String datas) {
//		this.datas = datas;
//	}

//	public boolean isHas_more() {
//		return has_more;
//	}
//
//	public void setHas_more(boolean has_more) {
//		this.has_more = has_more;
//	}


}
