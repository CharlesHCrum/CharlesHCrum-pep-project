package Service;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;
public class MessageService {
    private MessageDAO messageDAO;
    
    /**
     * No-args constructor for messageService which creates an MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Retrieves a list of all messages
     * @return List of Message objects (all messages)
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Retrieves a list of all messages from a particular user
     * @param account_id
     * @return List of Message objects (all messages from user)
     */
    public List<Message> getAllMessagesFromUser(int account_id){
        return messageDAO.getAllMessagesFromUser(account_id);
    }

    /**
     * Retrieves message by its ID, returns null if message id does not refer to an existing message
     * @param message id
     * @return Message object
     */
    public Message getMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }

    /**
     * Post a new message
     * @param message object (no message id)
     * @return created Message
     */
    public Message postMessage(Message message){
        int message_textlen = message.getMessage_text().length();
        if(message_textlen == 0 || message_textlen > 255){
            return null;
        }
        return messageDAO.createMessage(message);
    }

    /**
     * If message id refers to an existing message, then it will be deleted, if not then the newMessage object will 
     * return null, as getMessageByID returns null if message does not exist
     * @param message id
     * @return Message object, deleted message
     */
    public Message deleteMessageByID(int id){
        Message newMessage = messageDAO.getMessageByID(id);
        messageDAO.deleteMessage(id);
        return newMessage;
    }

    /**
     * Updates a message based on its message id, only if updated text is not blank and is less than 255 characters
     * DAO will not update the message if message id does not refer to an existing message
     * MessageDAO.updateMessage returns true if update is successful, getMessageByID is used to retrieve any potentially updated message 
     * @param message_text
     * @param id
     * @return Message object, updated
     */
    public Message updateMessageByID(String message_text, int id){
        if(message_text.length() > 255 || message_text.length() == 0){
            return null;
        }
        messageDAO.updateMessage(message_text, id);
        Message newMessage = messageDAO.getMessageByID(id);
        return newMessage; 
    }
}
