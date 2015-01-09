package br.com.tiago.drone.dao;

import java.util.List;

import br.com.tiago.drone.model.User;

public interface UserDAO {
	User findById(Long id) throws Exception;

	User findByUserName(String userName) throws Exception;

	void save(User user) throws Exception;
	
	List<User> findAll() throws Exception;
}
