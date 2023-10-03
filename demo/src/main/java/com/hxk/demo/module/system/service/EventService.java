package com.hxk.demo.module.system.service;

import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.exception.common.CodeEnum;
import com.hxk.demo.exception.common.CommonException;
import com.hxk.demo.module.system.entity.Event;
import com.hxk.demo.module.system.entity.vo.EventVO;
import com.hxk.demo.module.system.jdbc.EventJDBC;
import com.hxk.demo.module.system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.service
 * @className: NotionService
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Service
public class EventService {

    @Autowired
    private EventJDBC eventJDBC;
    @Autowired
    private EventRepository repository;

    public List<EventVO> getList() {
        List<EventVO> list = eventJDBC.getList();

        if (list.isEmpty()) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }

        return list;
    }

    public List<EventVO> findEventByGroup(String group) {
        if (!"error".equals(group) && !"warn".equals(group)) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        List<EventVO> list = eventJDBC.getListByGroup(group);
        if (list.isEmpty()) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        return list;
    }

    public void deleteById(String id) {
        Long tag = Long.valueOf(id);
        Optional<Event> option = repository.findById(tag);
        if (!option.isPresent()) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        Event event = option.get();
        if (!"warn".equals(event.getEventName())) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        repository.deleteById(tag);
    }

    public void operateById(String id, String user) {
        Long tag = Long.valueOf(id);
        Optional<Event> option = repository.findById(tag);
        if (!option.isPresent()) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        Event event = option.get();
        if (!"error".equals(event.getEventName())) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        event.setUserName(user);
        repository.save(event);
    }
}
