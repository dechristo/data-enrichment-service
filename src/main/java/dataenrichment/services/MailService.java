package dataenrichment.services;

import dataenrichment.models.EnrichedTemperatureDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EnrichedTemperatureDataDTO data) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("dudu.christo@gmail.com");
        msg.setSubject("Sensor High Temperature Alert: " + data.getName());
        msg.setText(String.format("Sensor %s had a high temperature reading: \n Value: %s \n City: %s \n Country: %s",
            data.getName(), data.getValue(), data.getLocation().getCity(), data.getLocation().getCountry()));
        javaMailSender.send(msg);
    }
}
