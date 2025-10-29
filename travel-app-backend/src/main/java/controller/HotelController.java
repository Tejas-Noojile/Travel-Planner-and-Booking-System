package com.travel.travelapp.controller;

import com.travel.travelapp.entity.Hotel;
import com.travel.travelapp.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String city) {
        return ResponseEntity.ok(hotelService.findHotelsByCity(city));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(hotelService.getAvailableCities());
    }
}
