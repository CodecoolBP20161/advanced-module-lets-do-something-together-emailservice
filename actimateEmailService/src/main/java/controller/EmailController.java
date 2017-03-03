package controller;


import dao.EmailAddressDao;
import dao.impl.EmailAddressDaoImpl;
import model.Email;
import model.Sender;
import service.EmailService;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EmailController {
    private static EmailAddressDao emailAddressDao = EmailAddressDaoImpl.getInstance();
    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    public Response saveAdresses(Request req, Response res) {
        String emailString = req.queryParams("emails");
        try {
            emailAddressDao.save(new ArrayList<>(Arrays.asList(
                    emailString.replaceAll(" ", "+").split(","))),
                    req.queryParams("template"),
                    req.queryParams("subject"));
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return res;
    }

    public void sendToAddresses() {
        List<Email> emails = emailAddressDao.getAllNew();
        if (emails.size() > 0) {
            emails.stream()
                    .filter(email ->
                            service.send(attachSender(email)))
                    .forEach(address ->
                            emailAddressDao.switchSentStatus(address.getReceiver()));
        }
    }

    public String sentAddresses(Request req, Response res) {
        List<String> addresses = new ArrayList<>();
        for (Email email : emailAddressDao.getAllSent()) {
            addresses.add(email.getReceiver());
            emailAddressDao.removeSent();
        }
        return String.join(",", addresses);
    }

    private Email attachSender(Email email) {
        email.setSender(new Sender());
        return email;
    }
}