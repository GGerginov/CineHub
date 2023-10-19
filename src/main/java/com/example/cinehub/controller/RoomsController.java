package com.example.cinehub.controller;

import com.example.cinehub.controller.responseDTOs.RoomResponseDTO;
import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {


    private final RoomService roomService;

    @Autowired
    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/{slug}")
    public ResponseEntity<?> getAllCinemas(@PathVariable String slug) {

        List<RoomDto> roomsByCinemaSlug = this.roomService.findRoomsByCinemaSlug(slug);


        List<RoomResponseDTO> responseDTOList = roomsByCinemaSlug.stream()
                .map(roomDto -> RoomResponseDTO.builder()
                        .roomNumber(roomDto.getRoomNumber())
                        .capacity(roomDto.getCapacity())
                        .cinemaSlug(roomDto.getCinema().getSlug())
                        .build())
                .toList();

        return new SuccessResponse<>(responseDTOList).getResponse();
    }
}
