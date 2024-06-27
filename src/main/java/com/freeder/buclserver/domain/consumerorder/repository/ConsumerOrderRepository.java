package com.freeder.buclserver.domain.consumerorder.repository;

import java.util.List;
import java.util.Optional;

import com.freeder.buclserver.admin.발주.dto.엑셀다운Dto;
import com.freeder.buclserver.admin.발주.dto.엑셀다운가공전Dto;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsumerOrderRepository extends JpaRepository<ConsumerOrder, Long> {

    @EntityGraph(attributePaths = {"product"})
        //List<ConsumerOrder> findAllByConsumerOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<ConsumerOrder> findAllByConsumerOrderByCreatedAtDesc(User consumer, Pageable pageable);

    @EntityGraph(attributePaths = {"product"})
    Optional<ConsumerOrder> findById(Long consumerOrderId);

    Optional<ConsumerOrder> findByOrderCode(String orderCode);

    Optional<ConsumerOrder> findByOrderCodeAndConsumer(String orderCode, User consumer);


    Optional<List<ConsumerOrder>> findByProduct_Id(Long productId);

    List<ConsumerOrder> findByConsumerUserName(String name);

    @Query(value = "select sum(case when s.shipping_status='NOT_PROCESSING' then 1 else 0 end)," +
            "sum(case when s.shipping_status='PROCESSING' then 1 else 0 end)," +
            "sum(case when s.shipping_status='IN_DELIVERY' then 1 else 0 end)," +
            "sum(case when s.shipping_status='DELIVERED' then 1 else 0 end)" +
            "from consumer_order co " +
            "left join shipping s on s.consumser_order_id = co.consumer_order_id " +
            "where is_confirmed = 0 and order_status = 'ORDERED'", nativeQuery = true)
    List<Object[]> 발주메인페이지();

//    @Query("select new com.freeder.buclserver.admin.발주.dto.엑셀다운Dto(" +
//            "co.id," +
//            "p.id," +
//            "p.name," +
//            "cpo.productOptionValue," +
//            "cpo.productOrderQty," +
//            "co.rewardUseAmount," +
//            "co.totalOrderAmount," +
//            "cp.consumerName," +
//            "cp.consumerAddress," +
//            "cp.consumerCellphone," +
//            "null," +
//            "co.createdAt" +
//            ") " +
//            "from ConsumerOrder co " +
//            "left join co.product p " +
//            "left join co.consumerPurchaseOrders cpo " +
//            "left join co.consumerPayments cp " +
//            "where co.isConfirmed = false " +
//            "and co.orderStatus = com.freeder.buclserver.domain.consumerorder.vo.OrderStatus.ORDERED ")
//    List<엑셀다운Dto> 주문수찾기();

    @Query("select new com.freeder.buclserver.admin.발주.dto.엑셀다운가공전Dto(" +
            "co.id," +
            "p.id," +
            "p.name," +
            "cpo.productOptionValue," +
            "cpo.productOrderQty," +
            "co.rewardUseAmount," +
            "co.totalOrderAmount," +
            "cp.consumerName," +
            "cp.consumerAddress," +
            "cp.consumerCellphone," +
            "s.id," +
            "s.shippingCoName," +
            "s.trackingNum," +
            "co.createdAt" +
            ") " +
            "from ConsumerOrder co " +
            "left join co.product p " +
            "left join co.shippings s " +
            "left join co.consumerPurchaseOrders cpo " +
            "left join co.consumerPayments cp " +
            "where co.isConfirmed = false " +
            "and co.orderStatus = com.freeder.buclserver.domain.consumerorder.vo.OrderStatus.ORDERED " +
            "and s.shippingStatus = :shippingStatus")
    List<엑셀다운가공전Dto> 주문수찾기(ShippingStatus shippingStatus);


}
