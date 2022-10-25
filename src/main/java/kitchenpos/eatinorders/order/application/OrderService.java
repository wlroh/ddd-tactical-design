package kitchenpos.eatinorders.order.application;

import kitchenpos.eatinorders.order.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.order.application.dto.EatInOrderResponse;
import kitchenpos.eatinorders.order.domain.EatInOrder;
import kitchenpos.eatinorders.order.domain.EatInOrderFactory;
import kitchenpos.eatinorders.order.domain.EatInOrderRepository;
import kitchenpos.eatinorders.order.domain.LastEatInOrderPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final EatInOrderRepository eatInOrderRepository;
    private final EatInOrderFactory eatInOrderFactory;
    private final LastEatInOrderPolicy lastEatInOrderPolicy;

    public OrderService(final EatInOrderRepository eatInOrderRepository,
                        final EatInOrderFactory eatInOrderFactory,
                        final LastEatInOrderPolicy lastEatInOrderPolicy) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.eatInOrderFactory = eatInOrderFactory;
        this.lastEatInOrderPolicy = lastEatInOrderPolicy;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        final EatInOrder eatInOrder = eatInOrderFactory.create(request.getOrderTableId(), request.getOrderLineItems());
        eatInOrderRepository.save(eatInOrder);
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();
        eatInOrderRepository.save(eatInOrder);
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        eatInOrder.serve();
        eatInOrderRepository.save(eatInOrder);
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        eatInOrder.complete(lastEatInOrderPolicy);
        eatInOrderRepository.save(eatInOrder);
        return EatInOrderResponse.from(eatInOrder);
    }

    public List<EatInOrderResponse> findAll() {
        List<EatInOrder> eatInOrders = eatInOrderRepository.findAll();
        return eatInOrders.stream()
                .map(EatInOrderResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
