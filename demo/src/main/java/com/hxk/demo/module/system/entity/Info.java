package com.hxk.demo.module.system.entity;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.entity
 * @className: Info
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class Info {
    private Long id;
    private String attribute;
    private Long value;

    public Info() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
