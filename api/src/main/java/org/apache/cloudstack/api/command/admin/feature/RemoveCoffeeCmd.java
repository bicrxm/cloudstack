package org.apache.cloudstack.api.command.admin.feature;

import org.apache.cloudstack.api.ApiConstants;
import com.cloud.user.Account;
import org.apache.cloudstack.acl.RoleType;
import org.apache.cloudstack.api.ServerApiException;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.BaseAsyncCmd;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.response.SuccessResponse;
import org.apache.cloudstack.api.response.CoffeeResponse;
import org.apache.cloudstack.api.CoffeeManager;
import org.apache.cloudstack.api.ApiErrorCode;
import org.apache.log4j.Logger;

@APICommand(name = "removeCoffee",
        description = "Deletes a Coffee",
        responseObject = CoffeeResponse.class,
        requestHasSensitiveInfo = false,
        responseHasSensitiveInfo = false,
        authorized = {RoleType.Admin, RoleType.ResourceAdmin, RoleType.DomainAdmin, RoleType.User})
public class RemoveCoffeeCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(RemoveCoffeeCmd.class.getName());

    private static final String s_name = "removecoffeeresponse";
    private static Long id;

    @Parameter(name = ApiConstants.ID,
            type = CommandType.UUID,
            required = false,
            entityType = CoffeeResponse.class,
            description = "ID of my coffee")
    private Long Id;

    public static Long getId() {
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
        boolean result = CoffeeManager.RemoveCoffee(this);
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