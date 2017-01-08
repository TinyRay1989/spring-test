package com.test.resource;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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

	/*
	 * ***********************************GET***************************************
	 */
	
	/**  
	 * @Description: 
	 * <p>URL:http://localhost:8080/user/1
	 * <p>headers：Accept:application/json or Accept:application/xml
	 * @author yanlei
	 * @date 2017年1月9日 上午2:51:51
	 * @version V1.0
	 * @param id
	 * @return {@link ResponseEntity}<{@link User}>
	 */ 
	@RequestMapping(
			value = "/{id}", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }
			)
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userMap.get(id));
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }
			)
	public ResponseEntity<Users1> getAllUser() {
		Users1 users = new Users1();
		Set<Entry<Long, User>> userSet = userMap.entrySet();
		Iterator<Entry<Long, User>> iterator = userSet.iterator();
		while (iterator.hasNext()) {
			users.addUser(iterator.next().getValue());
		}
		return ResponseEntity.ok(users);
	}

	/*
	 * ***********************************POST***************************************
	 */
	/**  
	 * @Description: 
	 * <p>URL:http://localhost:8080/user
	 * <p>headers：Content-Type: application/json
	 * <p>requestBody：<pre> { "id": 6, "name": "name6"}</pre>
	 * @author yanlei
	 * @date 2017年1月9日 上午3:35:52
	 * @version V1.0
	 * @param user
	 * @param ucBuilder
	 * @return {@link ResponseEntity}<{@link Void}>
	 */ 
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }
			)
	public ResponseEntity<Void> createUser(@RequestBody User user,
			UriComponentsBuilder ucBuilder) {
		Long id = user.getId();
		if (userMap.containsKey(id)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		createUser(user);
		return ResponseEntity.created(ucBuilder
				.path("/user/{id}").buildAndExpand(id).toUri()).build();
	}
	
	/**  
	 * @Description: 
	 * <p>URL:http://localhost:8080/user?id=7&name=name7
	 * <p>headers：Content-Type: application/x-www-form-urlencoded
	 * <p>or
	 * <p>URL:http://localhost:8080/user
	 * <p>headers：Content-Type: application/x-www-form-urlencoded
	 * <p>requestBody：<pre> id=7&name=name7</pre>
	 * @author yanlei
	 * @date 2017年1月9日 上午4:21:44
	 * @version V1.0
	 * @param id
	 * @param name
	 * @param ucBuilder
	 * @return {@link ResponseEntity}<{@link Void}>
	 */ 
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_FORM_URLENCODED }
			)
	public ResponseEntity<Void> createUserUseFormParam(
			@FormParam(value = "id") Long id,
			@FormParam(value = "name") String name,
			UriComponentsBuilder ucBuilder) {
		if (userMap.containsKey(id)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		User user = new User(id, name);
		createUser(user);
		return ResponseEntity.created(ucBuilder
				.path("/user/{id}").buildAndExpand(id).toUri()).build();
	}
	
	
	/**  
	 * @Description: 
	 * <p>URL:http://localhost:8080/user/list
	 * <p>headers：Content-Type: application/json
	 * <p>requestBody：<pre> [{ "id": 6, "name": "name6"},{ "id": 7, "name": "name7"}]</pre>
	 * @author yanlei
	 * @date 2017年1月9日 上午4:38:07
	 * @version V1.0
	 * @param userList
	 * @return {@link ResponseEntity}<{@link Void}>
	 */ 
	@RequestMapping(
			value = "/list",
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }
			)
	public ResponseEntity<Void> createUsers(@RequestBody List<User> userList) {
		Iterator<User> validateIterator = userList.iterator();
		while(validateIterator.hasNext()){
			User theUser = validateIterator.next();
			if (userMap.containsKey(theUser.getId())) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
		}
		createUserList(userList);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	/*
	 * ***********************************PUT***************************************
	 */
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.PUT,
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML },
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }
			)
	public ResponseEntity updateUser(
			@PathVariable Long id,
			@RequestBody User user,
			UriComponentsBuilder ucBuilder) {
		if(!id.equals(user.getId())){
			return ResponseEntity.badRequest().build();
		}
		if (!userMap.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		User dbUser = userMap.get(id);
		if (!dbUser.getIsValid()) {
			return new ResponseEntity(HttpStatus.GONE);
		}
		updateDbUser(user);
		return ResponseEntity.ok(user);
	}
	
	/*
	 * ***********************************DELETE***************************************
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		if (!userMap.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		userMap.remove(id);
		return ResponseEntity.noContent().build();
	}
	
	private void createUser(User user) {
		userMap.put(user.getId(), user);
	}

	private void createUserList(List<User> userList) {
		Iterator<User> iterator = userList.iterator();
		while(iterator.hasNext()){
			User theUser = iterator.next();
			createUser(theUser);
		}
	}

	private void updateDbUser(User user) {
		userMap.put(user.getId(), user);
	}
	
}
