package com.narlock.habitapi.service;

import com.narlock.habitapi.model.Habit;
import com.narlock.habitapi.model.HabitId;
import com.narlock.habitapi.model.HabitLog;
import com.narlock.habitapi.model.HabitLogId;
import com.narlock.habitapi.model.error.NotFoundException;
import com.narlock.habitapi.model.response.HabitLogResponse;
import com.narlock.habitapi.model.response.HabitRemindResponse;
import com.narlock.habitapi.repository.HabitLogRepository;
import com.narlock.habitapi.repository.HabitRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {
  private final HabitRepository habitRepository;
  private final HabitLogRepository habitLogRepository;

  public Habit getHabit(String name, String userId) {
    Optional<Habit> habitOptional =
        habitRepository.findById(HabitId.builder().name(name).userId(userId).build());
    if (habitOptional.isPresent()) {
      return habitOptional.get();
    }

    throw new NotFoundException("No habit called " + name + " for user with id " + userId);
  }

  public Habit putHabit(Habit habit) {
    return habitRepository.saveAndFlush(habit);
  }

  public List<Habit> getHabitsByUserId(String userId) {
    return habitRepository.getHabitsByUserId(userId);
  }

  public void deleteHabit(Habit habit) {
    habitRepository.delete(habit);
    List<HabitLog> habitEntries =
        habitLogRepository.getHabitEntriesByNameAndUserId(habit.getName(), habit.getUserId());
    for (HabitLog habitLog : habitEntries) {
      habitLogRepository.delete(habitLog);
    }
  }

  public HabitLogResponse createHabitEntry(HabitLog habitLog) {
    getHabit(habitLog.getName(), habitLog.getUserId());
    HabitLog savedHabitLog = habitLogRepository.save(habitLog);
    Habit habit = getHabit(habitLog.getName(), habitLog.getUserId());
    Integer streak = getStreakForHabit(habit);
    return HabitLogResponse.builder()
            .habit(habit)
            .date(savedHabitLog.getDate())
            .streak(streak)
            .build();
  }

  public List<String> getHabitCompletedDate(String name, String userId) {
    getHabit(name, userId);
    List<HabitLog> habitEntries = habitLogRepository.getHabitEntriesByNameAndUserId(name, userId);
    return habitEntries.stream().map(e -> e.getDate().toString()).collect(Collectors.toList());
  }

  public List<String> getHabitEntryByDate(String name, String userId, LocalDate date) {
    log.info("Attempting to retrieve habit entry by date with {} {} {}", name, userId, date);
    getHabit(name, userId);
    HabitLogId habitLogId = HabitLogId.builder().name(name).userId(userId).date(date).build();
    Optional<HabitLog> habitEntryOptional = habitLogRepository.findById(habitLogId);
    if (habitEntryOptional.isPresent()) {
      return List.of(habitEntryOptional.get().getName());
    }

    throw new NotFoundException("No habit entry found on " + date);
  }

  public Integer getStreakForHabit(Habit habit) {
    getHabit(habit.getName(), habit.getUserId());

    LocalDate date = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    Integer streak = 0;
    try {
      while (true) {
        getHabitEntryByDate(habit.getName(), habit.getUserId(), date);
        streak++;
        date = date.minusDays(1);
      }
    } catch (NotFoundException e) {
      return streak;
    }
  }

  public List<HabitRemindResponse> getHabitsByUserIdFilterRemindTimeOnlyNotCompletedToday(String userId) {
    return habitRepository.getHabitsByUserId(userId).stream()
        .filter(habit -> habit.getRemindTime() != null)
        .filter(
            remindHabit ->
                !(getHabitCompletedDate(remindHabit.getName(), remindHabit.getUserId())
                    .contains(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))))
            .map(filteredHabit -> HabitRemindResponse.builder()
                    .remindTime(filteredHabit.getRemindTime())
                    .name(filteredHabit.getName())
                    .remindRepeatMinutes(filteredHabit.getRemindRepeatMinutes()).
                    build())
      .toList();
  }

  public void deleteHabitEntry(String name, String userId, LocalDate date) {
      HabitLog habitLog = HabitLog.builder()
              .name(name)
              .userId(userId)
              .date(date)
              .build();
      habitLogRepository.delete(habitLog);
  }
}
