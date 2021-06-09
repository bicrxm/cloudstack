package org.apache.cloudstack.api.response;

import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.BaseResponse;
import org.apache.cloudstack.api.EntityReference;
import org.apache.cloudstack.api.ResponseObject;
import org.apache.cloudstack.feature.Coffee;

@EntityReference(value = Coffee.class)
public class coffeeResponse extends BaseResponse implements ResponseObject {
    @SerializedName(ApiConstants.ID)
    @Param(description = "the ID of my resource")
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getResponseName() {
        return null;
    }

    public void setResponseName(String commandName) {
    }

    @Override
    public String getObjectName() {
        return null;
    }

    public void setObjectName(String s) {
    }

    @Override
    public String getObjectId() {
        return null;
    }

}
