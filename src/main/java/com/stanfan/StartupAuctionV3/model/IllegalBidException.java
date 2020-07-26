package com.stanfan.StartupAuctionV3.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.BAD_REQUEST, reason = "Bid value too low.")
public class IllegalBidException extends RuntimeException{

}
