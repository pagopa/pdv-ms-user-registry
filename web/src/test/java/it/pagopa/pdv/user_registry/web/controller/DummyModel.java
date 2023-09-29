package it.pagopa.pdv.user_registry.web.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DummyModel {

    @NotNull
    String value;

}
