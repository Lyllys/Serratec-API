package org.serratec.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired 
    private JavaMailSender mailSender;

    public Boolean enviar(String titulo, String texto, String para) {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        String html = "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>" + titulo + "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div><b>E-commerce</b></div>\n" +
                "\n" +
                "<div>" + texto + "</div>\n" +
                "</body>\n" +
                "</html>\n";

        try {
            helper.setSubject(titulo);
            helper.setText(html, true);
            helper.setTo(para);

            mailSender.send(message); 

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

