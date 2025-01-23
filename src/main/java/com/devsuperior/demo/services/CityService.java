package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAllSortedByName() {
        return cityRepository.findAll(Sort.by("name"))
                .stream().map(CityDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insertCity(CityDTO dto) {
        City entity = copyDtoToEntity(dto);
        City result = cityRepository.save(entity);
        return new CityDTO(result);
    }

    private City copyDtoToEntity(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        return entity;
    }
}
