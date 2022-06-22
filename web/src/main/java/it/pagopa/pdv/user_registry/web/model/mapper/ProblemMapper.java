package it.pagopa.pdv.user_registry.web.model.mapper;

import it.pagopa.pdv.user_registry.web.model.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

public class ProblemMapper {

    public static ResponseEntity<Problem> toResponseEntity(Problem problem) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_PROBLEM_JSON);
        return new ResponseEntity<>(problem, headers, problem.getStatus());
    }

}
