package kitchenpos.eatinorders.ordertable.domain;

public class CleanUpPolicyFixture {

    public static final CleanUpPolicy PASS = orderTableId -> true;
    public static final CleanUpPolicy FAIL = orderTableId -> false;
}
