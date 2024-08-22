package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import rs.raf.demo.model.ErrorMessage;
import rs.raf.demo.repositories.ErrorMessageRepository;
import rs.raf.demo.repositories.ScheduleRepository;

import java.util.List;
import java.util.Optional;

public class ErrorMesageService implements IService<ErrorMessage, Long>{

    private final ErrorMessageRepository errorMessageRepository;

    @Autowired
    public ErrorMesageService(ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }

    @Override
    public <S extends ErrorMessage> S save(S var1) {
        return this.errorMessageRepository.save(var1);
    }

    @Override
    public Optional<ErrorMessage> findById(Long var1) {
        Optional<ErrorMessage> myError =  this.errorMessageRepository.findById(var1);
        return myError;
    }

    @Override
    public List<ErrorMessage> findAll() {
        return this.errorMessageRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.errorMessageRepository.deleteById(var1);
    }
}
