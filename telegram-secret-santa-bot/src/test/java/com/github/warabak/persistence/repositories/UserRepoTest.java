package com.github.warabak.persistence.repositories;

import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.warabak.configuration.RepositoryConfiguration;
import com.github.warabak.persistence.models.GroupModel;
import com.github.warabak.persistence.models.UserModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class UserRepoTest {

	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	private GroupModel group1;
	private GroupModel group2;
	
	@Before
	public void before() {
		final String groupId_1 = "1234abcd";
		final String groupId_2 = "5678efgh";

		group1 = new GroupModel(groupId_1);
		group2 = new GroupModel(groupId_2);

		groupRepo.save(group1);
		groupRepo.save(group2);
	}
	
	@After
	public void after() {
		groupRepo.delete(group1);
		groupRepo.delete(group2);
	}

	@Test
	public void testSave() throws Exception {
		final UserModel user = new UserModel(123, "George", "Washington", group1);
		Assert.assertEquals(0l, user.getId());
		userRepo.save(user);
		Assert.assertNotEquals(0l, user.getId());

		userRepo.delete(user);
	}
	
	@Test
	public void testListUsersByGroupId() throws Exception {
		final UserModel user1 = new UserModel(123, "Teddy", "Roosevelt", group1);
		final UserModel user2 = new UserModel(456, "Franklin", "Roosevelt", group1);
		final UserModel user3 = new UserModel(789, "George", "Bush", group2); // Add to a different group

		userRepo.save(user1);
		userRepo.save(user2);
		userRepo.save(user3);
		
		final Iterator<UserModel> iterator = userRepo.findAllByGroupModel(group1).iterator();

		int numberOfUsersInGroup = 0;
		while (iterator.hasNext()) {
			iterator.next();
			numberOfUsersInGroup++;
		}
		
		Assert.assertEquals(2, numberOfUsersInGroup);
		
		userRepo.delete(user1);
		userRepo.delete(user2);
		userRepo.delete(user3);
	}
	
	@Test
	public void testFindByUserId() throws Exception {
		final UserModel user = new UserModel(123, "George", "Washington", group1);
		userRepo.save(user);

		Assert.assertNotNull(userRepo.findByUserIdAndGroupModel(user.getUserId(), group1));
		userRepo.delete(user);

	}
}
