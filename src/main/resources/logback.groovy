appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"
    }
}

logger("io.marvellab.qtbot", DEBUG, ["Console-Appender"], false)

root(ERROR, ["Console-Appender"])