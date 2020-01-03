package org.data;

import java.util.Date;

import org.util.DateTimeUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Date date =  DateTimeUtils.getDateBySimpleFormat("2019-12-11");
        System.out.println(date);
    }
}
