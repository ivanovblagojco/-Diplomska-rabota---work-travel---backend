package com.diplomska.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="message", schema="wat")
public class Message implements Comparable<Message>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @CreationTimestamp
    private OffsetDateTime date_creation;

    @ManyToOne
    @JoinColumn(name="sender_user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name="receiver_user_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name="conversation_id")
    private Conversation conversation;

    @Override
    public int compareTo(Message o) {
        return this.getId().compareTo(o.getId());
    }
}
