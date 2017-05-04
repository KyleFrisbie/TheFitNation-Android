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
    module ManufacturersV1
      
      # 
      class ListProductsResponse
        include Google::Apis::Core::Hashable
      
        # The token for the retrieval of the next page of product statuses.
        # Corresponds to the JSON property `nextPageToken`
        # @return [String]
        attr_accessor :next_page_token
      
        # List of the products.
        # Corresponds to the JSON property `products`
        # @return [Array<Google::Apis::ManufacturersV1::Product>]
        attr_accessor :products
      
        def initialize(**args)
           update!(**args)
        end
      
        # Update properties of this object
        def update!(**args)
          @next_page_token = args[:next_page_token] if args.key?(:next_page_token)
          @products = args[:products] if args.key?(:products)
        end
      end
      
      # Product issue.
      class Issue
        include Google::Apis::Core::Hashable
      
        # The severity of the issue.
        # Corresponds to the JSON property `severity`
        # @return [String]
        attr_accessor :severity
      
        # Description of the issue.
        # Corresponds to the JSON property `description`
        # @return [String]
        attr_accessor :description
      
        # If present, the attribute that triggered the issue. For more information
        # about attributes, see
        # https://support.google.com/manufacturers/answer/6124116.
        # Corresponds to the JSON property `attribute`
        # @return [String]
        attr_accessor :attribute
      
        # The server-generated type of the issue, for example,
        # “INCORRECT_TEXT_FORMATTING”, “IMAGE_NOT_SERVEABLE”, etc.
        # Corresponds to the JSON property `type`
        # @return [String]
        attr_accessor :type
      
        def initialize(**args)
           update!(**args)
        end
      
        # Update properties of this object
        def update!(**args)
          @severity = args[:severity] if args.key?(:severity)
          @description = args[:description] if args.key?(:description)
          @attribute = args[:attribute] if args.key?(:attribute)
          @type = args[:type] if args.key?(:type)
        end
      end
      
      # Attributes of the product. For more information, see
      # https://support.google.com/manufacturers/answer/6124116.
      class Attributes
        include Google::Apis::Core::Hashable
      
        # The URL of the manufacturer's detail page of the product. For more
        # information, see
        # https://support.google.com/manufacturers/answer/6124116#productpage.
        # Corresponds to the JSON property `productPageUrl`
        # @return [String]
        attr_accessor :product_page_url
      
        # The Manufacturer Part Number (MPN) of the product. For more information,
        # see https://support.google.com/manufacturers/answer/6124116#mpn.
        # Corresponds to the JSON property `mpn`
        # @return [String]
        attr_accessor :mpn
      
        # The title of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116#title.
        # Corresponds to the JSON property `title`
        # @return [String]
        attr_accessor :title
      
        # The name of the group of products related to the product. For more
        # information, see
        # https://support.google.com/manufacturers/answer/6124116#productline.
        # Corresponds to the JSON property `productLine`
        # @return [String]
        attr_accessor :product_line
      
        # The canonical name of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116#productname.
        # Corresponds to the JSON property `productName`
        # @return [String]
        attr_accessor :product_name
      
        # The brand name of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116#brand.
        # Corresponds to the JSON property `brand`
        # @return [String]
        attr_accessor :brand
      
        # The manufacturer's category of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116#producttype.
        # Corresponds to the JSON property `productType`
        # @return [Array<String>]
        attr_accessor :product_type
      
        # The Global Trade Item Number (GTIN) of the product. For more information,
        # see https://support.google.com/manufacturers/answer/6124116#gtin.
        # Corresponds to the JSON property `gtin`
        # @return [Array<String>]
        attr_accessor :gtin
      
        def initialize(**args)
           update!(**args)
        end
      
        # Update properties of this object
        def update!(**args)
          @product_page_url = args[:product_page_url] if args.key?(:product_page_url)
          @mpn = args[:mpn] if args.key?(:mpn)
          @title = args[:title] if args.key?(:title)
          @product_line = args[:product_line] if args.key?(:product_line)
          @product_name = args[:product_name] if args.key?(:product_name)
          @brand = args[:brand] if args.key?(:brand)
          @product_type = args[:product_type] if args.key?(:product_type)
          @gtin = args[:gtin] if args.key?(:gtin)
        end
      end
      
      # Product data.
      class Product
        include Google::Apis::Core::Hashable
      
        # Parent ID in the format `accounts/`account_id``.
        # `account_id` - The ID of the Manufacturer Center account.
        # @OutputOnly
        # Corresponds to the JSON property `parent`
        # @return [String]
        attr_accessor :parent
      
        # The target country of the product as a CLDR territory code (for example,
        # US).
        # @OutputOnly
        # Corresponds to the JSON property `targetCountry`
        # @return [String]
        attr_accessor :target_country
      
        # The content language of the product as a two-letter ISO 639-1 language code
        # (for example, en).
        # @OutputOnly
        # Corresponds to the JSON property `contentLanguage`
        # @return [String]
        attr_accessor :content_language
      
        # Names of the attributes of the product deleted manually via the
        # Manufacturer Center UI.
        # @OutputOnly
        # Corresponds to the JSON property `manuallyDeletedAttributes`
        # @return [Array<String>]
        attr_accessor :manually_deleted_attributes
      
        # The ID of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116#id.
        # @OutputOnly
        # Corresponds to the JSON property `productId`
        # @return [String]
        attr_accessor :product_id
      
        # Attributes of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116.
        # Corresponds to the JSON property `finalAttributes`
        # @return [Google::Apis::ManufacturersV1::Attributes]
        attr_accessor :final_attributes
      
        # A server-generated list of issues associated with the product.
        # @OutputOnly
        # Corresponds to the JSON property `issues`
        # @return [Array<Google::Apis::ManufacturersV1::Issue>]
        attr_accessor :issues
      
        # Name in the format ``target_country`:`content_language`:`product_id``.
        # `target_country`   - The target country of the product as a CLDR territory
        # code (for example, US).
        # `content_language` - The content language of the product as a two-letter
        # ISO 639-1 language code (for example, en).
        # `product_id`     -   The ID of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116#
        # id.
        # @OutputOnly
        # Corresponds to the JSON property `name`
        # @return [String]
        attr_accessor :name
      
        # Attributes of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116.
        # Corresponds to the JSON property `uploadedAttributes`
        # @return [Google::Apis::ManufacturersV1::Attributes]
        attr_accessor :uploaded_attributes
      
        # Attributes of the product. For more information, see
        # https://support.google.com/manufacturers/answer/6124116.
        # Corresponds to the JSON property `manuallyProvidedAttributes`
        # @return [Google::Apis::ManufacturersV1::Attributes]
        attr_accessor :manually_provided_attributes
      
        def initialize(**args)
           update!(**args)
        end
      
        # Update properties of this object
        def update!(**args)
          @parent = args[:parent] if args.key?(:parent)
          @target_country = args[:target_country] if args.key?(:target_country)
          @content_language = args[:content_language] if args.key?(:content_language)
          @manually_deleted_attributes = args[:manually_deleted_attributes] if args.key?(:manually_deleted_attributes)
          @product_id = args[:product_id] if args.key?(:product_id)
          @final_attributes = args[:final_attributes] if args.key?(:final_attributes)
          @issues = args[:issues] if args.key?(:issues)
          @name = args[:name] if args.key?(:name)
          @uploaded_attributes = args[:uploaded_attributes] if args.key?(:uploaded_attributes)
          @manually_provided_attributes = args[:manually_provided_attributes] if args.key?(:manually_provided_attributes)
        end
      end
    end
  end
end
