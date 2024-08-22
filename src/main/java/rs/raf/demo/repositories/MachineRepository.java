package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.raf.demo.enums.MachineStatus;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Machine m SET m.status = 'RUNNING' WHERE m.id = :machineId")
    void updateMachineStatusToRunning(Long machineId);

    @Transactional
    @Modifying
    @Query("UPDATE Machine m SET m.status = 'STOPPED' , m.ciclusCount = m.ciclusCount + 1 WHERE m.id = :machineId")
    void updateMachineStatusToStopped(Long machineId);

    @Transactional
    @Modifying
    @Query("UPDATE Machine m SET m.status = 'DISCHARGING' WHERE m.id = :machineId")
    void updateMachineStatusToDischarged(Long machineId);

//    @Transactional
//    @Modifying
//    @Query("UPDATE Machine m SET m.ciclusCount = :ciclusCount WHERE m.id = :machineId")
//    void updateMachineCiclusCount(Long machineId, Long ciclusCount);


    @Query("SELECT m FROM Machine m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')) AND m.status IN (:status) AND m.dateCreated >= :dateFrom AND m.dateCreated <= :dateTo")
    List<Machine> findByQuery(@Param("name") String name, @Param("status") MachineStatus[] status, @Param("dateFrom") Date dateFrom,  @Param("dateTo") Date dateTo);

}

