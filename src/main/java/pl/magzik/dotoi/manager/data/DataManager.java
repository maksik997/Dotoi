package pl.magzik.dotoi.manager.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manager responsible for handling all data changes (or at least it should).
 * <p>
 * It allows different parts of the application to be managed independently,
 * meaning that they do not need to be aware of each other.
 * <p>
 * This manager extensively uses the Observer pattern.
 * <ul>
 *     <li>To listen for events, use the {@link DataManager#subscribe(IDataSubscriber)} method.</li>
 *     <li>To stop listening, call the {@link DataManager#unsubscribe(IDataSubscriber)} method.</li>
 *     <li>To trigger an event, use the {@link DataManager#notifySubscribers(DataEvent)} method with the specified event.</li>
 * </ul>
 * <p>
 * This class is implemented as a Singleton using the InstanceHolder pattern,
 * which ensures proper synchronization.
 * Additionally, all subscribers are stored in a thread-safe {@link CopyOnWriteArrayList}.
 * <p>
 * Class is part of Event-Driven-Architecture (or at least tries to be).
 *
 * @see IDataSubscriber
 * @see DataEvent
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class DataManager {

    private static final Logger log = LoggerFactory.getLogger(DataManager.class);

    private static final class InstanceHolder {
        private final static DataManager instance = new DataManager();
    }

    public static DataManager getInstance() {
        return InstanceHolder.instance;
    }

    private final List<IDataSubscriber> subscribers;

    private DataManager() {
        subscribers = new CopyOnWriteArrayList<>();
        log.info("Data manager initialized.");
    }

    public void subscribe(IDataSubscriber subscriber) {
        subscribers.add(subscriber);
        log.debug("New subscriber added.");
    }

    public void unsubscribe(IDataSubscriber subscriber) {
        subscribers.remove(subscriber);
        log.debug("Removed subscriber");
    }

    public void notifySubscribers(DataEvent event) {
        log.debug("Event: {} has occurred.", event);
        for (IDataSubscriber subscriber : subscribers) {
            subscriber.onDataUpdate(event);
        }
    }
}
