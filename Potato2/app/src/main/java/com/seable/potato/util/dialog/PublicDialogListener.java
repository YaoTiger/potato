package com.seable.potato.util.dialog;

/**
 * @author 王维玉
 * @ClassName: PublicDialogListener
 * @Description: 公共对话框监听回调接口
 * @date 2015-01-22 11:39
 */
public interface PublicDialogListener {
    /**
     * 公共对话框回调函数
     *
     * @param position 返回用户选择的项数
     */
    public void onPublicDialogResult(int position);
}
