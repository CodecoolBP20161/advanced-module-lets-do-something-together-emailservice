# ActiMate emailservice :mailbox_with_mail:

Our email service sends `html` emails with the desired subject to any arbitrary number of email addresses.

The service operates on `http://localhost:60227`.

Routes:
* `/` to send emails
* `/sent` to get the addresses the emails have been sent to - so you can update your own db or property that tracks emails

## :white_check_mark: Steps to use the service
* Set up your db connection data via **./src/main/resources/db.properties** in the following format:
```
DBURL=jdbc\:postgresql\://localhost\:5432/{your_db}
DB_USER={your_username}
DB_PASSWORD={your_password}
```

  * After you're done with the connection part, you need to build the necessary db structure by running `./src/main/resources/static/init_db.sql`.

* Set up your sender info in **./src/main/resources/sender.properties**:
```
SENDER_PWD={your_pwd}
SENDER_ADDRESS={your_email}
```

## :white_check_mark: To send emails
You need the following `json` sent to `http://localhost:60227/`:
  * An arbitrary number of email addresses in `String` format, separated by a comma ("*,*") under the name `emails`.
  * Your desired `html` template along with the name `template`. It also works with plain `String` text as the body of the email.
  * Subject of the email(s) called `subject`.
  * **example request**:
```
{"emails":"email@example.com,email2@example.com",
 "template":"<h1>hello</h1>",
 "subject":"no subject"}
```


## :white_check_mark: To get info about the emails
Send a `request` to `http://localhost:60227/sent`.
  * The `response` will be a list of email addresses to which our service has sent the email. The format is in the same fashion as the input was: if there are such addresses in the db, you'll get a `String` under the name `emails`.
  * If there are more than one, they're separated by a comma ("*,*").
  * If there are no records in our db, the response will be empty.
