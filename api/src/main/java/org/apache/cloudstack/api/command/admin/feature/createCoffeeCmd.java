package org.apache.cloudstack.api.command.admin.feature;

import com.cloud.exception.ResourceAllocationException;
import com.cloud.user.Account;
import org.apache.cloudstack.api.*;
import org.apache.cloudstack.api.response.coffeeResponse;
import org.apache.cloudstack.feature.Coffee;
import org.apache.log4j.Logger;

@APICommand(name = "createCoffee", description = "Creates a Coffee.", responseObject = coffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class createCoffeeCmd extends BaseAsyncCreateCmd {
    public static final Logger s_logger = Logger.getLogger(createCoffeeCmd.class.getName());

    private static final String s_name = "createcoffeeresponse";

    @Parameter(name = ApiConstants.ID,
            type = CommandType.UUID,
            required = false,
            entityType = coffeeResponse.class,
            description = "ID of my coffee")
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }

    @Override
    public void execute() {
        Coffee result = CoffeeManager.createCoffee(this);

        if (result != null) {
            coffeeResponse response = new coffeeResponse();
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create Coffee");
        }
    }

    @Override
    public String getEventType() {
        return null;
    }

    @Override
    public String getEventDescription() {
        return null;
    }

    @Override
    public void create() throws ResourceAllocationException {

    }
}