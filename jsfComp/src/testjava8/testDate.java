package testjava8;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class testDate {
    public testDate() {
        super();
    }
    public static void main(String[] args) {
       final Clock clock = Clock.systemUTC();
       
       System.out.println(clock.instant() );
       System.out.println(clock.millis() );
       System.out.println("clock.instant="+ clock.instant() );
       System.out.println("clock.millis="+ clock.millis() );
       
       // Get the local date and local time
       final LocalDate date = LocalDate.now();
       final LocalDate dateFromClock = LocalDate.now( clock );
                
       System.out.println( date );
       System.out.println( dateFromClock );
                
       // Get the local date and local time
       final LocalTime time = LocalTime.now();
       final LocalTime timeFromClock = LocalTime.now( clock );
                
       System.out.println( time );
       System.out.println( timeFromClock );
       
       System.out.println("--localDateTime--");
       final LocalDateTime datetime = LocalDateTime.now();
       final LocalDateTime datetimeFromClock = LocalDateTime.now( clock );
                
       System.out.println( datetime );
       System.out.println( datetimeFromClock );
       
       System.out.println("--ZonedDateTime--");
       final ZonedDateTime zonedDatetime = ZonedDateTime.now();
       final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now( clock );
       final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );
                
       System.out.println( zonedDatetime );
       System.out.println( zonedDatetimeFromClock );
       System.out.println( zonedDatetimeFromZone );
       
       System.out.println("--Duration--");
       final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
       final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );
        
       final Duration duration = Duration.between( from, to );
       System.out.println( "Duration in days: " + duration.toDays() );
       System.out.println( "Duration in hours: " + duration.toHours() );
   }
}
