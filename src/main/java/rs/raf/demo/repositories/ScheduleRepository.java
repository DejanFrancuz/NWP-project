package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.demo.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
