import controller.EmailController;
import dao.DbHandler;
import service.EmailService;

import java.util.Timer;
import java.util.TimerTask;

import static spark.Spark.*;


public class Server {
    private static final Timer timer = new Timer();
    private static EmailController emailController;

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(60227);

        emailController = new EmailController(EmailService.getInstance());
        DbHandler.setConnection();

        get("/", emailController::saveAdresses);
        get("/sent", emailController::sentAddresses);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try{
                    emailController.sendToAddresses();
                } catch (NullPointerException e){
                    e.getMessage();
                }
            }
        };
        timer.schedule(timerTask, 1000, 30000);
    }
}