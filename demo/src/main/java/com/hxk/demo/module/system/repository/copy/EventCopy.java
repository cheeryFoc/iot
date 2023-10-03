package com.hxk.demo.module.system.repository.copy;

import com.hxk.demo.module.system.entity.Event;
import com.hxk.demo.module.system.entity.dto.EventDTO;
import com.hxk.demo.module.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.repository.copy
 * @className: EventCopy
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Service
public class EventCopy {

    public Event convertOfEventDTO(EventDTO dto) {
        Event event = new Event();
        BeanUtils.copyProperties(dto, event);
        return event;
    }

}
