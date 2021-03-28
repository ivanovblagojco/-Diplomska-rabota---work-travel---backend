package com.diplomska.backend.model;

import com.diplomska.backend.helpers.ConversationHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="conversation", schema="wat")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation")
    private List<UserConversation> userConversations;

    @JsonIgnore
    public ConversationHelper getAsConversationHelper(){
        ConversationHelper ch = new ConversationHelper();
        ch.setId(id);
        ch.setName(name);

        return ch;
    }
}
