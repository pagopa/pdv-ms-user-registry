package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.core.model.User;

public interface UserService {

    String upsert(User user, String namespace);

    User findById(String id, boolean fetchFiscalCode);

    void save(String id, User user);

    User search(String fiscalCode, String namespace);

}
