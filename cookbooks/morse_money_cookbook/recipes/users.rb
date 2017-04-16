config = data_bag_item('morse-monkey', node.chef_environment)

template "#{node['morse_monkey_cookbook']['dir']}/#{['morse_monkey_cookbook']['users_file']}" do
  source 'users.erb'
  variables({
                users: config.users
            })
end

