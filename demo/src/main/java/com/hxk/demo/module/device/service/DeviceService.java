package com.hxk.demo.module.device.service;

import com.hxk.demo.exception.AlreadyExistsException;
import com.hxk.demo.module.device.entity.Device;
import com.hxk.demo.module.device.entity.DeviceGroup;
import com.hxk.demo.module.device.entity.DeviceModel;
import com.hxk.demo.module.device.entity.dto.DeviceDto;
import com.hxk.demo.module.device.entity.dto.InfoDTO;
import com.hxk.demo.module.device.entity.vo.DeviceVo;
import com.hxk.demo.module.device.jdbc.DeviceJDBC;
import com.hxk.demo.module.device.repository.DeviceGroupRepository;
import com.hxk.demo.module.device.repository.DeviceModelRepository;
import com.hxk.demo.module.device.repository.DeviceRepository;
import com.hxk.demo.module.system.entity.Subscription;
import com.hxk.demo.module.system.service.EmqxService;
import com.hxk.demo.module.system.service.SubscriptionService;
import com.hxk.demo.module.user.entity.User;
import com.hxk.demo.utils.id.UUIDTool;
import com.hxk.demo.utils.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.device.service
 * @className: DeviceService
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Service
public class DeviceService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeviceGroupRepository groupRepository;

    @Autowired
    private DeviceRepository repository;

    @Autowired
    private DeviceModelRepository modelRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private DeviceJDBC deviceJDBC;

    @Autowired
    private EmqxService emqxService;

    @Autowired
    private RedisCache redisCache;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> add(DeviceDto dto) {
//        看看有没有写入分组
        if (dto.getGroupName() == null || "".equals(dto.getGroupName())) {
            dto.setGroupName("default");
        }
        DeviceGroup group = new DeviceGroup();
        group.setGroupName(dto.getGroupName());
//        先查，没有就存，再查
        Optional<DeviceGroup> groupOptional = groupRepository.findByGroupName(dto.getGroupName());
        if (!groupOptional.isPresent()) {
            log.info("find failed, the group nerve exist");
            groupRepository.save(group);
            groupOptional = groupRepository.findByGroupName(dto.getGroupName());
            if (!groupOptional.isPresent()) {
                throw new AlreadyExistsException("save failed");
            }
        }
        group = groupOptional.get();

//        存设备
        Device device = new Device();
        device.setAlias(dto.getAlias());
        device.setGroupId(group.getId());
        device.setDeviceName(UUIDTool.getOrderSeq());
        repository.save(device);

//        存模型
        List<DeviceModel> list = dto.getModelList();
        for (DeviceModel model : list) {
            model.setDeviceId(device.getId());
            modelRepository.save(model);
        }

        log.info("存订阅");
        Subscription subscription = new Subscription();
        subscription.setTopic(device.getDeviceName());
        subscription.setPlatform(device.getDeviceName());
        subscriptionService.addSubscription(subscription);

        log.info("新增设备完成");
        Map<String, String> map = new HashMap<>();
        map.put("tag", device.getDeviceName());
        return map;
    }

    public List<DeviceVo> getList() {
        List<DeviceVo> list = deviceJDBC.getList();
        List<String> client = emqxService.status();
        for (int i = 0; i < list.size(); i++) {
            DeviceVo vo = list.get(i);
            if (client.contains(vo.getDeviceName())) {
                vo.setStatus("online");
                list.set(i, vo);
            }
        }
        return list;
    }

    public List<DeviceModel> getModel(String deviceId) {
        List<DeviceModel> list = modelRepository.findByDeviceId(Long.parseLong(deviceId));
        return list;
    }

    public List<DeviceGroup> getGroup() {
        List<DeviceGroup> list = groupRepository.findAll();
        return list;
    }

    public List<DeviceVo> getDeviceByGroup(String group) {
        List<DeviceVo> list = deviceJDBC.getDeviceByGroup(group);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String deviceName) {

        Optional<Device> optional = repository.findByDeviceName(deviceName);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("Device not found with user name: " + deviceName);
        }
        Device device = optional.get();

        List<Device> list = repository.findByGroupId(device.getGroupId());
        if (list.size() == 1) {
            groupRepository.deleteById(device.getGroupId());
        }

        //删模型 删设备 删订阅 删分组(删最后一位时)
        repository.deleteByDeviceName(deviceName);
        modelRepository.deleteByDeviceId(device.getId());
        subscriptionService.deleteByPlatform(device.getDeviceName());
        //设备下线
        emqxService.offline(deviceName);
        //干掉缓存
        this.clearCache(deviceName);
        log.info(" 已经完成设备删除");
    }

    public void clearCache(String deviceName) {
        if (redisCache.hasKey(deviceName)) {
            redisCache.deleteObject(deviceName);
        }
        if (redisCache.hasKey(deviceName + "io2")) {
            redisCache.deleteObject(deviceName);
        }
        if (redisCache.hasKey(deviceName + "io3")) {
            redisCache.deleteObject(deviceName);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(InfoDTO dto) {
        log.info(dto.toString());
        Optional<Device> optional = repository.findByDeviceName(dto.getDeviceName());
        if (!optional.isPresent() || !dto.getId().equals(optional.get().getId())) {
            throw new UsernameNotFoundException("can't find this device");
        }

        DeviceGroup group = new DeviceGroup();
        group.setGroupName(dto.getGroupName());
        Optional<DeviceGroup> groupOptional = groupRepository.findByGroupName(dto.getGroupName());
        if (!groupOptional.isPresent()) {
            log.info("find failed, the group nerve exist");
            groupRepository.save(group);
            groupOptional = groupRepository.findByGroupName(dto.getGroupName());
            if (!groupOptional.isPresent()) {
                throw new UsernameNotFoundException("save failed");
            }
        }
        group = groupOptional.get();

        Device device = optional.get();
        device.setAlias(dto.getAlias());
        device.setGroupId(group.getId());
        repository.save(device);
        log.info(" 已经完成编辑设备信息");
    }
}
