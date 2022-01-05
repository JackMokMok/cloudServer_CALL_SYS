package com.cloudapp.core.entity;

import com.cloudapp.core.constants.OffDayConstant;
import com.cloudapp.core.constants.SortingRulesConstant;
import com.cloudapp.core.entity.base.Entity;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class Customer implements Entity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 场所名称
     */
    private String name;

    /**
     * 注册码
     */
    private String code;

    /**
     * 客户组id
     */
    private Integer cusGroupId;

    /**
     * 授权点
     */
    private Integer authCount;

    /**
     * 授权到期日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date authDate;

    /**
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hTime;

    /**
     * 授权到期剩余天数
     */
    private Integer diffDay;

    /**
     * 没有心跳的天数
     */
    private Integer noneHeartDay;

    /**
     * 排序规则（默认按授权日期进行排序）
     */
    private Integer sortingRules;

    /**
     * 离线天数（查询规则）
     */
    private Integer offDay;

    /**
     * 批量授权时用到的
     */
    private Integer[] cusIds;

    private boolean check;

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAuthCount() {
        return authCount;
    }

    public void setAuthCount(Integer authCount) {
        this.authCount = authCount;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date gethTime() {
        return hTime;
    }

    public void sethTime(Date hTime) {
        this.hTime = hTime;
    }

    public Integer getCusGroupId() {
        return cusGroupId;
    }

    public void setCusGroupId(Integer cusGroupId) {
        this.cusGroupId = cusGroupId;
    }

    public Integer getDiffDay() {
        return diffDay;
    }

    public void setDiffDay(Integer diffDay) {
        this.diffDay = diffDay;
    }

    public Integer getNoneHeartDay() {
        return noneHeartDay;
    }

    public void setNoneHeartDay(Integer noneHeartDay) {
        this.noneHeartDay = noneHeartDay;
    }

    public Integer getSortingRules() {
        return sortingRules;
    }

    public void setSortingRules(Integer sortingRules) {
        this.sortingRules = sortingRules;
    }

    public Integer getOffDay() {
        return offDay;
    }

    public void setOffDay(Integer offDay) {
        this.offDay = offDay;
    }

    public Integer[] getCusIds() {
        return cusIds;
    }

    public void setCusIds(Integer[] cusIds) {
        this.cusIds = cusIds;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSortingRulesName() {
        String name = SortingRulesConstant.getName(this.sortingRules);
        if (name == null)
            return "其它";
        return name;
    }

    /**
     * 查询的离线天数规则
     * @return
     */
    public String getOffDayName() {
        String name = OffDayConstant.getName(this.offDay);
        if (name == null)
            return "其它";
        return name;
    }
}
