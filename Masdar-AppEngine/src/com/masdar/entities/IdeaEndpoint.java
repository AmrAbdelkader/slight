package com.masdar.entities;

import com.masdar.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "ideaendpoint", namespace = @ApiNamespace(ownerDomain = "masdar.com", ownerName = "masdar.com", packagePath = "entities"))
public class IdeaEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listIdea")
	public CollectionResponse<Idea> listIdea(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Idea> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Idea as Idea");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Idea>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Idea obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Idea> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getIdea")
	public Idea getIdea(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Idea idea = null;
		try {
			idea = mgr.find(Idea.class, id);
		} finally {
			mgr.close();
		}
		return idea;
	}
	
	@ApiMethod(name = "voteUp")
	public TransactionResponse voteUp(@Named("ideaId") Long ideaId , @Named("userId") Long userId) {
		EntityManager mgr = getEntityManager();
		Idea idea = null;
		TransactionResponse tranRes = new TransactionResponse(false);
		try {
			idea = mgr.find(Idea.class, ideaId);
			idea.setVoting_number(idea.getVoting_number() + 1);
			mgr.persist(idea);
			VotingUser votingUser = new VotingUser();
			votingUser.setVote_idea_id(ideaId);
			votingUser.setVote_type((byte)1);
			votingUser.setVote_user_id(userId);
			new VotingUserEndpoint().insertVotingUser(votingUser);
			tranRes.isValid = true;
		}
		catch(Exception exc){
			tranRes.isValid = false;
		}
		finally {
			mgr.close();
		}
		return tranRes;
	}
	
	@ApiMethod(name = "voteDown")
	public TransactionResponse voteDown(@Named("ideaId") Long ideaId , @Named("userId") Long userId) {
		EntityManager mgr = getEntityManager();
		Idea idea = null;
		TransactionResponse tranRes = new TransactionResponse(false);
		try {
			idea = mgr.find(Idea.class, ideaId);
			idea.setVoting_number(idea.getVoting_number() - 1);
			mgr.persist(idea);
			VotingUser votingUser = new VotingUser();
			votingUser.setVote_idea_id(ideaId);
			votingUser.setVote_type((byte)0);
			votingUser.setVote_user_id(userId);
			new VotingUserEndpoint().insertVotingUser(votingUser);
			tranRes.isValid = true;
		}catch(Exception exc){
			tranRes.isValid = false;
		}
		finally {
			mgr.close();
		}
		return tranRes;
	} 

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param idea the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertIdea")
	public Idea insertIdea(Idea idea) {
		EntityManager mgr = getEntityManager();
		try {
			//if (containsIdea(idea)) {
				//throw new EntityExistsException("Object already exists");
			//}
			mgr.persist(idea);
		} finally {
			mgr.close();
		}
		return idea;
	}
	
	@ApiMethod(name = "insertSocialIdea")
	public Idea insertSocialIdea(Idea idea , @Named("usersIds") String usersIds) {
		EntityManager mgr = getEntityManager();
		EntityManager mgr2 = getEntityManager();
		Long creatorUserId;
		Long ideaId;
		mgr.getTransaction().begin();
		//mgr2.getTransaction().begin();
		try {
			creatorUserId = idea.getUser_id();
			mgr.persist(idea);
			mgr.flush();
			ideaId = idea.getIdea_id();
			
			SocialIdeaUser creatorSocialUser = new SocialIdeaUser();
			creatorSocialUser.setIdea_id(ideaId);
			creatorSocialUser.setUser_id(creatorUserId);
			creatorSocialUser.setRole_id(0);  //creator
			creatorSocialUser.setIsLocked(0);
			mgr2.persist(creatorSocialUser);
			
			for (String userId : usersIds.split(",")) {
				EntityManager mgr3 = getEntityManager();
				SocialIdeaUser socialUser = new SocialIdeaUser();
				socialUser.setIdea_id(ideaId);
				socialUser.setUser_id(Long.valueOf(userId));
				socialUser.setRole_id(1); //contributor
				socialUser.setIsLocked(0);
				mgr3.persist(socialUser);
				mgr3.close();
			}
			mgr.getTransaction().commit();
			//mgr2.getTransaction().commit();
		} catch(Exception exc){
			exc.printStackTrace();
			mgr.getTransaction().rollback();
			//mgr2.getTransaction().rollback();
		}finally {
			mgr.close();
			mgr2.close();
		}
		return idea;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param idea the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateIdea")
	public Idea updateIdea(Idea idea) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsIdea(idea)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(idea);
		} finally {
			mgr.close();
		}
		return idea;
	}

	
	@ApiMethod(name = "updateSocialIdea" , path = "udpateSocialIdea")
	public Idea updateSocialIdea(Idea idea , @Named("socialIdeaUserId") Long socialUserId) {
		EntityManager mgr = getEntityManager();
		EntityManager socialMgr = getEntityManager();
		try {
			if (!containsIdea(idea)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(idea);
			
			//unloack current idea
			SocialIdeaUser socialideauser = socialMgr.find(SocialIdeaUser.class, socialUserId);
			if(socialideauser != null){
				socialideauser.setIsLocked(0);
				socialMgr.persist(socialideauser);
			}
			
		} finally {
			mgr.close();
			socialMgr.close();
		}
		return idea;
	}
	
	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeIdea")
	public void removeIdea(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Idea idea = mgr.find(Idea.class, id);
			mgr.remove(idea);
		} finally {
			mgr.close();
		}
	}

	private boolean containsIdea(Idea idea) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Idea item = mgr.find(Idea.class, idea.getIdea_id());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
