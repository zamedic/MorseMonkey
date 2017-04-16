#
# Cookbook Name:: morse_money_cookbook
# Recipe:: default
#
# Copyright 2017, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
include_recipe 'java'

chef_gem 'chef-vault' do
  action :nothing
end.run_action(:install)

require 'chef-vault'

package 'sshpass'

execute 'pkill java' do
  returns [0, 1]
end

directory node['morse_monkey_cookbook']['dir'] do
  action :create
  recursive true
end

include_recipe 'morce_monkey::application_yml'
include_recuoe 'morse_monkey::users'

write_auth()

execute "java -jar  /opt/morse_monkey/morsemonkey.jar -Dswarm.project.stage.file=#{node['morse_monkey_cookbook']['dir']}/#{node['morse_monkey_cookbook']['application_yml']}	 > /var/log/morse_monkey.log &"
