/* TODO: Validate the Builder. */
package pl.magzik.dotoi.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents a recurrence rule for a repeating task.
 * <p>
 * This class defines the recurrence type (e.g., daily, weekly, or monthly),
 * the interval between recurrences, the specific day of the week or month when the task should repeat,
 * and an optional end date. The recurrence rule can be used to determine whether a task should repeat
 * at a given moment in time.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 *     RecurrenceRule rule = new RecurrenceRule.Builder(RecurrenceType.DAILY)
 *                                  .interval(1)
 *                                  .build();
 * }</pre>
 * </p>
 *
 * @see RecurrenceType
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class RecurrenceRule implements Serializable {

    private final RecurrenceType recurrenceType;
    private final int interval;
    private final DayOfWeek dayOfWeek;
    private final int dayOfMonth;
    private final LocalDateTime endDate;

    /**
     * Constructs a new {@link RecurrenceRule} based on the provided builder.
     *
     * @param builder The builder containing the properties for the recurrence rule.
     */
    @Contract(pure = true)
    private RecurrenceRule(@NotNull Builder builder) {
        this.recurrenceType = builder.recurrenceType;
        this.interval = builder.interval;
        this.dayOfWeek = builder.dayOfWeek;
        this.dayOfMonth = builder.dayOfMonth;
        this.endDate = builder.endDate;
    }

    public RecurrenceType getRecurrenceType() {
        return recurrenceType;
    }
    public int getInterval() {
        return interval;
    }
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    public int getDayOfMonth() { return dayOfMonth; }
    public LocalDateTime getEndDate() { return endDate; }
    public boolean hasEndDate() {
        return endDate != null;
    }

    /**
     * Determines whether the task should repeat based on the current time.
     *
     * @param now The current time.
     * @return true if the task should repeat, false otherwise.
     */
    public boolean shouldRepeat(@NotNull LocalDateTime now) {
        if (hasEndDate() && now.isAfter(endDate)) return false;

        return switch (recurrenceType) {
            case DAILY -> shouldRepeatDaily(now);
            case WEEKLY -> shouldRepeatWeekly(now);
            case MONTHLY -> shouldRepeatMonthly(now);
        };
    }

    private boolean shouldRepeatDaily(@NotNull LocalDateTime now) {
        return calculateBetween(ChronoUnit.WEEKS, now);
    }

    private boolean shouldRepeatWeekly(@NotNull LocalDateTime now) {
        return calculateBetween(ChronoUnit.WEEKS, now) && now.getDayOfWeek().equals(dayOfWeek);
    }

    private boolean shouldRepeatMonthly(@NotNull LocalDateTime now) {
        return calculateBetween(ChronoUnit.MONTHS, now) && now.getDayOfMonth() == dayOfMonth;
    }

    private boolean calculateBetween(@NotNull ChronoUnit chronoUnit, @NotNull LocalDateTime now) {
        if (!hasEndDate()) return true;
        long between = chronoUnit.between(now.toLocalDate(), endDate.toLocalDate());
        return between % interval == 0;
    }

    /**
     * Represents the different types of recurrence.
     * <ul>
     *     <li>{@link #DAILY}: The task repeats daily.</li>
     *     <li>{@link #WEEKLY}: The task repeats weekly on a specific day of the week.</li>
     *     <li>{@link #MONTHLY}: The task repeats monthly on a specific day of the month.</li>
     * </ul>
     */
    public enum RecurrenceType {
        DAILY, WEEKLY, MONTHLY;
    }

    /**
     * Builder class for constructing a {@link RecurrenceRule}.
     * <p>
     * Use the builder to specify the recurrence type, interval, specific day of the week or month, and end date.
     * </p>
     */
    public static class Builder {
        private final RecurrenceType recurrenceType;
        private int interval;
        private DayOfWeek dayOfWeek;
        private int dayOfMonth;
        private LocalDateTime endDate;

        /**
         * Constructs a new builder for a {@link RecurrenceRule} with the specified recurrence type.
         *
         * @param recurrenceType The type of recurrence (e.g., daily, weekly, monthly).
         */
        public Builder(@NotNull RecurrenceType recurrenceType) {
            this.recurrenceType = recurrenceType;
        }

        /**
         * Sets the interval between recurrences (e.g., every 2 days, every 3 weeks).
         *
         * @param interval The interval between recurrences.
         * @return the builder instance.
         */
        public Builder interval(int interval) {
            this.interval = interval;
            return this;
        }

        /**
         * Sets the day of the week for weekly recurrence.
         *
         * @param dayOfWeek The day of the week.
         * @return the builder instance.
         */
        public Builder dayOfWeek(@NotNull DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        /**
         * Sets the day of the month for monthly recurrence.
         *
         * @param dayOfMonth The day of the month.
         * @return the builder instance.
         */
        public Builder dayOfMonth(int dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
            return this;
        }

        /**
         * Sets the end date for the recurrence.
         *
         * @param endDate The end date for the recurrence.
         * @return the builder instance.
         */
        public Builder endDate(@NotNull LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        /**
         * Builds and returns the {@link RecurrenceRule} based on the provided properties.
         *
         * @return a new {@link RecurrenceRule}.
         */
        public RecurrenceRule build() {
            return new RecurrenceRule(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecurrenceRule that = (RecurrenceRule) o;
        return interval == that.interval &&
                dayOfMonth == that.dayOfMonth &&
                recurrenceType == that.recurrenceType &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recurrenceType, interval, dayOfWeek, dayOfMonth, endDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RecurrenceRule.class.getSimpleName() + "[", "]")
                .add("recurrenceType=" + recurrenceType)
                .add("interval=" + interval)
                .add("dayOfWeek=" + dayOfWeek)
                .add("dayOfMonth=" + dayOfMonth)
                .add("endDate=" + endDate)
                .toString();
    }
}
