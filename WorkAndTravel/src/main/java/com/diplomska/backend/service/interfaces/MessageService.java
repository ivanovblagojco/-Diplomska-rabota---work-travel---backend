package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.MessageHelper;
import com.diplomska.backend.model.Message;

public interface MessageService {
    Message create(MessageHelper message);
}
