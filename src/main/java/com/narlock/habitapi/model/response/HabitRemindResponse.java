package com.narlock.habitapi.model.response;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class HabitRemindResponse {
  private String name;
  private LocalTime remindTime;
  private Integer remindRepeatMinutes;
}
