// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

<template>
  <div>
    <a-spin :spinning="loading">
      <a-row :gutter="12">
        <a-col :md="24" :lg="7">
          <info-card
            class="vm-info-card"
            :isStatic="true"
            :resource="resource"
            :title="this.$t('label.unmanaged.instance')" />
        </a-col>
        <a-col :md="24" :lg="17">
          <a-card :bordered="true">
            <a-form
              :form="form"
              @submit="handleSubmit"
              layout="vertical">
              <a-form-item>
                <tooltip-label slot="label" :title="$t('label.displayname')" :tooltip="apiParams.displayname.description"/>
                <a-input
                  v-decorator="['displayname', {
                    rules: [{ required: true, message: $t('message.error.input.value') }]
                  }]"
                  :placeholder="apiParams.displayname.description"
                  ref="displayname"
                  autoFocus />
              </a-form-item>
              <a-form-item>
                <tooltip-label slot="label" :title="$t('label.hostnamelabel')" :tooltip="apiParams.hostname.description"/>
                <a-input
                  v-decorator="['hostname', {}]"
                  :placeholder="apiParams.hostname.description" />
              </a-form-item>
              <a-form-item>
                <tooltip-label slot="label" :title="$t('label.domainid')" :tooltip="apiParams.domainid.description"/>
                <a-select
                  v-decorator="['domainid', {}]"
                  showSearch
                  optionFilterProp="children"
                  :filterOption="(input, option) => {
                    return option.componentOptions.propsData.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
                  }"
                  :loading="optionsLoading.domains"
                  :placeholder="apiParams.domainid.description"
                  @change="val => { this.selectedDomainId = val }">
                  <a-select-option v-for="dom in domainSelectOptions" :key="dom.value" :label="dom.label">
                    <span>
                      <resource-icon v-if="dom.icon" :image="dom.icon" size="1x" style="margin-right: 5px"/>
                      <a-icon v-else-if="dom.value !== null" style="margin-right: 5px" type="block" />
                      {{ dom.label }}
                    </span>
                  </a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item v-if="selectedDomainId">
                <tooltip-label slot="label" :title="$t('label.account')" :tooltip="apiParams.account.description"/>
                <a-input
                  v-decorator="['account', {}]"
                  :placeholder="apiParams.account.description"/>
              </a-form-item>
              <a-form-item>
                <tooltip-label slot="label" :title="$t('label.project')" :tooltip="apiParams.projectid.description"/>
                <a-select
                  v-decorator="['projectid', {}]"
                  showSearch
                  optionFilterProp="children"
                  :filterOption="(input, option) => {
                    return option.componentOptions.propsData.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
                  }"
                  :loading="optionsLoading.projects"
                  :placeholder="apiParams.projectid.description">
                  <a-select-option v-for="proj in projectSelectOptions" :key="proj.value" :label="proj.label">
                    <span>
                      <resource-icon v-if="proj.icon" :image="proj.icon" size="1x" style="margin-right: 5px"/>
                      <a-icon v-else-if="proj.value !== null" style="margin-right: 5px" type="project" />
                      {{ proj.label }}
                    </span>
                  </a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item>
                <tooltip-label slot="label" :title="$t('label.templatename')" :tooltip="apiParams.templateid.description + '. ' + $t('message.template.import.vm.temporary')"/>
                <a-radio-group
                  style="width:100%"
                  :value="templateType"
                  @change="changeTemplateType">
                  <a-row :gutter="12">
                    <a-col :md="24" :lg="12">
                      <a-radio value="auto">
                        {{ $t('label.template.temporary.import') }}
                      </a-radio>
                    </a-col>
                    <a-col :md="24" :lg="12">
                      <a-radio value="custom">
                        {{ $t('label.template.select.existing') }}
                      </a-radio>
                      <a-select
                        :disabled="templateType === 'auto'"
                        style="margin-top:10px"
                        v-decorator="['templateid', {
                          rules: [{ required: templateType !== 'auto', message: $t('message.error.input.value') }]
                        }]"
                        showSearch
                        optionFilterProp="children"
                        :filterOption="(input, option) => {
                          return option.componentOptions.propsData.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
                        }"
                        :loading="optionsLoading.templates"
                        :placeholder="apiParams.templateid.description">
                        <a-select-option v-for="temp in templateSelectOptions" :key="temp.value" :label="temp.label">
                          <span>
                            <resource-icon v-if="temp.icon" :image="temp.icon" size="1x" style="margin-right: 5px"/>
                            <os-logo v-else-if="temp.value !== null" :osId="temp.ostypeid" :osName="temp.ostypename" size="lg" style="margin-left: -1px" />
                            {{ temp.label }}
                          </span>
                        </a-select-option>
                      </a-select>
                    </a-col>
                  </a-row>
                </a-radio-group>
              </a-form-item>
              <a-form-item>
                <tooltip-label slot="label" :title="$t('label.serviceofferingid')" :tooltip="apiParams.serviceofferingid.description"/>
              </a-form-item>
              <compute-offering-selection
                :compute-items="computeOfferings"
                :loading="computeOfferingLoading"
                :rowCount="totalComputeOfferings"
                :value="computeOffering ? computeOffering.id : ''"
                :minimumCpunumber="isVmRunning ? resource.cpunumber : null"
                :minimumCpuspeed="isVmRunning ? resource.cpuspeed : null"
                :minimumMemory="isVmRunning ? resource.memory : null"
                size="small"
                @select-compute-item="($event) => updateComputeOffering($event)"
                @handle-search-filter="($event) => fetchComputeOfferings($event)" />
              <compute-selection
                class="row-element"
                v-if="computeOffering && (computeOffering.iscustomized || computeOffering.iscustomizediops)"
                :isCustomized="computeOffering.iscustomized"
                :isCustomizedIOps="'iscustomizediops' in computeOffering && computeOffering.iscustomizediops"
                :cpuNumberInputDecorator="cpuNumberKey"
                :cpuSpeedInputDecorator="cpuSpeedKey"
                :memoryInputDecorator="memoryKey"
                :computeOfferingId="computeOffering.id"
                :preFillContent="this.resource"
                :isConstrained="'serviceofferingdetails' in computeOffering"
                :minCpu="getMinCpu()"
                :maxCpu="getMaxCpu()"
                :minMemory="getMinMemory()"
                :maxMemory="getMaxMemory()"
                @update-iops-value="updateFieldValue"
                @update-compute-cpunumber="updateFieldValue"
                @update-compute-cpuspeed="updateFieldValue"
                @update-compute-memory="updateFieldValue" />
              <div v-if="resource.disk && resource.disk.length > 1">
                <a-form-item>
                  <tooltip-label slot="label" :title="$t('label.disk.selection')" :tooltip="apiParams.datadiskofferinglist.description"/>
                </a-form-item>
                <a-form-item :label="$t('label.rootdisk')">
                  <a-select
                    v-decorator="['rootdiskid', {
                      rules: [{ required: true, message: $t('message.error.input.value'), }],
                      initialValue: 0
                    }]"
                    defaultActiveFirstOption
                    showSearch
                    optionFilterProp="children"
                    :filterOption="(input, option) => {
                      return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
                    }"
                    @change="val => { selectedRootDiskIndex = val }">
                    <a-select-option v-for="(opt, optIndex) in resource.disk" :key="optIndex">
                      {{ opt.label || opt.id }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
                <multi-disk-selection
                  :items="dataDisks"
                  :zoneId="cluster.zoneid"
                  :selectionEnabled="false"
                  :customOfferingsAllowed="true"
                  :autoSelectCustomOffering="true"
                  :autoSelectLabel="$t('label.auto.assign.diskoffering.disk.size')"
                  @select-multi-disk-offering="updateMultiDiskOffering" />
              </div>
              <div v-if="resource.nic && resource.nic.length > 0">
                <a-form-item>
                  <tooltip-label slot="label" :title="$t('label.network.selection')" :tooltip="apiParams.nicnetworklist.description"/>
                  <span>{{ $t('message.ip.address.changes.effect.after.vm.restart') }}</span>
                </a-form-item>
                <multi-network-selection
                  :items="nics"
                  :zoneId="cluster.zoneid"
                  :selectionEnabled="false"
                  :filterUnimplementedNetworks="true"
                  filterMatchKey="broadcasturi"
                  @select-multi-network="updateMultiNetworkOffering" />
              </div>
              <a-row :gutter="12">
                <a-col :md="24" :lg="12">
                  <a-form-item>
                    <tooltip-label slot="label" :title="$t('label.migrate.allowed')" :tooltip="apiParams.migrateallowed.description"/>
                    <a-switch v-decorator="['migrateallowed', {initialValue: this.switches.migrateAllowed}]" :checked="this.switches.migrateAllowed" @change="val => { this.switches.migrateAllowed = val }" />
                  </a-form-item>
                </a-col>
                <a-col :md="24" :lg="12">
                  <a-form-item>
                    <tooltip-label slot="label" :title="$t('label.forced')" :tooltip="apiParams.forced.description"/>
                    <a-switch v-decorator="['forced', {initialValue: this.switches.forced}]" :checked="this.switches.forced" @change="val => { this.switches.forced = val }" />
                  </a-form-item>
                </a-col>
              </a-row>
              <div :span="24" class="action-button">
                <a-button @click="closeAction">{{ this.$t('label.cancel') }}</a-button>
                <a-button :loading="loading" type="primary" @click="handleSubmit">{{ this.$t('label.ok') }}</a-button>
              </div>
            </a-form>
          </a-card>
        </a-col>
      </a-row>
    </a-spin>
  </div>
</template>

<script>
import { api } from '@/api'
import _ from 'lodash'
import InfoCard from '@/components/view/InfoCard'
import TooltipLabel from '@/components/widgets/TooltipLabel'
import ComputeOfferingSelection from '@views/compute/wizard/ComputeOfferingSelection'
import ComputeSelection from '@views/compute/wizard/ComputeSelection'
import MultiDiskSelection from '@views/compute/wizard/MultiDiskSelection'
import MultiNetworkSelection from '@views/compute/wizard/MultiNetworkSelection'
import OsLogo from '@/components/widgets/OsLogo'
import ResourceIcon from '@/components/view/ResourceIcon'

export default {
  name: 'ImportUnmanagedInstances',
  components: {
    InfoCard,
    TooltipLabel,
    ComputeOfferingSelection,
    ComputeSelection,
    MultiDiskSelection,
    MultiNetworkSelection,
    OsLogo,
    ResourceIcon
  },
  props: {
    cluster: {
      type: Object,
      required: true
    },
    resource: {
      type: Object,
      required: true
    },
    isOpen: {
      type: Boolean,
      required: false
    }
  },
  data () {
    return {
      options: {
        domains: [],
        projects: [],
        templates: []
      },
      rowCount: {},
      optionsLoading: {
        domains: false,
        projects: false,
        templates: false
      },
      domains: [],
      domainLoading: false,
      selectedDomainId: null,
      templates: [],
      templateLoading: false,
      templateType: 'auto',
      totalComputeOfferings: 0,
      computeOfferings: [],
      computeOfferingLoading: false,
      computeOffering: {},
      selectedRootDiskIndex: 0,
      dataDisksOfferingsMapping: {},
      nicsNetworksMapping: {},
      cpuNumberKey: 'cpuNumber',
      cpuSpeedKey: 'cpuSpeed',
      memoryKey: 'memory',
      minIopsKey: 'minIops',
      maxIopsKey: 'maxIops',
      switches: {},
      loading: false
    }
  },
  beforeCreate () {
    this.form = this.$form.createForm(this)
    this.apiConfig = this.$store.getters.apis.importUnmanagedInstance || {}
    this.apiParams = {}
    this.apiConfig.params.forEach(param => {
      this.apiParams[param.name] = param
    })
  },
  created () {
    this.fetchData()
    this.form.getFieldDecorator('computeofferingid', { initialValue: undefined, preserve: true })
    this.form.getFieldDecorator(this.cpuNumberKey, { initialValue: undefined, preserve: true })
    this.form.getFieldDecorator(this.cpuSpeedKey, { initialValue: undefined, preserve: true })
    this.form.getFieldDecorator(this.memoryKey, { initialValue: undefined, preserve: true })
    this.form.getFieldDecorator(this.minIopsKey, { initialValue: undefined, preserve: true })
    this.form.getFieldDecorator(this.maxIopsKey, { initialValue: undefined, preserve: true })
  },
  computed: {
    params () {
      return {
        domains: {
          list: 'listDomains',
          isLoad: true,
          field: 'domainid',
          options: {
            details: 'min',
            showicon: true
          }
        },
        projects: {
          list: 'listProjects',
          isLoad: true,
          field: 'projectid',
          options: {
            details: 'min',
            showicon: true
          }
        },
        templates: {
          list: 'listTemplates',
          isLoad: true,
          options: {
            templatefilter: 'all',
            hypervisor: this.cluster.hypervisortype,
            showicon: true
          },
          field: 'templateid'
        }
      }
    },
    isVmRunning () {
      if (this.resource && this.resource.powerstate === 'PowerOn') {
        return true
      }
      return false
    },
    domainSelectOptions () {
      var domains = this.options.domains.map((domain) => {
        return {
          label: domain.path || domain.name,
          value: domain.id,
          icon: domain?.icon?.base64image || ''
        }
      })
      domains.unshift({
        label: '',
        value: null
      })
      return domains
    },
    projectSelectOptions () {
      var projects = this.options.projects.map((project) => {
        return {
          label: project.name,
          value: project.id,
          icon: project?.icon?.base64image || ''
        }
      })
      projects.unshift({
        label: '',
        value: null
      })
      return projects
    },
    templateSelectOptions () {
      return this.options.templates.map((template) => {
        return {
          label: template.name,
          value: template.id,
          icon: template?.icon?.base64image || '',
          ostypeid: template.ostypeid,
          ostypename: template.ostypename
        }
      })
    },
    dataDisks () {
      var disks = []
      if (this.resource.disk && this.resource.disk.length > 1) {
        for (var index = 0; index < this.resource.disk.length; ++index) {
          if (index !== this.selectedRootDiskIndex) {
            var disk = { ...this.resource.disk[index] }
            disk.size = disk.capacity / (1024 * 1024 * 1024)
            disk.name = disk.label
            disk.meta = this.getMeta(disk, { controller: 'controller', datastorename: 'datastore', position: 'position' })
            disks.push(disk)
          }
        }
      }
      return disks
    },
    nics () {
      var nics = []
      if (this.resource.nic && this.resource.nic.length > 0) {
        for (var nicEntry of this.resource.nic) {
          var nic = { ...nicEntry }
          nic.name = nic.name || nic.id
          nic.displaytext = nic.name
          if (nic.vlanid) {
            nic.broadcasturi = 'vlan://' + nic.vlanid
            if (nic.isolatedpvlan) {
              nic.broadcasturi = 'pvlan://' + nic.vlanid + '-i' + nic.isolatedpvlan
            }
          }
          nic.meta = this.getMeta(nic, { macaddress: 'mac', vlanid: 'vlan', networkname: 'network' })
          nics.push(nic)
        }
      }
      return nics
    }
  },
  watch: {
    isOpen (newValue) {
      if (newValue) {
        this.resetForm()
        this.$refs.displayname.focus()
        this.selectMatchingComputeOffering()
      }
    }
  },
  methods: {
    fetchData () {
      _.each(this.params, (param, name) => {
        if (param.isLoad) {
          this.fetchOptions(param, name)
        }
      })
      this.fetchComputeOfferings({
        keyword: '',
        pageSize: 10,
        page: 1
      })
    },
    getMeta (obj, metaKeys) {
      var meta = []
      for (var key in metaKeys) {
        if (key in obj) {
          meta.push({ key: metaKeys[key], value: obj[key] })
        }
      }
      return meta
    },
    getMinCpu () {
      if (this.isVmRunning) {
        return this.resource.cpunumber
      }
      return 'serviceofferingdetails' in this.computeOffering ? this.computeOffering.serviceofferingdetails.mincpunumber * 1 : 1
    },
    getMinMemory () {
      if (this.isVmRunning) {
        return this.resource.memory
      }
      return 'serviceofferingdetails' in this.computeOffering ? this.computeOffering.serviceofferingdetails.minmemory * 1 : 32
    },
    getMaxCpu () {
      if (this.isVmRunning) {
        return this.resource.cpunumber
      }
      return 'serviceofferingdetails' in this.computeOffering ? this.computeOffering.serviceofferingdetails.maxcpunumber * 1 : Number.MAX_SAFE_INTEGER
    },
    getMaxMemory () {
      if (this.isVmRunning) {
        return this.resource.memory
      }
      return 'serviceofferingdetails' in this.computeOffering ? this.computeOffering.serviceofferingdetails.maxmemory * 1 : Number.MAX_SAFE_INTEGER
    },
    fetchOptions (param, name, exclude) {
      if (exclude && exclude.length > 0) {
        if (exclude.includes(name)) {
          return
        }
      }
      this.optionsLoading[name] = true
      param.loading = true
      param.opts = []
      const options = param.options || {}
      if (!('listall' in options)) {
        options.listall = true
      }
      api(param.list, options).then((response) => {
        param.loading = false
        _.map(response, (responseItem, responseKey) => {
          if (Object.keys(responseItem).length === 0) {
            this.rowCount[name] = 0
            this.options[name] = []
            this.$forceUpdate()
            return
          }
          if (!responseKey.includes('response')) {
            return
          }
          _.map(responseItem, (response, key) => {
            if (key === 'count') {
              this.rowCount[name] = response
              return
            }
            param.opts = response
            this.options[name] = response

            this.$forceUpdate()
          })
        })
      }).catch(function (error) {
        console.log(error.stack)
        param.loading = false
      }).finally(() => {
        this.optionsLoading[name] = false
      })
    },
    fetchComputeOfferings (options) {
      this.computeOfferingLoading = true
      this.totalComputeOfferings = 0
      this.computeOfferings = []
      this.offeringsMap = []
      api('listServiceOfferings', {
        keyword: options.keyword,
        page: options.page,
        pageSize: options.pageSize,
        details: 'min',
        response: 'json'
      }).then(response => {
        this.totalComputeOfferings = response.listserviceofferingsresponse.count
        if (this.totalComputeOfferings === 0) {
          return
        }
        this.computeOfferings = response.listserviceofferingsresponse.serviceoffering
        this.computeOfferings.map(i => { this.offeringsMap[i.id] = i })
      }).finally(() => {
        this.computeOfferingLoading = false
        this.selectMatchingComputeOffering()
      })
    },
    updateFieldValue (name, value) {
      this.form.setFieldsValue({
        [name]: value
      })
    },
    updateComputeOffering (id) {
      this.updateFieldValue('computeofferingid', id)
      this.computeOffering = this.computeOfferings.filter(x => x.id === id)[0]
      if (this.computeOffering && !this.computeOffering.iscustomizediops) {
        this.updateFieldValue(this.minIopsKey, undefined)
        this.updateFieldValue(this.maxIopsKey, undefined)
      }
    },
    updateMultiDiskOffering (data) {
      this.dataDisksOfferingsMapping = data
    },
    updateMultiNetworkOffering (data) {
      this.nicsNetworksMapping = data
    },
    changeTemplateType (e) {
      this.templateType = e.target.value
      if (this.templateType === 'auto') {
        this.updateFieldValue('templateid', undefined)
      }
    },
    selectMatchingComputeOffering () {
      var offerings = [...this.computeOfferings]
      offerings.sort(function (a, b) {
        return a.cpunumber - b.cpunumber
      })
      for (var offering of offerings) {
        var cpuNumberMatches = false
        var cpuSpeedMatches = false
        var memoryMatches = false
        if (!offering.iscustomized) {
          cpuNumberMatches = offering.cpunumber === this.resource.cpunumber
          cpuSpeedMatches = !this.resource.cpuspeed || offering.cpuspeed === this.resource.cpuspeed
          memoryMatches = offering.memory === this.resource.memory
        } else {
          cpuNumberMatches = cpuSpeedMatches = memoryMatches = true
          if (offering.serviceofferingdetails) {
            cpuNumberMatches = (this.resource.cpunumber >= offering.serviceofferingdetails.mincpunumber &&
              this.resource.cpunumber <= offering.serviceofferingdetails.maxcpunumber)
            memoryMatches = (this.resource.memory >= offering.serviceofferingdetails.minmemory &&
              this.resource.memory <= offering.serviceofferingdetails.maxmemory)
            cpuSpeedMatches = !this.resource.cpuspeed || offering.cpuspeed === this.resource.cpuspeed
          }
        }
        if (cpuNumberMatches && cpuSpeedMatches && memoryMatches) {
          setTimeout(() => {
            this.updateComputeOffering(offering.id)
          }, 250)
          break
        }
      }
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFieldsAndScroll((err, values) => {
        if (err) {
          return
        }
        const params = {
          name: this.resource.name,
          clusterid: this.cluster.id,
          displayname: values.displayname
        }
        if (!this.computeOffering || !this.computeOffering.id) {
          this.$notification.error({
            message: this.$t('message.request.failed'),
            description: this.$t('message.step.2.continue')
          })
          return
        }
        params.serviceofferingid = values.computeofferingid
        if (this.computeOffering.iscustomized) {
          var details = [this.cpuNumberKey, this.cpuSpeedKey, this.memoryKey]
          for (var detail of details) {
            if (!(values[detail] || this.computeOffering[detail])) {
              this.$notification.error({
                message: this.$t('message.request.failed'),
                description: this.$t('message.please.enter.valid.value') + ': ' + this.$t('label.' + detail.toLowerCase())
              })
              return
            }
            if (values[detail]) {
              params['details[0].' + detail] = values[detail]
            }
          }
        }
        if (this.computeOffering.iscustomizediops) {
          var iopsDetails = [this.minIopsKey, this.maxIopsKey]
          for (var iopsDetail of iopsDetails) {
            if (!values[iopsDetail] || values[iopsDetail] < 0) {
              this.$notification.error({
                message: this.$t('message.request.failed'),
                description: this.$t('message.please.enter.valid.value') + ': ' + this.$t('label.' + iopsDetail.toLowerCase())
              })
              return
            }
            params['details[0].' + iopsDetail] = values[iopsDetail]
          }
          if (values[this.minIopsKey] > values[this.maxIopsKey]) {
            this.$notification.error({
              message: this.$t('message.request.failed'),
              description: this.$t('error.form.message')
            })
          }
        }
        var keys = ['hostname', 'domainid', 'projectid', 'account', 'migrateallowed', 'forced']
        if (this.templateType !== 'auto') {
          keys.push('templateid')
        }
        for (var key of keys) {
          if (values[key]) {
            params[key] = values[key]
          }
        }
        var diskOfferingIndex = 0
        for (var diskId in this.dataDisksOfferingsMapping) {
          if (!this.dataDisksOfferingsMapping[diskId]) {
            this.$notification.error({
              message: this.$t('message.request.failed'),
              description: this.$t('message.select.disk.offering') + ': ' + diskId
            })
            return
          }
          params['datadiskofferinglist[' + diskOfferingIndex + '].disk'] = diskId
          params['datadiskofferinglist[' + diskOfferingIndex + '].diskOffering'] = this.dataDisksOfferingsMapping[diskId]
          diskOfferingIndex++
        }
        var nicNetworkIndex = 0
        var nicIpIndex = 0
        for (var nicId in this.nicsNetworksMapping) {
          if (!this.nicsNetworksMapping[nicId].network) {
            this.$notification.error({
              message: this.$t('message.request.failed'),
              description: this.$t('message.select.nic.network') + ': ' + nicId
            })
            return
          }
          params['nicnetworklist[' + nicNetworkIndex + '].nic'] = nicId
          params['nicnetworklist[' + nicNetworkIndex + '].network'] = this.nicsNetworksMapping[nicId].network
          nicNetworkIndex++
          if ('ipAddress' in this.nicsNetworksMapping[nicId]) {
            if (!this.nicsNetworksMapping[nicId].ipAddress) {
              this.$notification.error({
                message: this.$t('message.request.failed'),
                description: this.$t('message.enter.valid.nic.ip') + ': ' + nicId
              })
              return
            }
            params['nicipaddresslist[' + nicIpIndex + '].nic'] = nicId
            params['nicipaddresslist[' + nicIpIndex + '].ip4Address'] = this.nicsNetworksMapping[nicId].ipAddress
            nicIpIndex++
          }
        }
        this.updateLoading(true)
        const name = this.resource.name
        api('importUnmanagedInstance', params).then(json => {
          const jobId = json.importunmanagedinstanceresponse.jobid
          this.$pollJob({
            jobId,
            title: this.$t('label.import.instance'),
            description: name,
            loadingMessage: `${this.$t('label.import.instance')} ${name} ${this.$t('label.in.progress')}`,
            catchMessage: this.$t('error.fetching.async.job.result'),
            successMessage: this.$t('message.success.import.instance') + ' ' + name,
            successMethod: result => {
              this.$emit('refresh-data')
            }
          })
          this.closeAction()
        }).catch(error => {
          this.$notifyError(error)
        }).finally(() => {
          this.updateLoading(false)
        })
      })
    },
    updateLoading (value) {
      this.loading = value
      this.$emit('loading-changed', value)
    },
    resetForm () {
      var fields = ['displayname', 'hostname', 'domainid', 'account', 'projectid', 'computeofferingid']
      for (var field of fields) {
        this.updateFieldValue(field, undefined)
      }
      this.templateType = 'auto'
      this.updateComputeOffering(undefined)
      this.switches = {}
    },
    closeAction () {
      this.$emit('close-action')
    }
  }
}
</script>

<style lang="less">
  @import url('../../style/index');
  .ant-table-selection-column {
    // Fix for the table header if the row selection use radio buttons instead of checkboxes
    > div:empty {
      width: 16px;
    }
  }

  .ant-collapse-borderless > .ant-collapse-item {
    border: 1px solid @border-color-split;
    border-radius: @border-radius-base !important;
    margin: 0 0 1.2rem;
  }

  .form-layout {
    width: 120vw;

    @media (min-width: 1000px) {
      width: 550px;
    }
  }

  .action-button {
    text-align: right;

    button {
      margin-right: 5px;
    }
  }
</style>
