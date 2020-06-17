package com.delicate.mule.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Ret 用于返回值封装
 */
public class Ret extends HashMap {
    private static final String STATE = "state";
    private static final String STATE_OK = "ok";
    private static final String STATE_FAIL = "fail";

    public Ret() {
    }

    public static Ret by(Object key, Object value) {
        return new Ret().set(key, value);
    }

    public static Ret create(Object key, Object value) {
        return new Ret().set(key, value);
    }

    public static Ret create() {
        return new Ret();
    }

    public static Ret ok() {
        return new Ret().setOk();
    }

    public static Ret ok(Object key, Object value) {
        return ok().set(key, value);
    }

    public static Ret fail() {
        return new Ret().setFail();
    }

    public static Ret fail(Object key, Object value) {
        return fail().set(key, value);
    }

    public Ret setOk() {
        super.put(STATE, STATE_OK);
        return this;
    }

    public Ret setFail() {
        super.put(STATE, STATE_FAIL);
        return this;
    }

    public Ret set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public Ret set(Map map) {
        super.putAll(map);
        return this;
    }

    public Ret set(Ret ret) {
        super.putAll(ret);
        return this;
    }

    public boolean isOk() {
        Object state = get(STATE);
        if (STATE_OK.equals(state)) {
            return true;
        }
        if (STATE_FAIL.equals(state)) {
            return false;
        }
        throw new IllegalStateException("调用 isOk() 之前，必须先调用 ok()、fail() 或者 setOk()、setFail() 方法");
    }

    /**
     * key 存在，并且 value 不为 null
     */
    public boolean notNull(Object key) {
        return get(key) != null;
    }

    /**
     * key 不存在，或者 key 存在但 value 为null
     */
    public boolean isNull(Object key) {
        return get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == true));
    }

    /**
     * key 存在，并且 value 为 false，则返回 true
     */
    public boolean isFalse(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == false));
    }

//    public String toJson() {
//        return JSONUtil.toJsonStr(this);
////        return Json.getJson().toJson(this);
//    }

    @Override
    public boolean equals(Object ret) {
        return ret instanceof Ret && super.equals(ret);
    }
}
