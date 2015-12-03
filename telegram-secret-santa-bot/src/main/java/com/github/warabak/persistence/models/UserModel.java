package com.github.warabak.persistence.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.warabak.services.beans.User;

@Entity
@Table(name = "users")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Integer userId;
	private String firstName;
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name = "groupId")
	private GroupModel groupModel;
	
	protected UserModel() {}
	
	public UserModel(final Integer userId, final String firstName, final String lastName, final GroupModel groupModel) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupModel = groupModel;
	}

	public UserModel(final User user, final GroupModel groupModel) {
		this(user.userId, user.firstName, user.lastName, groupModel);
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public GroupModel getGroupModel() {
		return groupModel;
	}

	public void setGroupModel(final GroupModel groupModel) {
		this.groupModel = groupModel;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

}
