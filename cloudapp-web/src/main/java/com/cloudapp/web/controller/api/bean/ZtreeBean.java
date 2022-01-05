package com.cloudapp.web.controller.api.bean;

/**
 * 为了适配jquery.ztree所需要的数据
 * @author SHUBEN
 *
 */
public class ZtreeBean {
	
	private Integer id;
	
    private Integer pid;
    
    private String name;
    
    private Boolean nocheck;
    
    private Boolean checked;
    
    private boolean isHidden;
    
    public ZtreeBean(Integer id, Integer pid, String name, boolean nocheck, boolean checked) {
        super();
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.nocheck = nocheck;
        this.checked = checked;
    }
    
    public Integer getId() {
		return id;
	}
    
    public void setId(Integer id) {
		this.id = id;
	}
    
    public Integer getPid() {
		return pid;
	}
    
    public void setPid(Integer pid) {
		this.pid = pid;
	}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getNocheck() {
		return nocheck;
	}
    
    public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
    
    public Boolean getChecked() {
		return checked;
	}
    
    public void setChecked(Boolean checked) {
		this.checked = checked;
	}
    
    public Boolean getIsHidden() {
		return isHidden;
	}
    
    public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
}
