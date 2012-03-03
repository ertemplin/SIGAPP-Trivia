#!/bin/sh
bundle install
rake db:create
rake db:migrate
