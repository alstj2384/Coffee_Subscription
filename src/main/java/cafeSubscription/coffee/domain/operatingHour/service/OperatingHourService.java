package cafeSubscription.coffee.domain.operatingHour.service;

import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import cafeSubscription.coffee.domain.operatingHour.repository.OperatingHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperatingHourService {

    private final OperatingHourRepository operatingHourRepository;

    public OperatingHour save(OperatingHour operatingHour) {
        return operatingHourRepository.save(operatingHour);
    }

    public OperatingHour update(Integer cafeId, OperatingHour updatedOperatingHour) {

        return operatingHourRepository.save(updatedOperatingHour);
    }
}
