package org.ticparabien.hotelcovid19.domain.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ticparabien.hotelcovid19.domain.Patient;
import org.ticparabien.hotelcovid19.domain.repositories.PatientRepository;

import java.util.List;

@Service
@Component
public class FindPatientsWithHighFever {
    private final PatientRepository patientRepository;

    @Autowired
    public FindPatientsWithHighFever(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> execute() {
        return patientRepository.findAllWithFever();
    }
}
