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
}