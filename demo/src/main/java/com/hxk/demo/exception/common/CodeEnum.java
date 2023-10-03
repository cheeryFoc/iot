package com.hxk.demo.exception.common;

public enum CodeEnum implements ErrorCode{

    OPT_SUCCEED("901","操作成功"),
    NOT_SUPPORT_DEL("801","无法被您删除"),
    PWD_MATCHING_ERROR("802","密码不匹配"),
    NOT_SUPPORT_DOWNGRADE("803","该用户不支持降级"),
    NOT_SUPPORT_UPDATE("804","不支持更新"),
    NOT_FOUND("805","找不到"),
    NOT_SUCCEED("806","不成功"),
    IS_NULL("807","参数为空"),
    ;

    private final String code;
    private final String msg;

    //要有构造,必要要有参,这样我们就可以使用枚举了
    CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
