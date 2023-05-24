package ar.com.laboratory.besttravel.infraestructure.services;

import ar.com.laboratory.besttravel.api.models.responses.FlyResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IFlyService;
import ar.com.laboratory.besttravel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

    private FlyRepository flyRepository;
    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size,SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest= PageRequest.of(page,size, Sort.by(FIELD_BY_SHORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SHORT).descending());
        }
        return flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set readPrice(BigDecimal price) {
        return null;
    }

    @Override
    public Set readBetweenPrices(BigDecimal min, BigDecimal max) {
        return null;
    }



    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return null;
    }

    private  FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
}
