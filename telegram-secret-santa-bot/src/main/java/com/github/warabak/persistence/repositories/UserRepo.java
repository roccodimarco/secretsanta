package com.github.warabak.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.github.warabak.persistence.models.GroupModel;
import com.github.warabak.persistence.models.UserModel;

public interface UserRepo extends CrudRepository<UserModel, Long> {

	Iterable<UserModel> findAllByGroupModel(GroupModel group);
	
	UserModel findByUserIdAndGroupModel(Integer userId, GroupModel groupModel);
	
}
