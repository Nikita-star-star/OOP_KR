package com.example.jkhspring.entity;

import com.example.jkhspring.searchable.Searchable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request implements Searchable {

    public enum Status {
        NEW("Новая"),
        IN_PROGRESS("В работе"),
        DONE("Завершена"),
        CANCELLED("Отменена");

        private final String title;

        Status(String title) {
            this.title = title;
        }

        /** Человекочитаемое название для отображения в интерфейсе. */
        public String getTitle() {
            return title;
        }
    }

    public enum Priority {
        LOW("Низкий"),
        MEDIUM("Средний"),
        HIGH("Высокий"),
        URGENT("Срочный");

        private final String title;

        Priority(String title) {
            this.title = title;
        }

        /** Человекочитаемое название для отображения в интерфейсе. */
        public String getTitle() {
            return title;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 4000)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_worker_id")
    private Worker assignedWorker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    /** Время начала выполнения/выезда (по аналогии с Appointment.startTime). */
    private LocalDateTime startTime;

    /** Время окончания выполнения/выезда (по аналогии с Appointment.endTime). */
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(requestId, request.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

    @Override
    public String toString() {
        return "Request:\n Id - " + requestId +
                "\nTitle - " + title +
                "\nResident - " + (resident == null ? "-" : resident.getName()) +
                "\nService - " + (serviceType == null ? "-" : serviceType.getName()) +
                "\nWorker - " + (assignedWorker == null ? "-" : assignedWorker.getName()) +
                "\nDate&Time - " + startTime + " - " + endTime +
                "\nPriority - " + (priority == null ? "-" : priority.getTitle()) +
                "\nStatus - " + (status == null ? "-" : status.getTitle());
    }
}
