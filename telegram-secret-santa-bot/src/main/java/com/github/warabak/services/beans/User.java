package com.github.warabak.services.beans;

import com.github.warabak.persistence.models.UserModel;

public class User {
	
	public final Integer userId;
	public final String firstName;
	public final String lastName;
	
	public User(final Integer userId, final String firstName, final String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(final UserModel userModel) {
		this(userModel.getUserId(), userModel.getFirstName(), userModel.getLastName());
	}
	
}
