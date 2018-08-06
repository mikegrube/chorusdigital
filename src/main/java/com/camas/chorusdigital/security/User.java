package com.camas.chorusdigital.security;

import com.camas.chorusdigital.contributor.Contributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
	private static final Logger log = LoggerFactory.getLogger(User.class);

	@Id
	@GeneratedValue
	private Long id;

	private String userName;

	@Transient
	private String password;

	private String encryptedPassword;
	private boolean enabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
			     inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();
	private Integer failedLoginAttempts = 0;

	//For Volunteers
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private boolean termsAgreed;

	@ManyToOne
	private Contributor member = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role){
		if(!this.roles.contains(role)){
			this.roles.add(role);
		}

		if(!role.getUsers().contains(this)){
			role.getUsers().add(this);
		}
	}

	public void removeRole(Role role){
		this.roles.remove(role);
		role.getUsers().remove(this);
	}

	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(Integer failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return lastName + ", " + firstName;
	}

	public boolean isAMember() {
		return member != null;
	}

	public Contributor getMember() {
		return member;
	}

	public void setMember(Contributor member) {
		this.member = member;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isTermsAgreed() {
		return termsAgreed;
	}

	public void setTermsAgreed(boolean termsAgreed) {
		this.termsAgreed = termsAgreed;
	}

	public boolean hasRole(String roleName) {
		for (Role role : roles) {
			if (role.getRole().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

}
