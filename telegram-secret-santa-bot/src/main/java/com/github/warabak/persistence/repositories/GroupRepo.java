package com.github.warabak.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.github.warabak.persistence.models.GroupModel;

public interface GroupRepo extends CrudRepository<GroupModel, Long> {
	
	GroupModel findByGroupId(String groupId);

}
