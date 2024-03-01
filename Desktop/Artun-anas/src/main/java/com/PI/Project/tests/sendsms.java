package com.PI.Project.tests;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class sendsms {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACbc5608278bb28c4de0cde4b73f954e65";
    public static final String AUTH_TOKEN = "a3baa74bec138d281804d2dabf8fd706";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21628482349"),
                        new com.twilio.type.PhoneNumber("+17079992405"),
                        "ya haroun ya bouheli")
                .create();

        System.out.println(message.getSid());
    }
}
