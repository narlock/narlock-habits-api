package com.narlock.habitapi.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "habit")
@IdClass(HabitId.class)
public class Habit {
  @Id private String name;

  @Id
  @Column(name = "user_id")
  private String userId;

  @Column(name = "remind_time")
  private LocalTime remindTime;

  @Column(name = "remind_repeat_minutes")
  private Integer remindRepeatMinutes;
}
