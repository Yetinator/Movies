package rando.yetinator.movies.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;

@Transactional
@Repository
public interface UserFriendsListDao extends CrudRepository<UserFriendsList, Integer>{
	
	public List<UserFriendsList> findAll();
	//find by user one is users that user one friended but not people who friended user one back
	public List<UserFriendsList> findByUserOne(int UserOne);
	public List<UserFriendsList> findByUserOne(User UserOne);
	
}
