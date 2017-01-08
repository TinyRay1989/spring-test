package com.test.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yanlei
 *<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 *<users>
 *  <user>
 *    <id>2</id>
 *    <name>name2</name>
 *  </user>
 *  <user>
 *    <id>1</id>
 *    <name>name1</name>
 *  </user>
 *  <user>
 *    <id>3</id>
 *    <name>name3</name>
 *  </user>
 *</users>
 */
@XmlRootElement(name = "users")
public class Users2 implements Serializable {
	private static final long serialVersionUID = 3601797596295438363L;
	private List<User> userList = new ArrayList<User>();

	@XmlElements(value = {@XmlElement(name = "user", type = User.class)})
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public void addUser(User user) {
		getUserList().add(user);
	}
}
