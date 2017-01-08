package com.test.resource;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.domain.User;
import com.test.domain.Users1;

@RestController
@RequestMapping("/user")
@Scope("prototype")
public class UserResource {

	private static ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<Long, User>();

	static {
		User user1 = new User();
		user1.setId(Long.valueOf(1));
		user1.setName("name1");
		User user2 = new User();
		user2.setId(Long.valueOf(2));
		user2.setName("name2");
		User user3 = new User();
		user3.setId(Long.valueOf(3));
		user3.setName("name3");
		userMap.put(user1.getId(), user1);
		userMap.put(user2.getId(), user2);
		userMap.put(user3.getId(), user3);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userMap.get(id));
	}

	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Users1> getAllUser() {
		Users1 users = new Users1();
		Set<Entry<Long, User>> userSet = userMap.entrySet();
		Iterator<Entry<Long, User>> iterator = userSet.iterator();
		while (iterator.hasNext()) {
			users.addUser(iterator.next().getValue());
		}
		return ResponseEntity.ok(users);
	}
}
