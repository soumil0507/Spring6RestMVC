package bose.soumil.spring6restmvc.services;

import bose.soumil.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
