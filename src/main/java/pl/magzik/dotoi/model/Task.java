package pl.magzik.dotoi.model;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Task {

    private final String title;
    private final String description;
    private final String content;
    private final List<URI> hyperlinks; ///< Either apps or sites, anything that can be specified with URI
    private final LocalDateTime createdAt;
    private final LocalDateTime deadline;
    private final RecurrenceRule recurrenceRule;

    private Task(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.content = builder.content;
        this.hyperlinks = builder.hyperlinks;
        this.createdAt = builder.createdAt;
        this.deadline = builder.deadline;
        this.recurrenceRule = builder.recurrenceRule;
    }

    public static class Builder {
        private final String title;
        private final String description;
        private final String content;
        private final List<URI> hyperlinks;
        private final LocalDateTime createdAt;
        private LocalDateTime deadline;
        private RecurrenceRule recurrenceRule;

        public Builder(@NotNull String title, @NotNull String description, @NotNull String content, @NotNull List<URI> hyperlinks, @NotNull LocalDateTime createdAt) {
            this.title = title;
            this.description = description;
            this.content = content;
            this.hyperlinks = hyperlinks;
            this.createdAt = createdAt;
        }

        public Builder deadline(LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder recurrenceRule(RecurrenceRule recurrenceRule) {
            this.recurrenceRule = recurrenceRule;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getContent() {
        return content;
    }
    public List<URI> getHyperlinks() {
        return hyperlinks;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Optional<LocalDateTime> getDeadline() {
        return Optional.ofNullable(deadline);
    }
    public Optional<RecurrenceRule> getRecurrenceRule() {
        return Optional.ofNullable(recurrenceRule);
    }
}
