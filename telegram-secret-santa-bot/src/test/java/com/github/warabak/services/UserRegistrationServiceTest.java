package com.github.warabak.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.warabak.configuration.RepositoryConfiguration;
import com.github.warabak.services.beans.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class UserRegistrationServiceTest {

	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Autowired
	private GroupRegistrationService groupRegistrationService;
	
	@Test
	public void testCreate() throws Exception {
		final User user = new User(123, "Bill", "Clinton");
		final String groupId = groupRegistrationService.create();
		
		userRegistrationService.register(groupId, user);
	}
	
	@Test
	public void testCreateAndList() throws Exception {
		final User user = new User(123, "Bill", "Clinton");
		final String groupId = groupRegistrationService.create();
		
		userRegistrationService.register(groupId, user);
		final List<User> users = userRegistrationService.listUsers(groupId);
		
		Assert.assertEquals(1, users.size());
		Assert.assertEquals(user.userId, users.get(0).userId);
		Assert.assertEquals(user.firstName, users.get(0).firstName);
		Assert.assertEquals(user.lastName, users.get(0).lastName);
	}
	
	@Test
	public void testListNonExistentGroup() throws Exception {
		Assert.assertEquals(0, userRegistrationService.listUsers("1234abdc").size());
	}
	
	@Test
	public void testUnregister() throws Exception {
		final User user = new User(123, "Bill", "Clinton");
		final String groupId = groupRegistrationService.create();
		
		userRegistrationService.register(groupId, user);
		Assert.assertEquals(1, userRegistrationService.listUsers(groupId).size());

		userRegistrationService.unregister(groupId, user.userId);
		Assert.assertEquals(0, userRegistrationService.listUsers(groupId).size());
	}

}
