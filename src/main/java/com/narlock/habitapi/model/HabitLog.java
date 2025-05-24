package com.narlock.habitapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "habit_log")
@IdClass(HabitLogId.class)
public class HabitLog {
  @Id
  @Column(name = "habit_name")
  private String name;

  @Id
  @Column(name = "user_id")
  private String userId;

  @Id
  @Column(name = "completed_date")
  private LocalDate date;
}
