package rando.yetinator.movies.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rando.yetinator.movies.model.InviteEntry;
import rando.yetinator.movies.model.User;

@Transactional
@Repository
public interface InviteEntryDao extends CrudRepository<InviteEntry, Integer> {
	public List<InviteEntry> findAll();
	public List<InviteEntry> findByInvitorId(int invitorId);
	public InviteEntry findByUid(int uid);
	public List<InviteEntry> findByInvited(User invited);
	
}
