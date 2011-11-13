package com.koutra.koutralib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKDelta;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;
import com.koutra.koutralib.domain.impl.BooleanKProperty;
import com.koutra.koutralib.domain.impl.FloatKProperty;
import com.koutra.koutralib.domain.impl.IntegerKProperty;
import com.koutra.koutralib.domain.impl.KClass;
import com.koutra.koutralib.domain.impl.KObject;
import com.koutra.koutralib.domain.impl.KProperty;
import com.koutra.koutralib.domain.impl.KPropertyDefn;
import com.koutra.koutralib.domain.impl.StringKProperty;
import com.koutra.koutralib.domain.impl.delta.KClassCreationDelta;
import com.koutra.koutralib.domain.impl.delta.KClassDeletionDelta;
import com.koutra.koutralib.domain.impl.delta.KClassUpdateDelta;
import com.koutra.koutralib.domain.impl.delta.KObjectCreationConfirmationDelta;
import com.koutra.koutralib.domain.impl.delta.KObjectCreationDelta;
import com.koutra.koutralib.domain.impl.delta.KObjectDeletionConfirmationDelta;
import com.koutra.koutralib.domain.impl.delta.KObjectDeletionDelta;
import com.koutra.koutralib.domain.impl.delta.KObjectPendingCreationDelta;
import com.koutra.koutralib.domain.impl.delta.KObjectPendingDeletionDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyCreationDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyDefnAttachmentDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyDefnCreationDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyDefnDeletionDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyDefnDetachmentDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyDefnUpdateDelta;
import com.koutra.koutralib.domain.impl.delta.KPropertyPendingCreationDelta;

public class MemoryKoutraPropSys extends KoutraPropSys {
	private static long kPropertyDefnId = 1L;
	private static long kClassId = 1L;
	private static long kObjectId = 1L;
	private static long kPropertyId = 1L;
	private static long pendingKObjectId = 1 >> 16;
	private static long pendingKPropertyId = 1 >> 16;
	
	private synchronized static long getNextIKPropertyDefnId() {
		return kPropertyDefnId++;
	}
	
	private synchronized static long getNextIKClassId() {
		return kClassId++;
	}
	
	private synchronized static long getNextIKObjectId() {
		return kObjectId++;
	}
	
	private synchronized static long getNextIKPropertyId() {
		return kPropertyId++;
	}
	
	private synchronized static long getNextPendingIKObjectId() {
		return pendingKObjectId++;
	}
	
	private synchronized static long getNextPendingIKPropertyId() {
		return pendingKPropertyId++;
	}
	
	protected Set<IKPropertyDefn> propertyDefns = new HashSet<IKPropertyDefn>();
	protected Map<Long, IKPropertyDefn> propertyDefnMap = new HashMap<Long, IKPropertyDefn>();
	protected Set<IKClass> classes = new HashSet<IKClass>();
	protected Map<Long, IKClass> classMap = new HashMap<Long, IKClass>();
	protected Map<Long, IKObject> objectMap = new HashMap<Long, IKObject>();
	protected Map<Long, Set<IKObject>> classInstanceMap = new HashMap<Long, Set<IKObject>>();
	protected Map<Long, IKObject> pendingObjectMap = new HashMap<Long, IKObject>();
	protected Map<Long, Set<IKObject>> pendingClassInstanceMap = new HashMap<Long, Set<IKObject>>();
	protected Map<Long, Map<Long, IKProperty>> pendingPropertyMap = new HashMap<Long, Map<Long,IKProperty>>();
	
	public MemoryKoutraPropSys() {
		// Intentionally empty.
	}

