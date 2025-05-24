package com.narlock.habitapi.controller;

import com.narlock.habitapi.model.HabitLog;
import com.narlock.habitapi.service.HabitService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit-log")
@RequiredArgsConstructor
public class HabitLogController {

  private final HabitService habitService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public HabitLog createHabitLog(@RequestBody HabitLog habitLog) {
    return habitService.createHabitEntry(habitLog);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<String> getDateLogForHabit(
      @RequestParam String name,
      @RequestParam String userId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    if (date != null) {
      return habitService.getHabitEntryByDate(name, userId, date);
    }
    return habitService.getHabitCompletedDate(name, userId);
  }
}
