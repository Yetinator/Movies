package rando.yetinator.movies.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rando.yetinator.movies.model.MovieLike;

@Transactional
@Repository
public interface MovieLikeDao extends CrudRepository<MovieLike, Integer>{
	public List<MovieLike> findAll();
	public MovieLike FindByuid(int uid);
	
}
