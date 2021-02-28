package com.epam.cloud.service.imp;

import com.epam.cloud.data.Health;
import com.epam.cloud.service.HealhCheckService;
import org.springframework.stereotype.Service;


@Service
public class HealthCheckServiceImpl implements HealhCheckService {
    @Override
    public Health checkHealth() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        return 0;
    }
}
