package Service;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesFromUser(int account_id){
        return messageDAO.getAllMessagesFromUser(account_id);
    }

    public Message getMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }

    public Message postMessage(Message message){
        int message_textlen = message.getMessage_text().length();
        if(message_textlen == 0 || message_textlen > 255){
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public Message deleteMessageByID(int id){
        Message newMessage = messageDAO.getMessageByID(id);
        messageDAO.deleteMessage(id);
        return newMessage;
    }

    public Message updateMessageByID(String message_text, int id){
        if(message_text.length() > 255 || message_text.length() == 0){
            return null;
        }
        messageDAO.updateMessage(message_text, id);
        Message newMessage = messageDAO.getMessageByID(id);
        return newMessage; 
    }
}
