package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService {
Set<FlyResponse> readByOriginDestiny(String origin, String destiny);


}
