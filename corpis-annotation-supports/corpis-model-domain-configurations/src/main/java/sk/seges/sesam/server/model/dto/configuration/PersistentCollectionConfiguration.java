package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.server.domain.converter.PersistentCollectionConverter;

import java.util.Collection;

@TransferObjectMapping(domainInterface = Collection.class, dtoInterface = Collection.class, converter = PersistentCollectionConverter.class)
public interface PersistentCollectionConfiguration {}