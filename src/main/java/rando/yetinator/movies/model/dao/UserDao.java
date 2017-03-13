package rando.yetinator.movies.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import rando.yetinator.movies.model.User;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer>{
	
	public List<User> findAll();
	
	User findByUserName(String UserName);
	
	User findByuid(int uid);
	
	//User findByTmbdid(int tmbdid);

}
