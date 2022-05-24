package com.uwplp.components.authorizationaUnits;

import com.uwplp.components.models.UserModel;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

public class Authority implements GrantedAuthority {

	private Long id;

	private String roleCode;

	private String roleDescription;

	public Authority(UserModel userModel) {
		this.id = userModel.getUserId();
		this.setRoleCode(userModel.getUserRole().toString());
		this.setRoleDescription("");
	}
	

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return roleCode;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getRoleCode() {
		return roleCode;
	}



	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}



	public String getRoleDescription() {
		return roleDescription;
	}



	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	
	
}