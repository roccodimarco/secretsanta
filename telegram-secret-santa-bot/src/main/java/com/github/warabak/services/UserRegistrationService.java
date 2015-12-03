package com.github.warabak.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.warabak.exceptions.GroupNotFoundException;
import com.github.warabak.persistence.models.GroupModel;
import com.github.warabak.persistence.models.UserModel;
import com.github.warabak.persistence.repositories.GroupRepo;
import com.github.warabak.persistence.repositories.UserRepo;
import com.github.warabak.services.beans.User;

@Component
public class UserRegistrationService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private GroupRepo groupRepo;

	@Transactional
	public void register(final String groupId, final User user) throws GroupNotFoundException {
		final GroupModel groupModel = groupRepo.findByGroupId(groupId);
		
		if(groupModel == null) { throw new GroupNotFoundException("Group does not exist"); }
		
		userRepo.save(new UserModel(user, groupModel));
	}

	@Transactional
	public List<User> listUsers(final String groupId) throws GroupNotFoundException {
		final List<User> users = new ArrayList<>();
		
		final GroupModel groupModel = groupRepo.findByGroupId(groupId);
		
		if(groupModel == null) { throw new GroupNotFoundException("Group does not exist"); }

		final Iterator<UserModel> iterator = userRepo.findAllByGroupModel(groupModel).iterator();
		
		while(iterator.hasNext()) {
			users.add(new User(iterator.next()));
		}
		
		return users;
	}

	@Transactional
	public void unregister(final String groupId, final Integer userId) throws GroupNotFoundException {
		
		final GroupModel groupModel = groupRepo.findByGroupId(groupId);
		
		if(groupModel == null) { throw new GroupNotFoundException("Group does not exist"); }

		final UserModel userModel = userRepo.findByUserIdAndGroupModel(userId, groupModel);
		
		userRepo.delete(userModel);
	}

}
