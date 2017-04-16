module MorseMonkey
  module Helpers
    module Config
      extend self

      def write_auth()
        keys = ChefVault::Item.load('morse_monkey', 'keys')
        File.new("#{node['morse_monkey_cookbook']['dir']}/#{node['morse_monkey_cookbook']['auth_file']}", "w") do |file|
          config = data_bag_item('morse-monkey', node.chef_environment)
          keys = ChefVault::Item.load('morse_monkey', 'keys')
          keyFileName = "#{node['morse_monkey_cookbook']['dir']}/#{server.id}.key"
          for server in config.ssh.connections do
            File.new(keyFileName, "w") do |keyfile|
              keyContent = keys[server.key.atabag_secret]
              keyFile.write(keyContent)
            end

            file.puts("#{server.id},#{server.username},#{keyFileName},#{server.password}")
          end
        end
      end
    end

    module DSL
      def write_auth()
        MorseMonkey::Helpers::Config.write_auth()
      end
    end

  end
end
