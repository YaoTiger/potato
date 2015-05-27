package com.seable.potato.data.entity;


/**
 * @author 王维玉
 * @ClassName: NewsPushMessage
 * @Description: 推送通知实例
 * @date 2015-01-20 16:51
 */
public class Entity_PushMessage {

    /**
     * 推送发送时间
     */
    private String create_date;
    /**
     * 推送通知类型
     */
    private String category;
    /**
     * 推送标题
     */
    private String title;
    /**
     * 推送内容
     */
    private String content;
    /**
     * 推送来源
     */
    private String source;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
