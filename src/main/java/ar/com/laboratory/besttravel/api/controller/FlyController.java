package ar.com.laboratory.besttravel.api.controller;


import ar.com.laboratory.besttravel.api.models.responses.FlyResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IFlyService;
import ar.com.laboratory.besttravel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/fly")
@AllArgsConstructor
public class FlyController {

    private final IFlyService flyService;
    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam SortType sortType
            ){
        if(Objects.isNull(sortType))sortType=SortType.NONE;
        var response =flyService.readAll(page, size, sortType);
        return  response.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(response);
    }

}
