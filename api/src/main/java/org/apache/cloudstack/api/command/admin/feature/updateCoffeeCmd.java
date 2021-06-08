package org.apache.cloudstack.api.command.admin.feature;


import com.cloud.user.Account;
import org.apache.cloudstack.api.*;
import org.apache.cloudstack.api.response.coffeeResponse;
import org.apache.cloudstack.region.RegionService;
import org.apache.log4j.Logger;

import javax.inject.Inject;

@APICommand(name = "updateCoffee", description = "Updates coffee", responseObject = coffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class updateCoffeeCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(updateCoffeeCmd.class.getName());

    private static final String s_name = "updatecoffeeresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name = ApiConstants.ID,
            type = BaseCmd.CommandType.UUID,
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
        Coffee result = _configService.updateCoffee();
        if (result != null) {
            coffeeResponse response = _responseGenerator.createCoffeeresponse();
            response.setResponseName(getCommandName());
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to update coffee");
        }
    }
}
