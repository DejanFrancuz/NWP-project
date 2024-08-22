package rs.raf.demo.controllers;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.dto.MachineDto;
import rs.raf.demo.dto.Query;
import rs.raf.demo.dto.ScheduleDto;
import rs.raf.demo.dto.UserDto;
import rs.raf.demo.enums.MachineStatus;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Schedule;
import rs.raf.demo.model.User;
import rs.raf.demo.services.MachineService;
import rs.raf.demo.services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PreAuthorize("hasAuthority('can_search_machine')")
    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Machine> getAllMachines(){
        List<Machine> machines = machineService.findAll();

        machines.forEach(machine -> unproxyCreatedBy(machine));

        return machines;
    };

    @GetMapping(value = "/getone",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Machine> getUser(@RequestParam("id") Long id){
        return machineService.findById(id);
    };

    @PreAuthorize("hasAuthority('can_create_machine')")
    @PostMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Machine addMachine(@RequestBody MachineDto machineDto){
        return machineService.createMachine(machineDto.getUsername(), machineDto.getStatus(), machineDto.isActive());
    }

    @PreAuthorize("hasAuthority('can_destroy_machine')")
    @DeleteMapping( value = "/delete")
    public ResponseEntity<?> destroyMachine(@RequestParam("id") Long id){
        this.machineService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('can_start_machine')")
    @PutMapping(value = "/start")
    public ResponseEntity<?> startMachine(@RequestBody Machine machine){
        if (machine.getStatus() != MachineStatus.STOPPED ) return ResponseEntity.badRequest().build();

        this.machineService.startMachine(machine);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('can_stop_machine')")
    @PutMapping(value = "/stop")
    public ResponseEntity<?> stopMachine(@RequestBody Machine machine){
        if (machine.getStatus() != MachineStatus.RUNNING ) return ResponseEntity.badRequest().build();

        this.machineService.stopMachine(machine);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('can_restart_machine')")
    @PutMapping(value = "/restart")
    public ResponseEntity<?> restartMachine(@RequestBody Machine machine){
        if (machine.getStatus() != MachineStatus.STOPPED ) return ResponseEntity.badRequest().build();

        this.machineService.dischargeMachine(machine);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('can_search_machine')")
    @GetMapping(value = "/search")
    public List<Machine> searchMachine(@RequestParam("name") String name, @RequestParam("status") String[] status,
                                       @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo){

        return this.machineService.findByQuery(new Query(name, status, dateFrom, dateTo));
    }

    @PutMapping(value = "/schedule")
    public ResponseEntity<?> scheduleMachine(@RequestBody ScheduleDto schedule){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Belgrade"));
        machineService.scheduleMachine(schedule);
        return ResponseEntity.ok().build();
    }


    private void unproxyCreatedBy(Machine machine) {
        if (machine != null && machine.getCreatedBy() instanceof HibernateProxy) {
            User realUser = (User) ((HibernateProxy) machine.getCreatedBy()).getHibernateLazyInitializer().getImplementation();
            machine.setCreatedBy(realUser);
        }
    }

    public Date convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
