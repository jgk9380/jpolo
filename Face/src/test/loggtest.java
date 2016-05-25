package test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class loggtest {
    public loggtest() {
        super();
    }

    public static void main(String[] args) {
       
 
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, "Here is some SEVERE");
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, "Here is some WARNING");
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Here is some INFO");
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.FINE, "Here is some FINE");
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.FINER, "Here is some FINER");
    }
}
