package dao;

import model.Email;

import java.util.List;

public interface EmailAddressDao {

    void save(List<String> emails, String body, String subject);

    void switchSentStatus(String email);

    List<Email> getAllNew();

    List<Email> getAllSent();

    void removeSent();
}
