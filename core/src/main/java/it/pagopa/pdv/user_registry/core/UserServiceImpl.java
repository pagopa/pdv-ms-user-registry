package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.TokenizerConnector;
import it.pagopa.pdv.user_registry.connector.model.*;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.core.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String upsert(User user, String namespace) {
        CreateTokenDto createTokenDto = new CreateTokenDto();
        createTokenDto.setPii(user.getFiscalCode());
        TokenResource tokenResource = tokenizerConnector.save(namespace, createTokenDto);//TODO: is correct to call tokenizer before?!?
        SavePersonNamespaceDto savePersonNamespaceDto = new SavePersonNamespaceDto();
        savePersonNamespaceDto.setNamespacedId(tokenResource.getToken());
        personConnector.saveNamespacedId(tokenResource.getRootToken(), namespace, savePersonNamespaceDto);
        SavePersonDto savePersonDto = UserMapper.map(user);
        personConnector.save(tokenResource.getRootToken(), savePersonDto);
        return tokenResource.getToken();
    }


    @Override
    public User findById(String id, boolean fetchFiscalCode) {
        PersonResource person = personConnector.getUserById(id, true);
        User user = UserMapper.map(person);
        if (fetchFiscalCode) {
            PiiResource pii = tokenizerConnector.findPiiByToken(id);
            user.setFiscalCode(pii.getPii());
        }

        return user;
    }


    @Override
    public void save(String id, User user) {
        PersonGlobalId personGlobalId = personConnector.getUserId(id);
        SavePersonDto savePersonDto = UserMapper.map(user);
        personConnector.save(personGlobalId.getId(), savePersonDto);
    }


    @Override
    public User search(String fiscalCode, String namespace) {
        SearchTokenFilterCriteria filterCriteria = new SearchTokenFilterCriteria();
        filterCriteria.setPii(fiscalCode);
        TokenResource resource = tokenizerConnector.search(namespace, filterCriteria);
        PersonResource person = personConnector.getUserById(resource.getRootToken(), false);
        User user = UserMapper.map(person);
        return user;
    }

}
