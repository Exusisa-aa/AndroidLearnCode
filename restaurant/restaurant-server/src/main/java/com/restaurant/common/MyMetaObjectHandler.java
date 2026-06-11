package com.restaurant.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Custom MetaObjectHandler to auto-fill fields
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        
        // For User table, createUser might be the user themselves or system (0)
        // Here we can use a default or current user ID if available
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            currentId = 0L; // System or Self-Registration
        }
        
        // Only fill if field exists
        if (metaObject.hasSetter("createUser")) {
            this.strictInsertFill(metaObject, "createUser", Long.class, currentId);
        }
        if (metaObject.hasSetter("updateUser")) {
            this.strictInsertFill(metaObject, "updateUser", Long.class, currentId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            currentId = 0L;
        }
        this.strictUpdateFill(metaObject, "updateUser", Long.class, currentId);
    }
}
