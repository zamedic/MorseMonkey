bots = ChefVault::Item.load('morse_monkey', 'bots')
config = data_bag_item('morse-monkey', node.chef_environment)


template "#{node['morse_monkey_cookbook']['dir']}/#{['morse_monkey_cookbook']['application_yml']}" do
  source 'morse_monkey.erb'
  variables ({
      node: node,
      bot_key: bots[config.bot.databag_secret],
      data_bag: config
  })
end

template "#{node['morse_monkey_cookbook']['dir']}/#{['morse_monkey_cookbook']['users_file']}" do
  source 'users.erb'
  variables({
                users: config.users
            })
end

template "#{node['morse_monkey_cookbook']['dir']}/#{['morse_monkey_cookbook']['recipe_file']}" do
  source 'users.erb'
  variables({
                cookbooks: config.cookbooks
            })
end

template "#{node['morse_monkey_cookbook']['dir']}/#{['morse_monkey_cookbook']['endpoints_file']}" do
  source 'users.erb'
  variables({
                cookbooks: config.monitor.endpoints
            })
end
