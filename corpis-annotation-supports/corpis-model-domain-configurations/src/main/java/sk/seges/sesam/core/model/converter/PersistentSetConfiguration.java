package sk.seges.sesam.core.model.converter;

import org.hibernate.collection.PersistentSet;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;

import java.util.HashSet;

@TransferObjectMapping(domainClass = PersistentSet.class, dtoClass = HashSet.class)
public class PersistentSetConfiguration {}
