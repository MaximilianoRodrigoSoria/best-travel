package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse> {
    Set<HotelResponse> readGreaterThan(Integer rating);
}
