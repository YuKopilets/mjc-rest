package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V6__Review_orders_cost extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        OrderCostReviewer orderCostReviewer = OrderCostReviewer.getInstance();
        orderCostReviewer.reviewOrdersCost();
    }
}
