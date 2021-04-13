package com.epam.cloud.service.imp;

import com.epam.cloud.data.HealthData;
import com.epam.cloud.service.HealhCheckService;
import org.springframework.stereotype.Service;


@Service
public class HealthCheckServiceImpl implements HealhCheckService {
    @Override
    public HealthData checkHealth() {
        int errorCode = check();
        if (errorCode != 0) {
            return HealthData.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return HealthData.up().build();
    }

    public int check() {
        return 0;
    }
}
