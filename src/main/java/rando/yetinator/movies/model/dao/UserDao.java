package rando.yetinator.movies.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rando.yetinator.movies.MoviesApplication;
import rando.yetinator.movies.model.User;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer>{
	
	public List<User> findAll();

}
