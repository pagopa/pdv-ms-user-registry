package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.TokenizerConnector;
import it.pagopa.pdv.user_registry.connector.model.*;
import it.pagopa.pdv.user_registry.core.logging.LogUtils;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.core.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final PersonConnector personConnector;
    private final TokenizerConnector tokenizerConnector;

    public static final String GLOBAL_NAMESPACE = "GLOBAL";


    @Autowired
    UserServiceImpl(PersonConnector personConnector, TokenizerConnector tokenizerConnector) {
        this.personConnector = personConnector;
        this.tokenizerConnector = tokenizerConnector;
    }


    @Override
    public String save(User user, String namespace) {
        log.trace("[save] start");
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[save] inputs: user = {}, namespace = {}", user, namespace);
        Assert.notNull(user, "A user is required");
        Assert.hasText(namespace, "A namespace is required");
        CreateTokenDto createTokenDto = new CreateTokenDto();
        createTokenDto.setPii(user.getFiscalCode());
        TokenResource tokenResource = tokenizerConnector.save(namespace, createTokenDto);//TODO: is correct to call tokenizer before?!?
        SavePersonNamespaceDto savePersonNamespaceDto = new SavePersonNamespaceDto();
        savePersonNamespaceDto.setNamespacedId(tokenResource.getToken());
        personConnector.saveNamespacedId(tokenResource.getRootToken(), namespace, savePersonNamespaceDto);
        SavePersonDto savePersonDto = UserMapper.map(user);
        personConnector.save(tokenResource.getRootToken(), savePersonDto);
        log.debug("[save] output = {}", tokenResource.getToken());
        log.trace("[save] end");
        return tokenResource.getToken();
    }


    @Override
    public User findById(String id, String namespace, boolean fetchFiscalCode) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, fetchFiscalCode = {}", id, fetchFiscalCode);
        Assert.hasText(id, "A user id is required");
        PersonResource person = personConnector.findById(id, namespace);
        User user;
        if (fetchFiscalCode) {
            PiiResource pii = tokenizerConnector.findPiiByToken(id, namespace);
            user = UserMapper.assembles(id, person, pii.getPii());
        } else {
            user = UserMapper.assembles(id, person);
        }
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[findById] output = {}", user);
        log.trace("[findById] end");
        return user;
    }


    @Override
    public void update(String id, User user, String namespace) {
        log.trace("[update] start");
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[update] inputs: id = {}, user = {}", id, user);
        Assert.hasText(id, "A user id is required");
        Assert.notNull(user, "A user is required");
        PersonGlobalId personGlobalId = personConnector.findIdByNamespacedId(id,namespace);
        SavePersonDto savePersonDto = UserMapper.map(user);
        personConnector.save(personGlobalId.getId(), savePersonDto);
        log.trace("[update] end");
    }


    @Override
    public User search(String fiscalCode, String namespace) {
        log.trace("[search] start");
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[search] inputs: fiscalCode = {}, namespace = {}", fiscalCode, namespace);
        Assert.hasText(fiscalCode, "A fiscal code is required");
        Assert.hasText(namespace, "A namespace is required");
        SearchTokenFilterCriteria filterCriteria = new SearchTokenFilterCriteria();
        filterCriteria.setPii(fiscalCode);
        TokenResource resource = tokenizerConnector.search(namespace, filterCriteria);
        PersonResource person = personConnector.findById(resource.getRootToken(), GLOBAL_NAMESPACE);
        User user = UserMapper.assembles(resource.getToken(), person, fiscalCode);
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[search] output = {}", user);
        log.trace("[search] end");
        return user;
    }

}
