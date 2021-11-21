#!/bin/bash
sed "s/tagVersion/$1/g" deployment.yml > app-deploy.yml
