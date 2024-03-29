package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Message;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Service.MessageService;
import Service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        return app;
    }
    /**
     * Handler to post a new Message, sends 400 error if the message posting is unsuccessful
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.postMessage(message);
        if(createdMessage!=null){
            ctx.json(mapper.writeValueAsString(createdMessage));
        }
        else{
            ctx.status(400);
        }
    }

    /**
     * Handler to get all messages 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to get all messages from a particular user 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     */
    private void getAllMessagesFromUserHandler(Context ctx){
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromUser(account_id);
        ctx.json(messages);
    }

    /**
     * Handler to retrieve a message by its ID
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     */
    private void getMessageByIDHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByID(message_id);
        if(message != null){
            ctx.json(message);
        }
        else{
            ctx.status(200);
        }
    }

    /**
     * Handler to delete a message by ID 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     */
    private void deleteMessageByIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByID(message_id);
        if(message != null){
            ctx.json(message);
        }
        else{
            ctx.status(200);
        }

    }
    /**
     * Handler to update a new message by its ID
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessageByID(message.getMessage_text(), message_id);
        if(updatedMessage != null){
            ctx.json(updatedMessage);
        }
        else{
            ctx.status(400);
        }
    }

    /**
     * Handler to register a new account
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to process logins to an account
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.login(account);
        if(loginAccount!=null){
            ctx.json(mapper.writeValueAsString(loginAccount));
        }else{
            ctx.status(401);
        }
    }
}