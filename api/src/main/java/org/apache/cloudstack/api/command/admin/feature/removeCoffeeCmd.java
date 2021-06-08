package org.apache.cloudstack.api.command.admin.feature;

import com.cloud.user.Account;
import org.apache.cloudstack.api.*;
import org.apache.cloudstack.api.response.coffeeResponse;
import org.apache.cloudstack.context.CallContext;
import org.apache.log4j.Logger;

@APICommand(name = "deleteUser", description = "Deletes a Coffee", responseObject = coffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class removeCoffeeCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(removeCoffeeCmd.class.getName());

    private static final String s_name = "removecoffeeresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name = ApiConstants.ID,
            type = CommandType.UUID,
            required = false,
            entityType = coffeeResponse.class,
            description = "ID of my coffee")
    private Long id;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

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
        Coffee result = _configService.editCoffee(this);
        coffeeResponse response = _responseGenerator.createPodResponse(result, false);
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}