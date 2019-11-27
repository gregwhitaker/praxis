# praxis-java
Praxis client for Java.

## Create the Praxis Client
The Praxis client has a fluent builder interface for creating instances:

    return Praxis.builder()
            .hostname("localhost")
            .port(8080)
            .build();
            
The Praxis client is thread-safe and should be a shared resource in your application.

## Publishing an Event
The Praxis client provides a single `event` method that allows you to supply an arbitrary object that will be serialized as JSON.

    // Publishing a Map
    Map<String, Object> eventData = new HashMap<>();
    eventData.put("os", os.toString());
    eventData.put("uptime", FormatUtil.formatElapsedSecs(os.getSystemUptime()));

    praxis.event(eventData, Map.class);