package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import rs.raf.demo.model.Schedule;
import rs.raf.demo.repositories.MachineRepository;
import rs.raf.demo.repositories.ScheduleRepository;
import rs.raf.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class ScheduleService implements IService<Schedule, Long>{

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public <S extends Schedule> S save(S var1) {
        return this.scheduleRepository.save(var1);
    }

    @Override
    public Optional<Schedule> findById(Long var1) {
        Optional<Schedule> mySchedule = this.scheduleRepository.findById(var1);
        return mySchedule;
    }

    @Override
    public List<Schedule> findAll() {
        return this.scheduleRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.deleteById(var1);
    }
}