	public MemoryKoutraPropSys(String domainPath)
			throws ParserConfigurationException, SAXException, IOException {
		InputStream is = getClass().getResourceAsStream(domainPath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document document = builder.parse(is);
		
		NodeList propDefnList = document.getElementsByTagName("propDefn");
		for (int i = 0; i < propDefnList.getLength(); i++) {
			Element element = (Element) propDefnList.item(i);
			IKPropertyDefn propDefn = new KPropertyDefn(
					Long.parseLong(element.getAttribute("id")),
					element.getAttribute("name"),
					Type.valueOf(element.getAttribute("type")));
			propertyDefns.add(propDefn);
			propertyDefnMap.put(propDefn.getId(), propDefn);
			
			kPropertyDefnId = propDefn.getId() + 1;
		}
		
		NodeList classList = document.getElementsByTagName("class");
		for (int i = 0; i < classList.getLength(); i++) {
			Element element = (Element) classList.item(i);
			long id = Long.parseLong(element.getAttribute("id"));
			String superClassId = element.getAttribute("superClassId");
			Set<IKPropertyDefn> propertyDefnSet = new HashSet<IKPropertyDefn>();
			
			for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				if (!(element.getChildNodes().item(j) instanceof Element)) {
					continue;
				}
				
				Element attachment = (Element) element.getChildNodes().item(j);
				if (!attachment.getTagName().equals("attach")) {
					continue;
				}
				
				long propDefnId = Long.parseLong(
						attachment.getAttribute("propDefnId"));
				propertyDefnSet.add(propertyDefnMap.get(propDefnId));
			}
			
			IKClass newClass = new KClass(
					id,
					superClassId.length() == 0 ? null : classMap.get(Long.parseLong(superClassId)),
					propertyDefnSet);
			classes.add(newClass);
			classMap.put(id, newClass);
			
			kClassId = id + 1;
		}
		
		NodeList objectList = document.getElementsByTagName("object");
		for (int i = 0; i < objectList.getLength(); i++) {
			Element element = (Element) objectList.item(i);
			long id = Long.parseLong(element.getAttribute("id"));
			long classId = Long.parseLong(element.getAttribute("classId"));
			IKObject kObject = new KObject(id, classMap.get(classId), new HashMap<Long, IKProperty>());
			
			for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				if (!(element.getChildNodes().item(j) instanceof Element)) {
					continue;
				}
				
				Element property = (Element) element.getChildNodes().item(j);
				if (!property.getTagName().equals("property")) {
					continue;
				}
				long propId = Long.parseLong(property.getAttribute("id"));
				long propDefnId = Long.parseLong(property.getAttribute("propDefnId"));
				String value = property.getAttribute("value");
				
				KProperty prop;
				IKPropertyDefn propDefn = propertyDefnMap.get(propDefnId);
				switch (propDefn.getType()) {
				case String:
					prop = new StringKProperty(propId, kObject, propDefn, value);
					break;
				case Integer:
					prop = new IntegerKProperty(propId, kObject, propDefn, Long.parseLong(value));
					break;
				case Float:
					prop = new FloatKProperty(propId, kObject, propDefn, Double.parseDouble(value));
					break;
				case Boolean:
					prop = new BooleanKProperty(propId, kObject, propDefn, Boolean.parseBoolean(value));
					break;
				default:
					throw new IllegalStateException(
							"Unexpected property definition type: " + propDefn.getType());
				}
				
				kObject.setIKProperty(propDefn, prop);
				
				kPropertyId = propId + 1;
			}
			
			objectMap.put(id, kObject);
			Set<IKObject> instanceSet = classInstanceMap.get(classId);
			if (instanceSet == null) {
				instanceSet = new HashSet<IKObject>();
				classInstanceMap.put(classId, instanceSet);
			}
			instanceSet.add(kObject);
			
			kObjectId = id + 1;
		}
	}
	
	@Override
	public Set<IKPropertyDefn> getIKPropertyDefnSet() {
		return propertyDefns;
	}
	
	@Override
	public Set<IKClass> getIKClassSet() {
		return classes;
	}
	
	@Override
	public Collection<IKObject> getIKObjectSet() {
		Collection<IKObject> returnValues = objectMap.values();
		returnValues.addAll(pendingObjectMap.values());
		return returnValues;
	}
	
	@Override
	public IKPropertyDefn getIKPropertyDefn(long id) {
		return propertyDefnMap.get(id);
	}

	@Override
	public IKClass getIKClass(long id) {
		return classMap.get(id);
	}
	
	@Override
	public IKObject getIKObject(long id) {
		return objectMap.get(id);
	}
	
	@Override
	public Set<IKObject> getIKClassInstances(long classId) {
		return classInstanceMap.get(classId);
	}

	@Override
	public void createIKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (isServerSide()) {
			kPropertyDefn.setId(getNextIKPropertyDefnId());
		}
		
		if (propertyDefnMap.containsKey(kPropertyDefn.getId())) {
			throw new IllegalStateException("Trying to add a property defn that " +
					"already exists in the system (id: " + kPropertyDefn.getId() + ").");
		}
		
		propertyDefnMap.put(kPropertyDefn.getId(), kPropertyDefn);
		propertyDefns.add(kPropertyDefn);
		
		if (isServerSide()) {
			// Generate a delta towards the clients.
			IKDelta delta = new KPropertyDefnCreationDelta(kPropertyDefn);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void updateIKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (!propertyDefnMap.containsKey(kPropertyDefn.getId())) {
			throw new IllegalStateException("Trying to update a property defn " +
					"that does not exist (id: " + kPropertyDefn.getId() + ").");
		}
		
		IKPropertyDefn oldKPropertyDefn = propertyDefnMap.remove(kPropertyDefn.getId());
		propertyDefns.remove(oldKPropertyDefn);
		propertyDefnMap.put(kPropertyDefn.getId(), kPropertyDefn);
		propertyDefns.add(kPropertyDefn);
		
		if (isServerSide()) {
			IKDelta delta = new KPropertyDefnUpdateDelta(kPropertyDefn);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void deleteIKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (!propertyDefnMap.containsKey(kPropertyDefn.getId())) {
			throw new IllegalStateException("Trying to delete a property defn " +
					"that does not exist (id: " + kPropertyDefn.getId() + ").");
		}
		
		IKPropertyDefn oldKPropertyDefn = propertyDefnMap.remove(kPropertyDefn.getId());
		propertyDefns.remove(oldKPropertyDefn);
		
		if (isServerSide()) {
			IKDelta delta = new KPropertyDefnDeletionDelta(kPropertyDefn);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void createIKClass(IKClass kClass) {
		if (isServerSide()) {
			kClass.setId(getNextIKClassId());
		}
		
		if (propertyDefnMap.containsKey(kClass.getId())) {
			throw new IllegalStateException("Trying to add a class that " +
					"already exists in the system (id: " + kClass.getId() + ").");
		}
		
		classMap.put(kClass.getId(), kClass);
		classes.add(kClass);
		
		if (isServerSide()) {
			// Generate a delta towards the clients.
			IKDelta delta = new KClassCreationDelta(kClass);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void updateIKClass(IKClass kClass) {
		if (!classMap.containsKey(kClass.getId())) {
			throw new IllegalStateException("Trying to update a class " +
					"that does not exist (id: " + kClass.getId() + ").");
		}
		
		IKClass oldKClass = classMap.remove(kClass.getId());
		classes.remove(oldKClass);
		classMap.put(kClass.getId(), kClass);
		classes.add(kClass);
		
		if (isServerSide()) {
			IKDelta delta = new KClassUpdateDelta(kClass);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void deleteIKClass(IKClass kClass) {
		if (!classMap.containsKey(kClass.getId())) {
			throw new IllegalStateException("Trying to delete a class " +
					"that does not exist (id: " + kClass.getId() + ").");
		}
		
		IKClass oldKClass = classMap.remove(kClass.getId());
		classes.remove(oldKClass);
		
		if (isServerSide()) {
			IKDelta delta = new KClassDeletionDelta(kClass);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void attachIKPropertyDefn(IKClass kClass,
			IKPropertyDefn kPropertyDefn) {
		if (kClass.containsIKPropertyDefn(kPropertyDefn)) {
			throw new IllegalStateException("Trying to attach a property defn " +
					"that is already attached (classId: " + kClass.getId() +
					", propDefnId: " + kPropertyDefn.getId() + ").");
		}
		
		kClass.attachIKPropertyDefn(kPropertyDefn);
		
		if (isServerSide()) {
			IKDelta delta = new KPropertyDefnAttachmentDelta(kClass, kPropertyDefn);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void detachIKPropertyDefn(IKClass kClass,
			IKPropertyDefn kPropertyDefn) {
		if (!kClass.containsIKPropertyDefn(kPropertyDefn)) {
			throw new IllegalStateException("Trying to detach a property defn " +
					"that is not attached (classId: " + kClass.getId() +
					", propDefnId: " + kPropertyDefn.getId() + ").");
		}
		
		kClass.detachIKPropertyDefn(kPropertyDefn);
		
		if (isServerSide()) {
			IKDelta delta = new KPropertyDefnDetachmentDelta(kClass, kPropertyDefn);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public void createIKObject(IKObject kObject) {
		if (isServerSide()) {
			kObject.setId(getNextIKObjectId());
			
			objectMap.put(kObject.getId(), kObject);
			Set<IKObject> instanceSet = classInstanceMap.get(kObject.getIKClass().getId());
			if (instanceSet == null) {
				instanceSet = new HashSet<IKObject>();
				classInstanceMap.put(kObject.getIKClass().getId(), instanceSet);
			}
			instanceSet.add(kObject);
			
			IKDelta delta = new KObjectCreationDelta(kObject);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		} else {
			kObject.setId(getNextPendingIKObjectId());
			
			pendingObjectMap.put(kObject.getId(), kObject);
			Set<IKObject> instanceSet = pendingClassInstanceMap.get(kObject.getIKClass().getId());
			if (instanceSet == null) {
				instanceSet = new HashSet<IKObject>();
				pendingClassInstanceMap.put(kObject.getIKClass().getId(), instanceSet);
			}
			instanceSet.add(kObject);
			
			IKDelta delta = new KObjectPendingCreationDelta(kObject);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public IKObject handlePendingIKObjectCreation(IKObject kObject) {
		if (!isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectCreation() on the client side.");
		}
		
		long pendingIKObjectId = kObject.getId();
		kObject.setId(getNextIKObjectId());
		
		objectMap.put(kObject.getId(), kObject);
		Set<IKObject> instanceSet = classInstanceMap.get(kObject.getIKClass().getId());
		if (instanceSet == null) {
			instanceSet = new HashSet<IKObject>();
			classInstanceMap.put(kObject.getIKClass().getId(), instanceSet);
		}
		instanceSet.add(kObject);
		
		IKDelta delta = new KObjectCreationConfirmationDelta(kObject, pendingIKObjectId);
		DeltaSysFactory.getDeltaSys().addDelta(delta);
		
		return kObject;
	}

	@Override
	public void handlePendingIKObjectCreationConfirmation(IKObject kObject,
			long pendingIKObjectId) {
		if (isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectCreationConfirmation() on the server side.");
		}
		
		IKObject oldObject = pendingObjectMap.remove(pendingIKObjectId);
		pendingClassInstanceMap.get(kObject.getIKClass().getId()).remove(oldObject);
		
		objectMap.put(kObject.getId(), kObject);
		Set<IKObject> instanceSet = classInstanceMap.get(kObject.getIKClass().getId());
		if (instanceSet == null) {
			instanceSet = new HashSet<IKObject>();
			classInstanceMap.put(kObject.getIKClass().getId(), instanceSet);
		}
		instanceSet.add(kObject);
	}

	@Override
	public void deleteIKObject(IKObject kObject) {
		if (isServerSide()) {
			if (!objectMap.containsKey(kObject.getId())) {
				throw new IllegalStateException("Trying to delete an object " +
						"that does not exist (id: " + kObject.getId() + ").");
			}
			
			IKObject oldObject = objectMap.remove(kObject.getId());
			classInstanceMap.get(oldObject.getIKClass().getId()).remove(oldObject);
			
			IKDelta delta = new KObjectDeletionDelta(kObject);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		} else {
			// TODO consider... is it OK not to modify any state in this case?
			IKDelta delta = new KObjectPendingDeletionDelta(kObject);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public IKObject handlePendingIKObjectDeletion(IKObject kObject) {
		if (!isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectDeletion() on the client side.");
		}
		
		IKObject oldObject = objectMap.remove(kObject.getId());
		classInstanceMap.get(oldObject.getIKClass().getId()).remove(oldObject);
		
		IKDelta delta = new KObjectDeletionConfirmationDelta(kObject);
		DeltaSysFactory.getDeltaSys().addDelta(delta);
		
		return null;
	}

	@Override
	public void handlePendingIKObjectDeletionConfirmation(IKObject kObject) {
		if (isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectDeletionConfirmation() on the server side.");
		}
		
		IKObject oldObject = objectMap.remove(kObject.getId());
		classInstanceMap.get(oldObject.getIKClass().getId()).remove(oldObject);
	}

	@Override
	public void createIKProperty(IKProperty kProperty) {
		if (isServerSide()) {
			kProperty.setId(getNextIKPropertyId());
			
			if (!kProperty.getIKObject().hasIKProperty(
					kProperty.getIKPropertyDefn())) {
				throw new IllegalStateException("Trying to instantiate an " +
						"unattached property (kObjectId: " +
						kProperty.getIKObject().getId() +
						", kPropertyDefnId: " +
						kProperty.getIKPropertyDefn().getId() + ").");
			}
			IKProperty existingProperty = kProperty.getIKObject().getIKProperty(
					kProperty.getIKPropertyDefn());
			if (existingProperty != null) {
				throw new IllegalStateException("Trying to instantiate an " +
						"instantiated property (kObjectId: " +
						kProperty.getIKObject().getId() +
						", kPropertyDefnId: " +
						kProperty.getIKPropertyDefn().getId() + ").");
			}
			
			kProperty.getIKObject().setIKProperty(
					kProperty.getIKPropertyDefn(), kProperty);
			
			IKDelta delta = new KPropertyCreationDelta(kProperty);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		} else {
			kProperty.setId(getNextPendingIKPropertyId());
			
			if (!kProperty.getIKObject().hasIKProperty(
					kProperty.getIKPropertyDefn())) {
				throw new IllegalStateException("Trying to instantiate an " +
						"unattached property (kObjectId: " +
						kProperty.getIKObject().getId() +
						", kPropertyDefnId: " +
						kProperty.getIKPropertyDefn().getId() + ").");
			}
			IKProperty existingProperty = kProperty.getIKObject().getIKProperty(
					kProperty.getIKPropertyDefn());
			if (existingProperty != null) {
				throw new IllegalStateException("Trying to instantiate an " +
						"instantiated property (kObjectId: " +
						kProperty.getIKObject().getId() +
						", kPropertyDefnId: " +
						kProperty.getIKPropertyDefn().getId() + ").");
			}

			Map<Long, IKProperty> propertyMap = pendingPropertyMap.get(
					kProperty.getIKObject().getId());
			if (propertyMap == null) {
				propertyMap = new HashMap<Long, IKProperty>();
				pendingPropertyMap.put(kProperty.getIKObject().getId(), propertyMap);
			}
			propertyMap.put(kProperty.getIKPropertyDefn().getId(), kProperty);
			
			IKDelta delta = new KPropertyPendingCreationDelta(kProperty);
			DeltaSysFactory.getDeltaSys().addDelta(delta);
		}
	}

	@Override
	public IKProperty handlePendingIKPropertyCreation(IKProperty kProperty) {
		if (!isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectCreation() on the client side.");
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handlePendingIKPropertyCreationConfirmation(
			IKProperty kProperty, IKProperty pendingIKProperty) {
		if (isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKPropertyCreationConfirmation() on the server side.");
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePendingIKPropertyCreationRejection(IKProperty kProperty,
			IKProperty pendingIKProperty) {
		if (isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKPropertyCreationRejection() on the server side.");
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIKProperty(IKProperty kProperty) {
		// TODO Auto-generated method stub

	}

	@Override
	public IKProperty handlePendingIKPropertyUpdate(IKProperty kProperty) {
		if (!isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectCreation() on the client side.");
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handlePendingIKPropertyUpdateConfirmation(IKProperty kProperty,
			IKProperty pendingIKProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePendingIKPropertyUpdateRejection(IKProperty kProperty,
			IKProperty pendingIKProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteIKProperty(IKProperty kProperty) {
		// TODO Auto-generated method stub

	}

	@Override
	public IKProperty handlePendingIKPropertyDeletion(IKProperty kProperty) {
		if (!isServerSide()) {
			throw new IllegalStateException("Trying to call " +
					"handlePendingIKObjectCreation() on the client side.");
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handlePendingIKPropertyDeletionConfirmation(
			IKProperty kProperty, IKProperty pendingIKProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePendingIKPropertyDeletionRejection(IKProperty kProperty,
			IKProperty pendingIKProperty) {
		// TODO Auto-generated method stub
		
	}

}
