package sk.seges.sesam.core.model.converter;

import org.hibernate.collection.PersistentSet;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.server.domain.converter.PersistentConverter;

import java.util.Set;

@TransferObjectMapping(domainClass = PersistentSet.class, dtoClass = Set.class, converter = PersistentConverter.class)
public class PersistentSetConfiguration {}
