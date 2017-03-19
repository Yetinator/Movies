package rando.yetinator.movies.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rando.yetinator.movies.model.InviteEntry;
import rando.yetinator.movies.model.InvitedGuest;

@Transactional
@Repository
public interface InvitedGuestDao extends CrudRepository<InvitedGuest, Integer>{
	public List<InvitedGuest> findAll();
	public List<InvitedGuest> findByInviteDetails(InviteEntry inviteEntry);
}
