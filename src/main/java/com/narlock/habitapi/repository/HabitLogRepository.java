package com.narlock.habitapi.repository;

import com.narlock.habitapi.model.HabitLog;
import com.narlock.habitapi.model.HabitLogId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitLogRepository extends JpaRepository<HabitLog, HabitLogId> {
  List<HabitLog> getHabitEntriesByNameAndUserId(String name, String userId);
}
