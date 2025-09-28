package com.bharatkart.userservice.utility;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OTP {

    public final String generateOTP(){
        return String.valueOf((int)(Math.random()*900000)+100000);
    }


}
