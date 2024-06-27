package com.freeder.buclserver.admin.발주.scheduler;


import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class 발주Scheduler {
    @Value("${shipping.time}")
    private long targetTime;

    private final ShippingRepository shippingRepository;


    @Scheduled(cron = "0 0 4 * * ?")
    @Transactional
    public void 자동발송완료처리() {
        List<Shipping> 자동발송완료처리 = shippingRepository.자동발송완료처리(LocalDateTime.now().minusDays(targetTime));

        if (자동발송완료처리.size() != 0) {
            자동발송완료처리.forEach(shipping -> {
                shipping.setShippingStatus(ShippingStatus.DELIVERED);
            });
            log.info("자동발송완료처리 성공");
        }
    }


}
