require_relative 'config_files'

Chef::Recipe.send(:include, MorseMonkey::DSL)
Chef::Resource.send(:include, MorseMonkey::DSL)
Chef::Provider.send(:include, MorseMonkey::DSL)