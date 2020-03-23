appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"
    }
}

root(DEBUG, ["Console-Appender"])