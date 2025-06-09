package com.narlock.habitapi.model.response;

import com.narlock.habitapi.model.Habit;
import com.narlock.habitapi.model.HabitLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLogResponse {
    private Habit habit;
    private LocalDate date;
    private Integer streak;
}
