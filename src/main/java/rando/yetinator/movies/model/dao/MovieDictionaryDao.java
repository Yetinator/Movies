package rando.yetinator.movies.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rando.yetinator.movies.model.MovieDictionary;
import rando.yetinator.movies.model.User;

public interface MovieDictionaryDao extends CrudRepository<MovieDictionary, Integer>{
	
	public List<MovieDictionary> findAll();
	
	public User findByTitle(String title);

}