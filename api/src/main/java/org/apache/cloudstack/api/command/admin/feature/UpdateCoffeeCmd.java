package org.apache.cloudstack.api.command.admin.feature;

import org.apache.cloudstack.api.ApiErrorCode;
import com.cloud.user.Account;
import org.apache.cloudstack.api.ServerApiException;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.BaseAsyncCmd;
import org.apache.cloudstack.api.BaseCmd;
import org.apache.cloudstack.api.response.CoffeeResponse;
import org.apache.cloudstack.feature.Coffee;
import org.apache.log4j.Logger;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.CoffeeManager;


@APICommand(name = "updateCoffee", description = "Updates coffee", responseObject = CoffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class UpdateCoffeeCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(UpdateCoffeeCmd.class.getName());

    private static final String s_name = "updatecoffeeresponse";

    @Parameter(name = ApiConstants.ID,
            type = BaseCmd.CommandType.UUID,
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
        Coffee result = CoffeeManager.UpdateCoffee(this);

        if (result != null) {
            CoffeeResponse response = new CoffeeResponse();
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to update Coffee");
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
