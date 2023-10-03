package com.hxk.demo.module.system.entity;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.entity
 * @className: Record
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class Record {

    private String id;
    private String attribute;
    private String command;
    private String value;

    public Record() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", attribute='" + attribute + '\'' +
                ", command='" + command + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
