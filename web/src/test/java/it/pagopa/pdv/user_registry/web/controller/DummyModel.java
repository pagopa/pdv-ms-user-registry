package it.pagopa.pdv.user_registry.web.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class DummyModel {

    @NotNull
    String value;

}
