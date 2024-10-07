package com.openclassrooms.mdd.posts_api.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public abstract class AbstractMapper {
        protected OffsetDateTime convertDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId =  ZoneOffset.systemDefault();
        return OffsetDateTime.ofInstant(instant, zoneId);
    }
}
