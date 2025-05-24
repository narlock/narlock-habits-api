package com.narlock.habitapi.controller;

import com.narlock.habitapi.model.Habit;
import com.narlock.habitapi.service.HabitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

  private final HabitService habitService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Habit createHabit(@RequestBody Habit body) {
    return habitService.putHabit(body);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Habit> getListsByUserId(@RequestParam String userId) {
    return habitService.getHabitsByUserId(userId);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteHabit(@RequestParam String name, @RequestParam String userId) {
    Habit habit = Habit.builder().name(name).userId(userId).build();
    habitService.deleteHabit(habit);
  }

  @GetMapping("/streak")
  @ResponseStatus(HttpStatus.OK)
  public Integer getStreakForHabit(@RequestBody Habit habit) {
    return habitService.getStreakForHabit(habit);
  }
}
