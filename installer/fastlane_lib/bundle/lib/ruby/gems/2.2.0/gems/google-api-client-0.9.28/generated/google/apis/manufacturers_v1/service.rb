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

require 'google/apis/core/base_service'
require 'google/apis/core/json_representation'
require 'google/apis/core/hashable'
require 'google/apis/errors'

module Google
  module Apis
    module ManufacturersV1
      # Manufacturer Center API
      #
      # Public API for managing Manufacturer Center related data.
      #
      # @example
      #    require 'google/apis/manufacturers_v1'
      #
      #    Manufacturers = Google::Apis::ManufacturersV1 # Alias the module
      #    service = Manufacturers::ManufacturerCenterService.new
      #
      # @see https://developers.google.com/manufacturers/
      class ManufacturerCenterService < Google::Apis::Core::BaseService
        # @return [String]
        #  API key. Your API key identifies your project and provides you with API access,
        #  quota, and reports. Required unless you provide an OAuth 2.0 token.
        attr_accessor :key

        # @return [String]
        #  Available to use for quota purposes for server-side applications. Can be any
        #  arbitrary string assigned to a user, but should not exceed 40 characters.
        attr_accessor :quota_user

        def initialize
          super('https://manufacturers.googleapis.com/', '')
        end
        
        # Gets the product from a Manufacturer Center account, including product
        # issues.
        # @param [String] parent
        #   Parent ID in the format `accounts/`account_id``.
        #   `account_id` - The ID of the Manufacturer Center account.
        # @param [String] name
        #   Name in the format ``target_country`:`content_language`:`product_id``.
        #   `target_country`   - The target country of the product as a CLDR territory
        #   code (for example, US).
        #   `content_language` - The content language of the product as a two-letter
        #   ISO 639-1 language code (for example, en).
        #   `product_id`     -   The ID of the product. For more information, see
        #   https://support.google.com/manufacturers/answer/6124116#
        #   id.
        # @param [String] quota_user
        #   Available to use for quota purposes for server-side applications. Can be any
        #   arbitrary string assigned to a user, but should not exceed 40 characters.
        # @param [String] fields
        #   Selector specifying which fields to include in a partial response.
        # @param [Google::Apis::RequestOptions] options
        #   Request-specific options
        #
        # @yield [result, err] Result & error if block supplied
        # @yieldparam result [Google::Apis::ManufacturersV1::Product] parsed result object
        # @yieldparam err [StandardError] error object if request failed
        #
        # @return [Google::Apis::ManufacturersV1::Product]
        #
        # @raise [Google::Apis::ServerError] An error occurred on the server and the request can be retried
        # @raise [Google::Apis::ClientError] The request is invalid and should not be retried without modification
        # @raise [Google::Apis::AuthorizationError] Authorization is required
        def get_account_product(parent, name, quota_user: nil, fields: nil, options: nil, &block)
          command =  make_simple_command(:get, 'v1/{+parent}/products/{+name}', options)
          command.response_representation = Google::Apis::ManufacturersV1::Product::Representation
          command.response_class = Google::Apis::ManufacturersV1::Product
          command.params['parent'] = parent unless parent.nil?
          command.params['name'] = name unless name.nil?
          command.query['quotaUser'] = quota_user unless quota_user.nil?
          command.query['fields'] = fields unless fields.nil?
          execute_or_queue_command(command, &block)
        end
        
        # Lists all the products in a Manufacturer Center account.
        # @param [String] parent
        #   Parent ID in the format `accounts/`account_id``.
        #   `account_id` - The ID of the Manufacturer Center account.
        # @param [Fixnum] page_size
        #   Maximum number of product statuses to return in the response, used for
        #   paging.
        # @param [String] page_token
        #   The token returned by the previous request.
        # @param [String] quota_user
        #   Available to use for quota purposes for server-side applications. Can be any
        #   arbitrary string assigned to a user, but should not exceed 40 characters.
        # @param [String] fields
        #   Selector specifying which fields to include in a partial response.
        # @param [Google::Apis::RequestOptions] options
        #   Request-specific options
        #
        # @yield [result, err] Result & error if block supplied
        # @yieldparam result [Google::Apis::ManufacturersV1::ListProductsResponse] parsed result object
        # @yieldparam err [StandardError] error object if request failed
        #
        # @return [Google::Apis::ManufacturersV1::ListProductsResponse]
        #
        # @raise [Google::Apis::ServerError] An error occurred on the server and the request can be retried
        # @raise [Google::Apis::ClientError] The request is invalid and should not be retried without modification
        # @raise [Google::Apis::AuthorizationError] Authorization is required
        def list_account_products(parent, page_size: nil, page_token: nil, quota_user: nil, fields: nil, options: nil, &block)
          command =  make_simple_command(:get, 'v1/{+parent}/products', options)
          command.response_representation = Google::Apis::ManufacturersV1::ListProductsResponse::Representation
          command.response_class = Google::Apis::ManufacturersV1::ListProductsResponse
          command.params['parent'] = parent unless parent.nil?
          command.query['pageSize'] = page_size unless page_size.nil?
          command.query['pageToken'] = page_token unless page_token.nil?
          command.query['quotaUser'] = quota_user unless quota_user.nil?
          command.query['fields'] = fields unless fields.nil?
          execute_or_queue_command(command, &block)
        end

        protected

        def apply_command_defaults(command)
          command.query['key'] = key unless key.nil?
          command.query['quotaUser'] = quota_user unless quota_user.nil?
        end
      end
    end
  end
end
