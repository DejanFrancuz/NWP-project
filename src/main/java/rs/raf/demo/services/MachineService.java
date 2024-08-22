package rs.raf.demo.services;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import rs.raf.demo.dto.Query;
import rs.raf.demo.dto.ScheduleDto;
import rs.raf.demo.enums.MachineStatus;
import rs.raf.demo.errorhandler.InvalidDateFormatException;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Schedule;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.MachineRepository;
import rs.raf.demo.repositories.UserRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Service
public class MachineService implements IService<Machine, Long> {

    private final MachineRepository machineRepository;
    private final UserRepository userRepository;
    private final TaskScheduler taskScheduler;

    @Autowired
    public MachineService(MachineRepository machineRepository, UserRepository userRepository, TaskScheduler taskScheduler) {
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.taskScheduler = taskScheduler;
    }

    public Machine createMachine(String username, MachineStatus status, Boolean active) {
        User createdBy = userRepository.findByEmail(username);
        if (createdBy != null) {
            Machine machine = new Machine(status, createdBy, active, username + "s machine");
            return machineRepository.save(machine);
        } else {
            throw new IllegalArgumentException("Korisnik ne postoji: " + username);
        }
    }

    public void startMachine(Machine machine){
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime executionTime = now.plusSeconds(15);

        String cronExpression = generateCronExpression(executionTime);

        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        this.taskScheduler.schedule(() -> {
            System.out.println("starting machine");
            machineRepository.updateMachineStatusToRunning(machine.getId());
        }, cronTrigger);

    }

    public void stopMachine(Machine machine){
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime executionTime = now.plusSeconds(15);

        String cronExpression = generateCronExpression(executionTime);

        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        this.taskScheduler.schedule(() -> {
            System.out.println("stopping machine");
            machineRepository.updateMachineStatusToStopped(machine.getId());
        }, cronTrigger);
    }

    public void dischargeMachine(Machine machine){
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime executionTime = now.plusSeconds(15);

        String cronExpression = generateCronExpression(executionTime);

        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        this.taskScheduler.schedule(() -> {
            machineRepository.updateMachineStatusToDischarged(machine.getId());
        }, cronTrigger);

        executionTime = executionTime.plusSeconds(15);
        cronExpression = generateCronExpression(executionTime);
        cronTrigger = new CronTrigger(cronExpression);

        this.taskScheduler.schedule(() -> {
            machineRepository.updateMachineStatusToStopped(machine.getId());
        }, cronTrigger);
    }

    public void scheduleMachine(ScheduleDto schedule){
//        Instant instant = schedule.getExecutionDateTime().toInstant();

        String cronExpression = generateCronExpression(schedule.getExecutionDateTime());

        CronTrigger cronTrigger = new CronTrigger(cronExpression);



        switch (schedule.getMachineOperation()){
            case START:
                System.out.println("operation is: " + schedule.getMachineOperation());
                this.taskScheduler.schedule(() -> {
                    System.out.println("in schedule is: " + schedule.getMachineOperation());
                    machineRepository.updateMachineStatusToRunning(schedule.getMachine().getId());
//                    this.startMachine((schedule.getMachine()));
                }, cronTrigger);
                break;
            case STOP:
                System.out.println("operation is: " + schedule.getMachineOperation());
                this.taskScheduler.schedule(() -> {
                    this.stopMachine((schedule.getMachine()));
                }, cronTrigger);
                break;
            case DISCHARGE:
                System.out.println("operation is: " + schedule.getMachineOperation());
                this.taskScheduler.schedule(() -> {
                    this.dischargeMachine((schedule.getMachine()));
                }, cronTrigger);
                break;
            default:
                System.out.println("operation not found");
                return;
        }
    }

    public List<Machine> findByQuery(Query query){
        try {
            Date startDate = DateUtil.convertStringToDate(query.getDateFrom());
            Date endDate = DateUtil.convertStringToDate(query.getDateTo());

            MachineStatus[] statuses = StatusConverter.convertStringsToMachineStatuses(query.getStatus());

            return machineRepository.findByQuery(query.getName(), statuses, startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format provided", e);
        }
    }

    private String generateCronExpression(LocalDateTime dateTime) {
        System.out.println("dateTimeeeeee: "  + dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss mm HH dd MM ?");
        return dateTime.format(formatter);
    }

    @Override
    public <S extends Machine> S save(S machine) {
        return null;
    }

    @Override
    public Optional<Machine> findById(Long id) {
        return this.machineRepository.findById(id);
    }

    @Override
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.machineRepository.deleteById(id);
    }
}
