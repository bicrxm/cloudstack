package org.apache.cloudstack.api.command.admin.feature;

import com.cloud.user.Account;
import org.apache.cloudstack.acl.RoleType;
import org.apache.cloudstack.api.*;
import org.apache.cloudstack.api.response.SuccessResponse;
import org.apache.cloudstack.api.response.coffeeResponse;
import org.apache.cloudstack.context.CallContext;
import org.apache.cloudstack.feature.Coffee;
import org.apache.log4j.Logger;

@APICommand(name = "removeCoffee",
        description = "Deletes a Coffee",
        responseObject = coffeeResponse.class,
        requestHasSensitiveInfo = false,
        responseHasSensitiveInfo = false,
        authorized = {RoleType.Admin, RoleType.ResourceAdmin, RoleType.DomainAdmin, RoleType.User})
public class removeCoffeeCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(removeCoffeeCmd.class.getName());

    private static final String s_name = "removecoffeeresponse";

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
        boolean result = CoffeeManager.removeCoffee(this);
        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete Coffee");
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
}