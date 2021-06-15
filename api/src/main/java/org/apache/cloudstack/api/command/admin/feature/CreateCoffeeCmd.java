package org.apache.cloudstack.api.command.admin.feature;

import com.cloud.exception.ResourceAllocationException;
import com.cloud.user.Account;

import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.ApiErrorCode;
import org.apache.cloudstack.api.CoffeeManager;
import org.apache.cloudstack.api.BaseAsyncCreateCmd;
import org.apache.cloudstack.api.response.CoffeeResponse;
import org.apache.cloudstack.api.ServerApiException;
import org.apache.log4j.Logger;
import org.apache.cloudstack.api.Parameter;

@APICommand(name = "createCoffee", description = "Creates a Coffee.", responseObject = CoffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class CreateCoffeeCmd extends BaseAsyncCreateCmd {
    public static final Logger s_logger = Logger.getLogger(CreateCoffeeCmd.class.getName());

    private static final String s_name = "createcoffeeresponse";

    @Parameter(name = ApiConstants.ID,
            type = CommandType.UUID,
            required = false,
            entityType = CoffeeResponse.class,
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
        boolean result = CoffeeManager.createCoffee(this);

        if (result != false) {
            CoffeeResponse response = new CoffeeResponse();
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