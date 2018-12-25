package com.lanmei.bilan.bean;

import java.io.Serializable;

/**
 * Created by xkai on 2018/1/9.
 * 首页 链世界  分类（或应用推荐分类或社区）
 */

public class HomeLianClassBean implements Serializable{


    /**
     * id : 3
     * classname : 入门简介
     */

    private String id;
    private String classname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
