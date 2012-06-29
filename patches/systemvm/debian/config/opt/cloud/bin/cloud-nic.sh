#!/usr/bin/env bash


plug_nic() {
  sudo echo "$tableNo $tableName" >> /etc/iproute2/rt_tables 2>/dev/null
  sudo ip rule add fwmark $tableNo table $tableName 2>/dev/null
  sudo ip route flush table $tableName
  sudo ip route flush cache
}


unplug_nic() {
  sudo iptables -t mangle -D PREROUTING -i $dev -m state --state NEW -j CONNMARK --set-mark $tableNo 2>/dev/null

  sudo ip rule del fwmark $tableNo 2>/dev/null
  sudo ip route flush table $tableName
  sudo sed -i /"$tableNo $tableName"/d /etc/iproute2/rt_tables 2>/dev/null
  sudo ip route flush cache
  # remove usage
  sudo iptables -t mangle -F NETWORK_STATS_$dev 2>/dev/null
  sudo iptables -t mangle -D POSTROUTING -o $dev -j NETWORK_STATS_$dev 2>/dev/null
  rule=$(iptables-save | grep NETWORK_STATS_$dev | grep "\-A")
  if [ $? -eq 0 ]
  then
    rule=$(echo $rule | sed 's/\-A/\-D/')
    sudo iptables $rule
  fi
  sudo iptables -t mangle -X NETWORK_STATS_$dev 2>/dev/null
}

action=$1
dev=$2
tableNo=${dev:3}
tableName="Table_$dev"

if [ $action == 'add' ]
then
  plug_nic
else
  unplug_nic
fi
