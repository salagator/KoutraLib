package com.koutra.koutralib;

import java.util.Collection;
import java.util.Set;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;

public abstract class KoutraPropSys {
	private static boolean serverSide = false;
	
	public static void setServerSide(boolean serverSide) {
		KoutraPropSys.serverSide = serverSide;
	}
	
	public static boolean isServerSide() {
		return serverSide;
	}
	
	public abstract Set<IKPropertyDefn> getIKPropertyDefnSet();
	
	public abstract Set<IKClass> getIKClassSet();
	
	public abstract Collection<IKObject> getIKObjectSet();
	
	public abstract IKPropertyDefn getIKPropertyDefn(long id);

	public abstract IKClass getIKClass(long id);
	
	public abstract IKObject getIKObject(long id);
	
	public abstract Set<IKObject> getIKClassInstances(long classId);
	
	public abstract void createIKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	public abstract void updateIKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	public abstract void deleteIKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	public abstract void createIKClass(IKClass kClass);
	
	public abstract void updateIKClass(IKClass kClass);
	
	public abstract void deleteIKClass(IKClass kClass);
	
	public abstract void attachIKPropertyDefn(IKClass kClass, IKPropertyDefn kPropertyDefn);
	
	public abstract void detachIKPropertyDefn(IKClass kClass, IKPropertyDefn kPropertyDefn);
	
	public abstract void createIKObject(IKObject kObject);
	
	public abstract IKObject handlePendingIKObjectCreation(IKObject kObject);
	
	public abstract void handlePendingIKObjectCreationConfirmation(IKObject kObject, long pendingIKObjectId);
	
	public abstract void deleteIKObject(IKObject kObject);
	
	public abstract IKObject handlePendingIKObjectDeletion(IKObject kObject);
	
	public abstract void handlePendingIKObjectDeletionConfirmation(IKObject kObject);
	
	public abstract void createIKProperty(IKProperty kProperty);
	
	public abstract IKProperty handlePendingIKPropertyCreation(IKProperty kProperty);
	
	public abstract void handlePendingIKPropertyCreationConfirmation(IKProperty kProperty, IKProperty pendingIKProperty);
	
	public abstract void handlePendingIKPropertyCreationRejection(IKProperty kProperty, IKProperty pendingIKProperty);

	public abstract void updateIKProperty(IKProperty kProperty);
	
	public abstract IKProperty handlePendingIKPropertyUpdate(IKProperty kProperty);

	public abstract void handlePendingIKPropertyUpdateConfirmation(IKProperty kProperty, IKProperty pendingIKProperty);
	
	public abstract void handlePendingIKPropertyUpdateRejection(IKProperty kProperty, IKProperty pendingIKProperty);

	public abstract void deleteIKProperty(IKProperty kProperty);
	
	public abstract IKProperty handlePendingIKPropertyDeletion(IKProperty kProperty);

	public abstract void handlePendingIKPropertyDeletionConfirmation(IKProperty kProperty, IKProperty pendingIKProperty);
	
	public abstract void handlePendingIKPropertyDeletionRejection(IKProperty kProperty, IKProperty pendingIKProperty);

}
