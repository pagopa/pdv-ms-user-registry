package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.TokenizerConnector;
import it.pagopa.pdv.user_registry.connector.model.*;
import it.pagopa.pdv.user_registry.core.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final PersonConnector personConnector;
    private final TokenizerConnector tokenizerConnector;


    @Autowired
    UserServiceImpl(PersonConnector personConnector, TokenizerConnector tokenizerConnector) {
        this.personConnector = personConnector;
        this.tokenizerConnector = tokenizerConnector;
    }


    @Override
    public String upsertUser(String fiscalCode, String namespace) {
        CreateTokenDto createTokenDto = new CreateTokenDto();
        createTokenDto.setPii(fiscalCode);
        TokenResource tokenResource = tokenizerConnector.save(createTokenDto, namespace);
        SavePersonNamespaceDto request = new SavePersonNamespaceDto();
        request.setNamespacedId(tokenResource.getToken());
        personConnector.saveNamespacedId(tokenResource.getRootToken(), namespace, request);
        return tokenResource.getToken();
    }


    @Override
    public User getUserById(String id, Optional<List<String>> fields) {
        PersonResource person = personConnector.getUserById(id, true);
        User user = new User(person);
        if (fields.isEmpty() || fields.get().contains("fiscalCode")) {
            PiiResource pii = tokenizerConnector.findPiiByToken(id);
            user.setFiscalCode(pii.getPii());
        }

        return user;
    }


    //    @Override
//    public String save(String pii, Namespace namespace) {
//        Assert.hasText(pii, "A Private Data is required");
//        Assert.notNull(namespace, "A Namespace is required");
//        log.debug(LogUtils.CONFIDENTIAL_MARKER, "save input: pii = {}, namespace = {}", pii, namespace);
//        String token = tokenizerConnector.save(pii, namespace);
//        log.debug("save output: token = {}", token);
//        return token;
//    }
//
//    @Override
//    public String findById(String pii, Namespace namespace) {
//        Assert.hasText(pii, "A Private Data is required");
//        Assert.notNull(namespace, "A Namespace is required");
//        log.debug(LogUtils.CONFIDENTIAL_MARKER, "findById input: pii = {}, namespace = {}", pii, namespace);
//        String token = tokenizerConnector.findById(pii, namespace);
//        log.debug("findById output: token = {}", token);
//        return token;
//    }
//
//    @Override
//    public String findPiiByToken(String token) {
//        Assert.hasText(token, "A token is required");
//        log.debug("findPiiByToken input: token = {}", token);
//        String pii = tokenizerConnector.findPiiByToken(token);
//        log.debug(LogUtils.CONFIDENTIAL_MARKER, "findById output: pii = {}", pii);
//        return pii;
//    }

}
