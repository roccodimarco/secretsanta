package com.github.warabak.services;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.warabak.exceptions.DuplicateIdException;
import com.github.warabak.persistence.models.GroupModel;
import com.github.warabak.persistence.repositories.GroupRepo;

@Component
public class GroupRegistrationService {

	@Autowired
	private GroupRepo groupRepo;

	@Transactional
	public String create() throws DuplicateIdException {
		final String groupId = RandomStringUtils.randomAlphanumeric(8);

		// The rare case when a randomly generated group ID already exists.
		if (groupRepo.findByGroupId(groupId) != null) {
			throw new DuplicateIdException("Group ID already exists");
		}

		groupRepo.save(new GroupModel(groupId));

		return groupId;
	}

}
