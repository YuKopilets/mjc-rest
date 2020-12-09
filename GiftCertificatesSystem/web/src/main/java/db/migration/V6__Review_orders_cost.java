package db.migration;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class V6__Review_orders_cost extends BaseJavaMigration {
    private final OrderCostReviewer orderCostReviewer;

    @Override
    public void migrate(Context context) {
        orderCostReviewer.reviewOrdersCost();
    }
}
