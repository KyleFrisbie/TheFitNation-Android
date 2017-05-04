# Copyright 2015 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

require 'date'
require 'google/apis/core/base_service'
require 'google/apis/core/json_representation'
require 'google/apis/core/hashable'
require 'google/apis/errors'

module Google
  module Apis
    module MonitoringV3
      
      class ListGroupsResponse
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class ListGroupMembersResponse
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class CreateCollectdTimeSeriesRequest
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class ListMonitoredResourceDescriptorsResponse
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class TimeSeries
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class CreateTimeSeriesRequest
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Distribution
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class MonitoredResource
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class ListMetricDescriptorsResponse
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class MonitoredResourceDescriptor
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class TypedValue
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class CollectdPayload
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Linear
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Option
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Empty
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class TimeInterval
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Explicit
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Exponential
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Point
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Field
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Metric
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class LabelDescriptor
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class ListTimeSeriesResponse
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Type
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Group
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class BucketOptions
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class CollectdValue
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class MetricDescriptor
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class SourceContext
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class Range
        class Representation < Google::Apis::Core::JsonRepresentation; end
      
        include Google::Apis::Core::JsonObjectSupport
      end
      
      class ListGroupsResponse
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :next_page_token, as: 'nextPageToken'
          collection :group, as: 'group', class: Google::Apis::MonitoringV3::Group, decorator: Google::Apis::MonitoringV3::Group::Representation
      
        end
      end
      
      class ListGroupMembersResponse
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :members, as: 'members', class: Google::Apis::MonitoringV3::MonitoredResource, decorator: Google::Apis::MonitoringV3::MonitoredResource::Representation
      
          property :next_page_token, as: 'nextPageToken'
          property :total_size, as: 'totalSize'
        end
      end
      
      class CreateCollectdTimeSeriesRequest
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :resource, as: 'resource', class: Google::Apis::MonitoringV3::MonitoredResource, decorator: Google::Apis::MonitoringV3::MonitoredResource::Representation
      
          collection :collectd_payloads, as: 'collectdPayloads', class: Google::Apis::MonitoringV3::CollectdPayload, decorator: Google::Apis::MonitoringV3::CollectdPayload::Representation
      
          property :collectd_version, as: 'collectdVersion'
        end
      end
      
      class ListMonitoredResourceDescriptorsResponse
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :resource_descriptors, as: 'resourceDescriptors', class: Google::Apis::MonitoringV3::MonitoredResourceDescriptor, decorator: Google::Apis::MonitoringV3::MonitoredResourceDescriptor::Representation
      
          property :next_page_token, as: 'nextPageToken'
        end
      end
      
      class TimeSeries
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :resource, as: 'resource', class: Google::Apis::MonitoringV3::MonitoredResource, decorator: Google::Apis::MonitoringV3::MonitoredResource::Representation
      
          property :metric_kind, as: 'metricKind'
          property :metric, as: 'metric', class: Google::Apis::MonitoringV3::Metric, decorator: Google::Apis::MonitoringV3::Metric::Representation
      
          collection :points, as: 'points', class: Google::Apis::MonitoringV3::Point, decorator: Google::Apis::MonitoringV3::Point::Representation
      
          property :value_type, as: 'valueType'
        end
      end
      
      class CreateTimeSeriesRequest
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :time_series, as: 'timeSeries', class: Google::Apis::MonitoringV3::TimeSeries, decorator: Google::Apis::MonitoringV3::TimeSeries::Representation
      
        end
      end
      
      class Distribution
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :range, as: 'range', class: Google::Apis::MonitoringV3::Range, decorator: Google::Apis::MonitoringV3::Range::Representation
      
          property :mean, as: 'mean'
          property :count, as: 'count'
          collection :bucket_counts, as: 'bucketCounts'
          property :bucket_options, as: 'bucketOptions', class: Google::Apis::MonitoringV3::BucketOptions, decorator: Google::Apis::MonitoringV3::BucketOptions::Representation
      
          property :sum_of_squared_deviation, as: 'sumOfSquaredDeviation'
        end
      end
      
      class MonitoredResource
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          hash :labels, as: 'labels'
          property :type, as: 'type'
        end
      end
      
      class ListMetricDescriptorsResponse
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :metric_descriptors, as: 'metricDescriptors', class: Google::Apis::MonitoringV3::MetricDescriptor, decorator: Google::Apis::MonitoringV3::MetricDescriptor::Representation
      
          property :next_page_token, as: 'nextPageToken'
        end
      end
      
      class MonitoredResourceDescriptor
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :type, as: 'type'
          collection :labels, as: 'labels', class: Google::Apis::MonitoringV3::LabelDescriptor, decorator: Google::Apis::MonitoringV3::LabelDescriptor::Representation
      
          property :name, as: 'name'
          property :display_name, as: 'displayName'
          property :description, as: 'description'
        end
      end
      
      class TypedValue
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :double_value, as: 'doubleValue'
          property :int64_value, as: 'int64Value'
          property :distribution_value, as: 'distributionValue', class: Google::Apis::MonitoringV3::Distribution, decorator: Google::Apis::MonitoringV3::Distribution::Representation
      
          property :bool_value, as: 'boolValue'
          property :string_value, as: 'stringValue'
        end
      end
      
      class CollectdPayload
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :values, as: 'values', class: Google::Apis::MonitoringV3::CollectdValue, decorator: Google::Apis::MonitoringV3::CollectdValue::Representation
      
          property :type_instance, as: 'typeInstance'
          property :type, as: 'type'
          hash :metadata, as: 'metadata', class: Google::Apis::MonitoringV3::TypedValue, decorator: Google::Apis::MonitoringV3::TypedValue::Representation
      
          property :plugin, as: 'plugin'
          property :plugin_instance, as: 'pluginInstance'
          property :end_time, as: 'endTime'
          property :start_time, as: 'startTime'
        end
      end
      
      class Linear
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :width, as: 'width'
          property :offset, as: 'offset'
          property :num_finite_buckets, as: 'numFiniteBuckets'
        end
      end
      
      class Option
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          hash :value, as: 'value'
          property :name, as: 'name'
        end
      end
      
      class Empty
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
        end
      end
      
      class TimeInterval
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :end_time, as: 'endTime'
          property :start_time, as: 'startTime'
        end
      end
      
      class Explicit
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :bounds, as: 'bounds'
        end
      end
      
      class Exponential
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :scale, as: 'scale'
          property :num_finite_buckets, as: 'numFiniteBuckets'
          property :growth_factor, as: 'growthFactor'
        end
      end
      
      class Point
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :interval, as: 'interval', class: Google::Apis::MonitoringV3::TimeInterval, decorator: Google::Apis::MonitoringV3::TimeInterval::Representation
      
          property :value, as: 'value', class: Google::Apis::MonitoringV3::TypedValue, decorator: Google::Apis::MonitoringV3::TypedValue::Representation
      
        end
      end
      
      class Field
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :type_url, as: 'typeUrl'
          property :number, as: 'number'
          property :kind, as: 'kind'
          property :json_name, as: 'jsonName'
          collection :options, as: 'options', class: Google::Apis::MonitoringV3::Option, decorator: Google::Apis::MonitoringV3::Option::Representation
      
          property :oneof_index, as: 'oneofIndex'
          property :packed, as: 'packed'
          property :cardinality, as: 'cardinality'
          property :default_value, as: 'defaultValue'
          property :name, as: 'name'
        end
      end
      
      class Metric
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          hash :labels, as: 'labels'
          property :type, as: 'type'
        end
      end
      
      class LabelDescriptor
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :key, as: 'key'
          property :description, as: 'description'
          property :value_type, as: 'valueType'
        end
      end
      
      class ListTimeSeriesResponse
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :time_series, as: 'timeSeries', class: Google::Apis::MonitoringV3::TimeSeries, decorator: Google::Apis::MonitoringV3::TimeSeries::Representation
      
          property :next_page_token, as: 'nextPageToken'
        end
      end
      
      class Type
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          collection :options, as: 'options', class: Google::Apis::MonitoringV3::Option, decorator: Google::Apis::MonitoringV3::Option::Representation
      
          collection :fields, as: 'fields', class: Google::Apis::MonitoringV3::Field, decorator: Google::Apis::MonitoringV3::Field::Representation
      
          property :name, as: 'name'
          collection :oneofs, as: 'oneofs'
          property :syntax, as: 'syntax'
          property :source_context, as: 'sourceContext', class: Google::Apis::MonitoringV3::SourceContext, decorator: Google::Apis::MonitoringV3::SourceContext::Representation
      
        end
      end
      
      class Group
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :name, as: 'name'
          property :parent_name, as: 'parentName'
          property :display_name, as: 'displayName'
          property :is_cluster, as: 'isCluster'
          property :filter, as: 'filter'
        end
      end
      
      class BucketOptions
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :exponential_buckets, as: 'exponentialBuckets', class: Google::Apis::MonitoringV3::Exponential, decorator: Google::Apis::MonitoringV3::Exponential::Representation
      
          property :linear_buckets, as: 'linearBuckets', class: Google::Apis::MonitoringV3::Linear, decorator: Google::Apis::MonitoringV3::Linear::Representation
      
          property :explicit_buckets, as: 'explicitBuckets', class: Google::Apis::MonitoringV3::Explicit, decorator: Google::Apis::MonitoringV3::Explicit::Representation
      
        end
      end
      
      class CollectdValue
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :value, as: 'value', class: Google::Apis::MonitoringV3::TypedValue, decorator: Google::Apis::MonitoringV3::TypedValue::Representation
      
          property :data_source_type, as: 'dataSourceType'
          property :data_source_name, as: 'dataSourceName'
        end
      end
      
      class MetricDescriptor
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :display_name, as: 'displayName'
          property :description, as: 'description'
          property :unit, as: 'unit'
          collection :labels, as: 'labels', class: Google::Apis::MonitoringV3::LabelDescriptor, decorator: Google::Apis::MonitoringV3::LabelDescriptor::Representation
      
          property :name, as: 'name'
          property :type, as: 'type'
          property :value_type, as: 'valueType'
          property :metric_kind, as: 'metricKind'
        end
      end
      
      class SourceContext
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :file_name, as: 'fileName'
        end
      end
      
      class Range
        # @private
        class Representation < Google::Apis::Core::JsonRepresentation
          property :min, as: 'min'
          property :max, as: 'max'
        end
      end
    end
  end
end
