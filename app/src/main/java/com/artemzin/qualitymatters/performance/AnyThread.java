package com.artemzin.qualitymatters.performance;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Marker. Means that component may be accessed from any thread and implementation should be thread safe.
 */
@Target({METHOD, FIELD})
@Retention(SOURCE)
@Inherited
public @interface AnyThread {
}
