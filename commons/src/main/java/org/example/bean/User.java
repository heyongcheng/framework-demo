package org.example.bean;

import java.util.Date;
import org.example.BeanChangeUtil.Ignore;
import org.example.BeanChangeUtil.Name;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/5/12 17:20
 */
public class User {

    private Long id;

    @Name("姓名")
    private String name;

    @Name("年龄")
    private Integer age;

    private Date createTime;

    @Ignore
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
