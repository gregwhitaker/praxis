# praxis-java
Praxis client for Java applications.

## Create the Praxis Client
The Praxis client has a fluent builder interface for creating instances:

    Praxis.builder()
            .connect("localhost", 8080)
            .application("demo")
            .environment("test")
            .heartbeat()
                .interval(Duration.ofMinutes(1))
                .build()
            .build();
            
The Praxis client is thread-safe and should be a shared resource in your application.

## Publishing an Event
The Praxis client provides a number of methods for sending events. The one you will most use is `send(UserDefinedEvent)`:

    praxis.send(new UserDefinedEvent.Builder()
                    .application("demo")
                    .attribute("test", "somevalue")
                    .attribute("test1", 1)
                    .build());