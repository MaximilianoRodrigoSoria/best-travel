package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.util.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

public interface CatalogService<R> {
    Page<R>readAll(Integer page, Integer size,SortType sortType);
    Set<R>readLessPrice(BigDecimal price);
    Set<R>readBetweenPrices(BigDecimal min, BigDecimal max);
    String FIELD_BY_SHORT = "price";
}
