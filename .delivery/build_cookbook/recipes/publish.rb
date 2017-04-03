#
# Cookbook Name:: build_cookbook
# Recipe:: publish
#
# Copyright (c) 2017 The Authors, All Rights Reserved.
secrets = get_project_secrets

maven_setting 'settings.servers' do
  value 'server' => {
      'id' => 's3.releases',
      'username' => secrets['s3-user'],
      'password' => secrets['s3-key']
  }
end

include_recipe 'coffee-truck::publish'