package sk.seges.sesam.core.model.converter;

import org.hibernate.collection.PersistentSet;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.server.domain.converter.PersistentSetConverter;

import java.util.Set;

@TransferObjectMapping(domainClass = PersistentSet.class, dtoClass = Set.class, converter = PersistentSetConverter.class)
public class PersistentSetConfiguration {}
