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
}
