package com.example.cinehub.exception.jsonMessages;

public class ErrorMessages {

    public static final ErrorMessage CINEMA_NOT_FOUND = new ErrorMessage(404, "Cinema is not found");
    public static final ErrorMessage ROOM_NOT_FOUND = new ErrorMessage(404, "Room is not found");
    public static final ErrorMessage TICKET_NOT_FOUND = new ErrorMessage(404, "Ticket is not found");

    public static final ErrorMessage SLUG_NOT_FOUND = new ErrorMessage(404, "Slug is not found");
    public static final ErrorMessage TICKET_IS_ALREADY_BOOKED = new ErrorMessage(409, "Ticket is already booked");
    public static final ErrorMessage TICKET_ID_NOT_VALID= new ErrorMessage(422, "Ticket id is not valid");

    public static final ErrorMessage INVALID_JSON_STRUCTURE = new ErrorMessage(400, "Invalid JSON structure.");

}