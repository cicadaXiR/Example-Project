package com.myproject.exampleproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myproject.exampleproject.model.User;
import com.myproject.exampleproject.repository.UserRepository;
import com.myproject.exampleproject.userDto.UserDto;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User register(UserDto userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(userDto.getPassword());
		return userRepository.save(user);
	}
	public String getUser(UserDto userDto) {
		User user = new User();
		User getUser=userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword()).get();
		if(getUser==null) {
			return null;
		}
		return "Hii" + getUser.getFirstName();
	}
}
