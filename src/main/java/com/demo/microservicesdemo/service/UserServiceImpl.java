package com.demo.microservicesdemo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.demo.microservicesdemo.entity.User;

@Component
public class UserServiceImpl implements UserService {

	private static List<User> users = new ArrayList<>();
	
	static int count=0;

	static {
		users.add(new User(++count, "Bajirao", LocalDate.now().of(1967, 7, 4)));
		users.add(new User(++count, "Varsha", LocalDate.now().minusYears(50)));
		users.add(new User(++count, "Aishwarya", LocalDate.now().minusYears(27)));
		users.add(new User(++count, "Prathamesh", LocalDate.now().minusYears(26)));
		users.add(new User(++count, "Abc", LocalDate.now().minusYears(26)));
	}

	@Override
	public List<User> finaAll() {
		return users;
	}

	@Override
	public User findOne(int id) {
		Predicate<? super User> Predicate = user -> id==user.getId();
		return users.stream().filter(Predicate).findFirst().orElse(null);
	}

	@Override
	public User addUser(User user) {
		user.setId(++count);
		users.add(user);
		return user;
	}

	@Override
	public void deleteUserById(int id) {
		Predicate<? super User> Predicate = user -> id==user.getId();
		users.removeIf(Predicate);
	}

}
