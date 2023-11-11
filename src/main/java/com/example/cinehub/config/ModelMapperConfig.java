package com.example.cinehub.config;


import com.example.cinehub.controller.responseDTOs.*;
import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.data.dtos.TicketDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    private final ModelMapper modelMapper;

    public ModelMapperConfig() {
        this.modelMapper = new ModelMapper();
        setGlobalMapperConfiguration();
    }

    private void setGlobalMapperConfiguration() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(getCinemaResponseDtoConverter());
        modelMapper.addConverter(getRoomResponseDtoConverter());
        modelMapper.addConverter(getRoomMovieResponseDtoConverter());
        modelMapper.addConverter(getTicketResponseDtoConverter());
    }

    private Converter<TicketDto, TicketResponseDto> getTicketResponseDtoConverter() {
        return new AbstractConverter<>(){
            @Override
            protected TicketResponseDto convert(TicketDto ticketDto) {
                return TicketResponseDto.builder()
                        .uuid(ticketDto.getId())
                        .ticketType(ticketDto.getTicketType())
                        .price(ticketDto.getPrice())
                        .showTimeStartTime(ticketDto.getShowTime().getStartTime())
                        .showTimeEndTime(ticketDto.getShowTime().getEndTime())
                        .showTimeMovieTitle(ticketDto.getShowTime().getMovie().getTitle())
                        .isReserved(ticketDto.getIsReserved())
                        .seatNumber(ticketDto.getSeat().getSeatNumber())
                        .seatRow(ticketDto.getSeat().getRowNumber())
                        .roomNumber(ticketDto.getSeat().getRoom().getRoomNumber())
                        .cinemaName(ticketDto.getSeat().getRoom().getCinema().getName())
                        .cinemaCity(ticketDto.getSeat().getRoom().getCinema().getAddress().getCityName())
                        .cinemaStreet(ticketDto.getSeat().getRoom().getCinema().getAddress().getStreet())
                        .cinemaSlug(ticketDto.getSeat().getRoom().getCinema().getSlug())
                        .build();
            }
        };
    }

    private Converter<RoomDto, RoomMovieResponseDTO> getRoomMovieResponseDtoConverter() {
        return new AbstractConverter<>(){
            @Override
            protected RoomMovieResponseDTO convert(RoomDto roomDto) {
                return RoomMovieResponseDTO
                        .builder()
                        .roomNumber(roomDto.getRoomNumber())
                        .capacity(roomDto.getCapacity())
                        .cinemaSlug(roomDto.getCinema().getSlug())
                        .showTimes(roomDto.getShowTimes()
                                .stream()
                                .map(showTime -> ShowTimeResponseDTO.builder()
                                        .movieTitle(showTime.getMovie().getTitle())
                                        .movieDuration(showTime.getMovie().getDuration())
                                        .startTime(showTime.getStartTime())
                                        .endTime(showTime.getEndTime())
                                        .build())
                                .toList()
                        ).build();
            }
        };
    }
    private Converter<CinemaDto, CinemaResponseDTO> getCinemaResponseDtoConverter() {

        return new AbstractConverter<>() {

            @Override
            protected CinemaResponseDTO convert(CinemaDto cinemaDto) {
                return CinemaResponseDTO.builder()
                        .name(cinemaDto.getName())
                        .cityName(cinemaDto.getAddress().getCityName())
                        .slug(cinemaDto.getSlug())
                        .streetName(cinemaDto.getAddress().getStreet())
                        .build();
            }
        };
    }

    private Converter<RoomDto, RoomResponseDTO> getRoomResponseDtoConverter() {

        return new AbstractConverter<>() {
            @Override
            protected RoomResponseDTO convert(RoomDto roomDto) {
                return RoomResponseDTO.builder()
                        .roomNumber(roomDto.getRoomNumber())
                        .capacity(roomDto.getCapacity())
                        .cinemaSlug(roomDto.getCinema().getSlug())
                        .build();
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return this.modelMapper;
    }
}

