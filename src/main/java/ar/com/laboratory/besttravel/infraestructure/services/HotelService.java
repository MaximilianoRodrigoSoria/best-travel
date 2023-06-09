package ar.com.laboratory.besttravel.infraestructure.services;

import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IHotelService;
import ar.com.laboratory.besttravel.util.CacheConstants;
import ar.com.laboratory.besttravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class HotelService implements IHotelService {

    private HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {

            PageRequest pageRequest = null;
            switch (sortType){
                case NONE -> pageRequest = PageRequest.of(page,size);
                case LOWER -> pageRequest= PageRequest.of(page,size, Sort.by(FIELD_BY_SHORT).ascending());
                case UPPER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SHORT).descending());
            }
            return hotelRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return this.hotelRepository.findByPriceLessThan(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return this.hotelRepository.findByPriceBetween(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readGreaterThan(Integer rating) {
        return this.hotelRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private  HotelResponse entityToResponse(HotelEntity entity){
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
}
