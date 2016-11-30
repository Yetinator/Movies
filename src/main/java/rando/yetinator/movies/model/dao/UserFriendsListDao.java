package rando.yetinator.movies.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rando.yetinator.movies.model.UserFriendsList;

@Transactional
@Repository
public interface UserFriendsListDao extends CrudRepository<UserFriendsList, Integer>{
	
	public List<UserFriendsList> findAll();
	//public List<UserFriendsList> findByUserOne();
}
