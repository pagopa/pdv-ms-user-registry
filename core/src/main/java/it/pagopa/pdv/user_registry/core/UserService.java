package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.core.model.User;

public interface UserService {

    String save(User user, String namespace);

    User findById(String id, boolean fetchFiscalCode);

    void update(String id, User user);

    User search(String fiscalCode, String namespace);

}
