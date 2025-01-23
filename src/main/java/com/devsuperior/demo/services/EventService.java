package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPageable(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(EventDTO::new);
    }

    @Transactional
    public EventDTO insertEvent(EventDTO dto) {
        Event event = copyDtoToEntity(dto);
        Event result = eventRepository.save(event);
        return new EventDTO(result);
    }

    private Event copyDtoToEntity(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setUrl(dto.getUrl());
        Optional<City> eventCity = cityRepository.findById(dto.getCityId());
        event.setCity(eventCity.get());
        return event;

    }
}
