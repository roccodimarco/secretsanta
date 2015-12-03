package com.github.warabak.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.warabak.configuration.RepositoryConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class GroupRegistrationServiceTest {

	@Autowired
	private GroupRegistrationService groupRegistrationService;
	
	@Test
	public void testCreate() throws Exception {
		final String groupId = groupRegistrationService.create();
		Assert.assertNotNull(groupId);
	}

}
