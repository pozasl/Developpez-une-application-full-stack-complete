package com.openclassrooms.mdd.posts_api.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * An abstract mapper class
 */
public abstract class AbstractMapper {

    /**
     * Date to OffsetDateTime conversion
     *
     * @param date Date
     * @return OffsetDateTime
     */
        protected OffsetDateTime convertDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId =  ZoneOffset.systemDefault();
        return OffsetDateTime.ofInstant(instant, zoneId);
    }
}
