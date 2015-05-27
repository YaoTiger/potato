package com.seable.potato.data.entity;

/**
 * @author 王维玉
 * @ClassName: publicDialogItem
 * @Description: 公共对话框的每一项内容
 * @date 2015-01-22 11:35
 */
public class Entity_PublicDialogItem {
    /**
     * 显示的内容
     */
    private String name;
    /**
     * 返回的数字
     */
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
