package com.seable.potato.data.entity;

/**
 * @author 王维玉
 * @ClassName: ProviderCompanyInfo
 * @Description: 个人中心 - 设置  - 关于我们 - 软件公司的信息
 * @date 2015年01月20日 15:49
 */
public class Entity_ProviderCompany {

    private String name;

    private String tel;

    private String url;

    private String email;

    private String content;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
