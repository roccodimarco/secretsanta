package com.github.warabak.persistence.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class GroupModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String groupId;
	
	protected GroupModel() {}
	
	public GroupModel(final String groupId) {
		this.groupId = groupId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(final String groupId) {
		this.groupId = groupId;
	}

	
	
}
