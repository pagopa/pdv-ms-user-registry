package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    String upsertUser(String fiscalCode, String namespace);

    User getUserById(String id, Optional<List<String>> fields);

//    String save(String pii, Namespace namespace);
//
//    String findById(String pii, Namespace namespace);
//
//    String findPiiByToken(String token);

}
