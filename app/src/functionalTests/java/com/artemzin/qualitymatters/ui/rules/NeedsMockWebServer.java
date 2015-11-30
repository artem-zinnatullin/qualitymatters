package com.artemzin.qualitymatters.ui.rules;

import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @see MockWebServerRule
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface NeedsMockWebServer {

    /**
     * Optional specifier for a setupMethod that needs to be invoked
     * during initialization of {@link com.squareup.okhttp.mockwebserver.MockWebServer}.
     * <p>
     * Useful for setting up responses that you simply can not define in the test code because app already hit the server.
     *
     * @return empty string if no method invocation required or public method name that needs to be called.
     */
    @NonNull String setupMethod() default "";
}
