package rs.raf.demo.services;

import rs.raf.demo.enums.MachineStatus;

import java.util.Arrays;

public class StatusConverter {

    public static MachineStatus convertStringToMachineStatus(String statusString) {
        try {
            return MachineStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + statusString, e);
        }
    }

    public static MachineStatus[] convertStringsToMachineStatuses(String[] statusStrings) {
        return Arrays.stream(statusStrings)
                .map(StatusConverter::convertStringToMachineStatus)
                .toArray(MachineStatus[]::new);
    }
}