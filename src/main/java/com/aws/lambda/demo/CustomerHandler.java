package com.aws.lambda.demo;

import io.micronaut.function.aws.MicronautRequestHandler;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rdsdata.RdsDataClient;
import software.amazon.awssdk.services.rdsdata.model.ExecuteStatementRequest;

public class CustomerHandler extends MicronautRequestHandler<Object, Object> {

    @Override
    public Object execute(Object input) {
        RdsDataClient client = RdsDataClient.builder().region(Region.US_EAST_1).build();

        String resourceArn = "arn:aws:rds:us-east-1:231909950768:cluster:database-customer-new";
        String secretArn = "arn:aws:secretsmanager:us-east-1:231909950768:secret:rds-db-credentials/cluster-HFUKWCAJGOLFFACY6AFMED6W2A/postgres/1677425778600-L9Ah0A";
        String database = "lambda_aurora_customers";
        String sqlStatement = "SELECT * FROM Customers";
        ExecuteStatementRequest sqlRequest = ExecuteStatementRequest.builder()
                .resourceArn(resourceArn)
                .secretArn(secretArn)
                .database(database)
                .sql(sqlStatement)
                .build();
        return client.executeStatement(sqlRequest).toString();
    }
}
