package com.cloudapp.web.controller.api.bean;

/**
 * 客户地址
 * @author SHUBEN
 *
 */
public class AddressBean {
	
	private String customerCode;
	
    private Integer provinceId;
    
    private String cityId;
    
    
    public AddressBean() {
    }
    
    public String getCityId() {
		return cityId;
	}
    
    public void setCityId(String cityId) {
		this.cityId = cityId;
	}
    
    public String getCustomerCode() {
		return customerCode;
	}
    
    public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
    
    public Integer getProvinceId() {
		return provinceId;
	}
    
    public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
}
