package org.ocmc.ioc.liturgical.schemas.models.ws.db;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.reinert.jjschema.Attributes;
import com.google.gson.annotations.Expose;

import org.ocmc.ioc.liturgical.schemas.id.managers.IdManager;
import org.ocmc.ioc.liturgical.schemas.models.supers.AbstractModel;
import org.ocmc.ioc.liturgical.utils.ErrorUtils;
import org.ocmc.ioc.liturgical.utils.PasswordHasher;

public class User extends AbstractModel {
	private static final Logger logger = LoggerFactory.getLogger(User.class);
	
	@Expose public String username = "";
	@Expose public String firstname = "";
	@Expose public String surname = "";
	@Expose public String email = "";
	@Expose public String hashedPassword = "";
	@Expose public boolean resetPwdNextLogin = true;
	@Expose public boolean accountActive = true;
	@Expose public int failedLoginCount = 0;
	@Expose public long lastAccess = 0;
	@Expose public int accessCount = 0;
	@Expose public String domain = "";

	@Attributes(
			required = true
			, description = "verb for HTTP request"
			, enums = {"GET", "POST", "PUT", "DELETE"}
			)
	@Expose
	String verb = "";
	
	public User() {
		super();
		this.serialVersionUID = 1.1;
	}
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	/**
	 * Use this method if you are starting from a hashed password.
	 * This is not the typical case.  Normally you will be starting with
	 * a plain text password.  if so, call setPassword instead.
	 * @param hashedPassword the hashed password
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	/**
	 * Use this method when the user provides a plain text password.
	 * It will be hashed and stored as a hash.  Subsequent
	 * authentication will use the hashed value.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		try {
			setHashedPassword(PasswordHasher.createHash(password));
		} catch (NoSuchAlgorithmException e) {
			ErrorUtils.report(logger, e);
		} catch (InvalidKeySpecException e) {
			ErrorUtils.report(logger, e);
		}
	}


	public int getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(int failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(long lastAccess) {
		this.lastAccess = lastAccess;
		this.setAccessCount(this.getAccessCount() + 1);
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public boolean isAccountActive() {
		return accountActive;
	}

	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
	}
	
	public static void main(String[] args) {
		System.out.println(new User().toUiSchema().toString());
	}

	public boolean isResetPwdNextLogin() {
		return resetPwdNextLogin;
	}

	public void setResetPwdNextLogin(boolean resetPwdNextLogin) {
		this.resetPwdNextLogin = resetPwdNextLogin;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setDomain(String language, String country, String username) {
		try {
			IdManager idManager = new IdManager(language, country, username);
			this.domain = idManager.getId();
		} catch (Exception e) {
			ErrorUtils.report(logger, e);
		}
	}
		
}
