package service.utils;
import java.util.Comparator;

import service.Message;

/**
 * User: alex
 * Date: 12-3-23
 * Time: ÏÂÎç7:20
 */
public class MessageTimeComparator implements Comparator<Message> {

    @Override
    public int compare(Message message, Message message1) {
        String time1 = message.getTime();
        String time2 = message1.getTime();
        return time1.compareTo(time2);
    }
}