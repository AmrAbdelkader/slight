package com.masdar.dao;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.masdar.entities.Idea;

public class IdeaManager {

	public static List<Idea> listIdea(int limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Idea> execute = null;

		try {
			mgr = DAOManager.getEntityManager();
			Query query = mgr.createQuery("select from Idea as Idea");
			if (limit != 0) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Idea>) query.getResultList();
			
//			cursor = JPACursorHelper.getCursor(execute);
//			if (cursor != null)
//				cursorString = cursor.toWebSafeString();
			for (Idea obj : execute)
				;
		} finally {
			mgr.close();
		}

		return execute;
	}
}
