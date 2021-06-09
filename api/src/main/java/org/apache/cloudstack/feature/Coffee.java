package org.apache.cloudstack.feature;

import org.apache.cloudstack.api.Identity;
import org.apache.cloudstack.api.InternalIdentity;
import org.apache.cloudstack.api.command.admin.feature.createCoffeeCmd;

public class Coffee {
    public String UUID;
    public Long id;

    public String getUuid() {
        return  this.UUID;
    }

    public void setUuid(String newUuid) {
        this.UUID = newUuid;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Long getId (){
        return this.id;
    }
}
