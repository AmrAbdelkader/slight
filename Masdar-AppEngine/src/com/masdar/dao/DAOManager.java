package com.masdar.dao;

import javax.persistence.EntityManager;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.masdar.EMF;

public class DAOManager {

	public static Objectify getObjectifyService(){
		return ObjectifyService.begin();
	}
	
	public static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}
}
