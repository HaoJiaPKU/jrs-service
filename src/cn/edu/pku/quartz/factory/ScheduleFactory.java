package cn.edu.pku.quartz.factory;

import org.quartz.ScheduleBuilder;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Created by Lan Zheng on 4/7/16.
 * email: lanzheng@pku.edu.cn
 */
@Component
public class ScheduleFactory {

	@SuppressWarnings("rawtypes")
	public static ScheduleBuilder getSimpleSchedule(int intervalInSeconds) {
        return simpleSchedule().withIntervalInSeconds(intervalInSeconds).repeatForever();
    }
	
    @SuppressWarnings("rawtypes")
	public static ScheduleBuilder getSimpleSchedule(int intervalInSeconds, int repeatCount) {
        return simpleSchedule().withIntervalInSeconds(intervalInSeconds).withRepeatCount(repeatCount);
    }

    @SuppressWarnings("rawtypes")
	public static ScheduleBuilder getCronSchedule(String cronExp) {
        return cronSchedule(cronExp);
    }

    @SuppressWarnings("rawtypes")
	public static ScheduleBuilder getCronSchedule(int hour, int minute) {
        return dailyAtHourAndMinute(hour, minute);
    }

    @SuppressWarnings("rawtypes")
	public static ScheduleBuilder getCronSchedule(int dayOfMonth, int hour, int minute) {
        return monthlyOnDayAndHourAndMinute(dayOfMonth, hour, minute);
    }

    @SuppressWarnings("rawtypes")
	public static ScheduleBuilder getCronSchedule(int hour, int minute, Integer... daysOfWeek) {
        return atHourAndMinuteOnGivenDaysOfWeek(hour, minute, daysOfWeek);
    }
}
