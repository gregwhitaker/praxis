/**
 * Copyright 2019 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package praxis.service.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

/**
 * Date formatter to use for all Praxis API responses.
 */
public class PraxisDateFormat {

    private static final ThreadLocal<DateFormat> THREAD_LOCAL_DATE_FORMAT = ThreadLocal.withInitial(() -> {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf;
    });

    private PraxisDateFormat() { }

    public static Date parse(String dateString) throws ParseException {
        return THREAD_LOCAL_DATE_FORMAT.get().parse(fixIso8601TimeZone(dateString));
    }

    public static String format(Timestamp ts) {
        return THREAD_LOCAL_DATE_FORMAT.get().format(ts);
    }

    public static String format(Date date) {
        return THREAD_LOCAL_DATE_FORMAT.get().format(date);
    }

    public static String format(Instant instant) {
        return THREAD_LOCAL_DATE_FORMAT.get().format(instant);
    }

    /**
     * Helper function to handle ISO 8601 strings of the following format:
     * "2008-03-01T13:00:00+01:00".  Note that the final colon (":") in the
     * time zone is not supported by SimpleDateFormat's "Z" token.
     *
     * @param dateString a string containing the date.
     * @return a date string that matches the date format.
     */
    private static String fixIso8601TimeZone(String dateString) {
        if (dateString.length() >= 24 && dateString.charAt(22) == ':') {
            return dateString.substring(0, 22) + dateString.substring(23);
        }

        return dateString;
    }
}
