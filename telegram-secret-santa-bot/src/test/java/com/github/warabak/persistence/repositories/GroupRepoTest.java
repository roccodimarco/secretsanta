package com.github.warabak.persistence.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.warabak.configuration.RepositoryConfiguration;
import com.github.warabak.persistence.models.GroupModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class GroupRepoTest {

	@Autowired
	private GroupRepo groupRepo;

	@Test
	public void testSave() throws Exception {
		final String groupId = "1234abcd";
		final GroupModel group = new GroupModel(groupId);
		
		Assert.assertEquals(0l, group.getId());
		groupRepo.save(group);
		Assert.assertNotEquals(0l, group.getId());
		groupRepo.delete(group.getId());
	}
	
	@Test
	public void testFindByGroupId() throws Exception {
		final String groupId = "1234abcd";
		final GroupModel group = new GroupModel(groupId);
		
		groupRepo.save(group);
		Assert.assertNotNull(groupRepo.findByGroupId(groupId));
		groupRepo.delete(group.getId());
	}
	
	@Test
	public void testFindNonExistantGroup() throws Exception {
		final String groupId = "6789wxyz";
		Assert.assertNull(groupRepo.findByGroupId(groupId));
	}
}
