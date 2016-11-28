package rando.yetinator.movies.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rando.yetinator.movies.model.MovieLike;

@Transactional
@Repository
public interface MovieLikeDao extends CrudRepository<MovieLike, Integer>{
	public List<MovieLike> findAll();
	public MovieLike findByUid(int uid);
	public List<MovieLike> findDistinctMovieLikeByTitle(String title);
	public List<MovieLike> findAll(Sort sort);
	public List<MovieLike> findByUserUid(int user_uid);
}
