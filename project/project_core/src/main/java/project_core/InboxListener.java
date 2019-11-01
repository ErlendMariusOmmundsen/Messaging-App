
package project_core;

import java.util.List;

/**
 * Interface to listen to changes that happend in a Inbox.
 * 
 * @author Lukas Tveiten
 *
 */
public interface InboxListener {

  /**
   * The entire inbox have been changed somehow.
   * 
   * @param messages
   *        - How all the messages look now
   */
  void inboxChanged(List<Message> messages);

  /**
   * A single message have been added to the inbox.
   * 
   * @param message -
   *        The added message
   */
  void addedMessage(Message message);

}
