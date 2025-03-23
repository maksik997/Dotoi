package pl.magzik.dotoi.manager.data;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface representing a subscriber used in {@link DataManager}.
 * <p>
 * This interface defines a single method that accepts a {@link DataEvent} and is called by
 * {@link DataManager} when a {@link DataEvent} occurs.
 *
 * @see DataManager
 * @see DataEvent
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
@FunctionalInterface
public interface IDataSubscriber {
    void onDataUpdate(@NotNull DataEvent event);
}
