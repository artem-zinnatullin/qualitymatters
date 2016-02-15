package com.artemzin.qualitymatters.integration_tests.api;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class HttpCodes {

    // https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_Error
    private static final List<Integer> CLIENT_SIDE_ERROR_CODES = unmodifiableList(asList(
            400,
            401,
            402,
            403,
            404,
            405,
            406,
            // 408, OkHttp will do silent retry, so either we need to test that separately or be able to include it in generic tests somehow.
            409,
            410,
            411,
            412,
            413,
            414,
            415,
            416,
            417,
            418, // I'm teapot. (Really).
            422,
            423,
            424,
            426,
            428,
            429,
            431,
            440
    ));

    // https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_Error
    private static final List<Integer> SERVER_SIDE_ERROR_CODES = unmodifiableList(asList(
            500,
            501,
            502,
            503,
            504,
            505,
            506,
            507,
            508,
            509,
            510,
            511
    ));

    private static final List<Integer> CLIENT_AND_SERVER_ERROR_CODES;

    static {
        List<Integer> clientAndServerErrorCodes = new ArrayList<>(CLIENT_SIDE_ERROR_CODES.size() + SERVER_SIDE_ERROR_CODES.size());
        clientAndServerErrorCodes.addAll(CLIENT_SIDE_ERROR_CODES);
        clientAndServerErrorCodes.addAll(SERVER_SIDE_ERROR_CODES);

        CLIENT_AND_SERVER_ERROR_CODES = unmodifiableList(clientAndServerErrorCodes);
    }

    private HttpCodes() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static List<Integer> clientAndServerSideErrorCodes() {
        return CLIENT_AND_SERVER_ERROR_CODES;
    }
}
